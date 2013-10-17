package net.foxycorndog.arrowide.language.java;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;
import static net.foxycorndog.arrowide.ArrowIDE.PROJECT_CLASSPATHS;
import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.Program;
import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.command.CommandListener;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.event.CompilerEvent;
import net.foxycorndog.arrowide.event.CompilerListener;
import net.foxycorndog.arrowide.event.ProgramListener;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.CompileOutput;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;
import net.foxycorndog.arrowide.xml.XMLItem;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class JavaLanguage
{
	private static HashMap<String, String> classpaths;
	
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public  static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	private static JavaCompiler compiler;
	
	static
	{
		COMMENT_PROPERTIES = new CommentProperties("//", "/*", "*/", new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES  = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties(new char[] {  }, new char[] { '=' }, new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		classpaths = new HashMap<String, String>();
		
		JavaKeyword.init();
		
//		new Thread()
//		{
//			int counter;
//			
//			public void run()
//			{
//				try
//				{
//					URL url = new URL("file://" + PROPERTIES.get("arrowide.location") + "/res/bin/");
//				
//					ClassLoader cLoader = new URLClassLoader(new URL[] { url });
//					
//					cLoader.loadClass("Start");
//					
//					System.out.println("after");
//				}
//				catch (ClassNotFoundException e)
//				{
//					e.printStackTrace();
//				}
//				catch (MalformedURLException e)
//				{
//					e.printStackTrace();
//				}
//			}
//		}.start();
	}
	
	public static void parseChar()
	{
		
	}
	
	public static Program run(String fileLocation, final ConsoleStream stream)
	{
		return run(fileLocation, stream, null);
	}
	
	public static Program run(String fileLocation, final ConsoleStream stream, final ProgramListener programListener)
	{
		fileLocation = FileUtils.removeExtension(fileLocation);
		
		// TODO: Fix the issue with the /src/ possible ambiguities.
		String projectLocation = FileUtils.getPrecedingPath(fileLocation, "/src/");
		
		String className  = FileUtils.getPathRelativeTo(fileLocation, "/src/");
		
		String classLocation = projectLocation + "/bin/" + className;
		
		if (!FileUtils.fileExists(CONFIG_DATA.get("jdk.location")))
		{
			FileBrowseDialog jdkSearch = new FileBrowseDialog("Specify your JDK location.", "Location:", CONFIG_DATA.get("jdk.location"), FileBrowseDialog.DIRECTORY);
			
			String jdkLoc = jdkSearch.open();
			
			if (jdkLoc != null)
			{
				String location = FileUtils.removeEndingSlashes(jdkLoc.replace('\\', '/'));
			
				ArrowIDE.setConfigDataValue("jdk.location", location);
			}
			else
			{
				stream.println("You must specify a valid jdk to compile this program.");
				
				return null;
			}
		}
		
		String classpath = getClasspath(projectLocation);
		
		Command c = new Command(Display.getDefault(), new String[] { CONFIG_DATA.get("jdk.location") + "/bin/java", "-cp", classpath, "-Xdebug", "-agentlib:jdwp=transport=dt_socket,address=4003,server=y,suspend=y", className }, projectLocation);
		final Command c2 = new Command(Display.getDefault(), new String[] { "telnet", "localhost", "4003" }, projectLocation);
		
		String fileName = FileUtils.getFileNameWithoutExtension(fileLocation);
		
		ProgramListener listener = new ProgramListener()
		{
			public void programTerminated(Program program)
			{
				programListener.programTerminated(program);
			}
			
			public void programStarted(Program program)
			{
					System.out.println("yyy");
				programListener.programStarted(program);
				
				try
				{
					messageReceived("test");
					
					c2.execute();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			public void messageReceived(String message)
			{
				programListener.messageReceived(message);
			}
			
			public void errorMessageReceived(String message)
			{
				programListener.errorMessageReceived(message);
			}
		};
		
		try
		{
			c.execute(fileName, listener);
			
			return c.getProgram();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	private static String getClasspath(String projectLocation)
	{
		String classpath = null;
		
		if (classpaths.containsKey(projectLocation))
		{
			classpath = classpaths.get(projectLocation);
		}
		else
		{
			String dependencies[] = getDependencies(projectLocation);
			
			char colon = (Character)PROPERTIES.get("colon");
			
			classpath = projectLocation + "/bin";
			
			if (dependencies != null)
			{
				for (String dependency : dependencies)
				{
					classpath += colon + dependency;
				}
			}
			
			classpaths.put(projectLocation, classpath);
		}
		
		return classpath;
	}
	
	private static String[] getDependencies(String projectLocation)
	{
		ArrayList<String> dependencies = new ArrayList<String>();
		
		ArrowIDE.checkProject(projectLocation);
		
		String cpLocation = projectLocation + "/.classpath";
		String parentLocation = FileUtils.getParentFolder(projectLocation);
		
		if (PROJECT_CLASSPATHS.containsKey(cpLocation))
		{
			HashMap<String, XMLItem[]> map = PROJECT_CLASSPATHS.get(cpLocation);
			
			XMLItem items[] = map.get("classpath.classpathentry");
			
			if (items != null)
			{
				for (int i = 0; i < items.length; i++)
				{
					HashMap<String, String> attribute = items[i].getAttributes();
					
					if (attribute.containsKey("kind"))
					{
						String kind = attribute.get("kind");
						String path = attribute.get("path");
						
						if (kind.equals("src"))
						{
							if (!path.equals("src"))
							{
								if (path.startsWith("/"))
								{
									path = parentLocation + path;
								}
								
								String pathOutput = path + "/bin";
								
								dependencies.add(pathOutput);
								
								dependencies.add(path);
								
								if (!new File(pathOutput).isDirectory())
								{
									File parentFile = new File(path);
									
									File children[] = parentFile.listFiles();
									
									for (int j = 0; children != null && j < children.length; j++)
									{
										String name = children[j].getName();
										
										if (name.endsWith(".jar"))
										{
											dependencies.add(path + "/" + name);
										}
									}
								}
								
								ArrowIDE.checkProject(path);
								
								String more[] = getDependencies(path);
								
								for (int d = 0; d < more.length; d++)
								{
									dependencies.add(more[d]);
								}
							}
						}
						else if (kind.equals("lib"))
						{
							String pLoc = "";
							
							if (path.startsWith("/"))
							{
								pLoc = parentLocation;
							}
							
							dependencies.add(pLoc + path);
						}
					}
				}
			}
		}
		
		return dependencies.toArray(new String[0]);
	}
	
	private static void exec(Class clazz, String classLocation, PrintStream stream) throws IOException, InterruptedException
	{
		String javaHome  = System.getProperty("java.home");
		String javaBin   = javaHome + "/bin/java";
		String classpath = FileUtils.getParentFolder(classLocation);//System.getProperty("java.class.path");
		String className = clazz.getCanonicalName();
		
		String outputLocation = FileUtils.getParentFolder(classLocation);
		
		Command command = new Command(Display.getDefault(), "\"" + javaBin + "\" -cp " + "\"" + classpath + "\" " + className, outputLocation);
		
		command.execute();
	}
	
	private static JavaCompiler getCompiler()
	{
		if (JavaLanguage.compiler == null)
		{
			JavaCompiler compiler = null;
			
			final String loc = "com.sun.tools.javac.api.JavacTool";
			
			File file = new File(CONFIG_DATA.get("jdk.location") + "/lib/tools.jar");
			
			try
			{
				URL[] urls = { file.toURI().toURL() };
				
				ClassLoader cl = URLClassLoader.newInstance(urls);
				
				compiler = Class.forName(loc, false, cl).asSubclass(JavaCompiler.class).newInstance();
			}
			catch (MalformedURLException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e)
			{
				e.printStackTrace();
			}
			
			JavaLanguage.compiler = compiler;
		}
		
		return JavaLanguage.compiler;
	}
	
	public static void compile(String fileLocation, String code, String outputLocation, final PrintStream stream, final ArrayList<CompilerListener> compilerListeners)
	{
		fileLocation = FileUtils.removeExtension(fileLocation);
		
		// TODO: Fix the issue with the /src/ possible ambiguities.
		String projectLocation = FileUtils.getPrecedingPath(fileLocation, "/src/");
		
		String className  = FileUtils.getPathRelativeTo(fileLocation, "/src/");
		
		String classLocation = projectLocation + "/bin/" + className;
		
		if (!FileUtils.fileExists(CONFIG_DATA.get("jdk.location")))
		{
			FileBrowseDialog jdkSearch = new FileBrowseDialog("Specify your JDK location.", "Location:", CONFIG_DATA.get("jdk.location"), FileBrowseDialog.DIRECTORY);
			
			String jdkLoc = jdkSearch.open();
			
			if (jdkLoc != null)
			{
				String location = FileUtils.removeEndingSlashes(jdkLoc.replace('\\', '/'));
			
				ArrowIDE.setConfigDataValue("jdk.location", location);
			}
			else
			{
				stream.println("You must specify a valid jdk to compile this program.");
				
				return;
			}
		}
		
		String classpath = getClasspath(projectLocation);
		
		String projSrc = projectLocation + "/src/";
		
		String children[] = FileUtils.listChildFiles(projSrc, ".java");
		
		String params[] = new String[5 + children.length];
		
		params[0] = CONFIG_DATA.get("jdk.location") + "/bin/javac";
		params[1] = "-d";
		params[2] = projectLocation + "/bin/";
		params[3] = "-cp";
		params[4] = classpath;
		
		for (int i = 0; i < children.length; i++)
		{
			params[5 + i] = children[i];
		}
		
		final Command c = new Command(Display.getDefault(), params, projSrc);
		
		final ArrayList<String> messages = new ArrayList<String>();
		
		ProgramListener programListener = new ProgramListener()
		{
			public void programTerminated(Program program)
			{
				
			}
			
			public void programStarted(Program program)
			{
				
			}
			
			public void messageReceived(String message)
			{
				messages.add(message);
			}
			
			public void errorMessageReceived(String message)
			{
				messageReceived(message);
			}
		};
		
		String fileName = FileUtils.getFileNameWithoutExtension(fileLocation);
		
		final String fileLoc = fileLocation;
		
		try
		{
			c.execute(fileName, programListener);
			
			c.addCommandListener(new CommandListener()
			{
				public void resultReceived(int result)
				{
					if (result == 0)
					{
						CompileOutput output = new CompileOutput(0, 0, 0, 0, "Successfully compiled.");
						
						for (int i = 0; i < compilerListeners.size(); i++)
						{
							CompilerEvent event = new CompilerEvent(new String[0], new CompileOutput[] { output }, stream, fileLoc);
							
							compilerListeners.get(i).compiled(event);
						}
					}
					else
					{
						StringBuilder message = new StringBuilder();
						
						for (int i = 0; i < messages.size(); i++)
						{
							message.append(messages.get(i));
						}
						
						CompileOutput output = new CompileOutput(0, 0, 0, result, message.toString());
						
						for (int i = 0; i < compilerListeners.size(); i++)
						{
							CompilerEvent event = new CompilerEvent(new String[0], new CompileOutput[] { output }, stream, fileLoc);
							
							compilerListeners.get(i).compiled(event);
						}
					}
				}
				
				public void commandExecuted()
				{
					
				}
			});
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
//		if (!CONFIG_DATA.containsKey("jdk.location") || !(new File(CONFIG_DATA.get("jdk.location")).isDirectory()))
//		{
//			FileBrowseDialog jdkSearch = new FileBrowseDialog("Specify your JDK location.", "Location:", FileBrowseDialog.DIRECTORY);
//			
//			String jdkLoc = jdkSearch.open();
//			
//			if (jdkLoc != null)
//			{
//				String location = FileUtils.removeEndingSlashes(jdkLoc.replace('\\', '/'));
//			
//				ArrowIDE.setConfigDataValue("jdk.location", location);
//			}
//			else
//			{
//				stream.println("You must specify a valid jdk to compile this program.");
//				
//				return;
//			}
//		}
//		
//		String fileName = FileUtils.getFileNameWithoutExtension(fileLocation);
//		
//		fileLocation = FileUtils.removeExtension(fileLocation);
//		
//		String projectLocation = FileUtils.getPrecedingPath(fileLocation, "/src/");
//		
//		outputLocation = projectLocation + "/bin/";
//		
//		try
//		{
//			outputLocation = FileUtils.getAbsolutePath(outputLocation) + "/";
//		}
//		catch (IOException e2)
//		{
//			e2.printStackTrace();
//		}
//		
//		new File(outputLocation).mkdirs();
//		
////		fileName = fileName == null ? "" : fileName;
////		
////		JavacJavaCompilerSettings settings = new JavacJavaCompilerSettings();
////		
////		org.apache.commons.jci.compilers.JavaCompiler compiler = new JavacJavaCompiler(settings);//new JavaCompilerFactory().createCompiler("javac");//new JavacJavaCompiler(settings);
////		
//////		org.apache.commons.jci.compilers.JavaCompiler compiler = new JavaCompilerFactory().createCompiler("eclipse");
////
////		MemoryResourceReader mrr = new MemoryResourceReader();
////		mrr.add("Test", code.getBytes());
////
////		MemoryResourceStore mrs = new MemoryResourceStore();
////		
////		
////		CompilationResult result = compiler.compile(new String[] { fileName }, mrr, mrs);
////		
////		return result.getErrors().length + " ";
//		
//		/*Creating dynamic java source code file object*/
//		
//		SimpleJavaFileObject fileObject  = new DynamicJavaSourceCodeObject(fileName, code);
//		
//		JavaFileObject javaFileObjects[] = new JavaFileObject[] { fileObject };
//		
//		/*Instantiating the java compiler*/
//		JavaCompiler compiler = getCompiler();
// 
//		/**
//		 * Retrieving the standard file manager from compiler object, which is used to provide
//		 * basic building block for customizing how a compiler reads and writes to files.
//		 *
//		 * The same file manager can be reopened for another compiler task.
//		 * Thus we reduce the overhead of scanning through file system and jar files each time
//		 */
//		StandardJavaFileManager stdFileManager = compiler.getStandardFileManager(null, Locale.getDefault(), null);
// 
//		/* Prepare a list of compilation units (java source code file objects) to input to compilation task*/
//		Iterable<? extends JavaFileObject> compilationUnits = Arrays.asList(javaFileObjects);
//		
//		String classpath = getClasspath(projectLocation);
// 
//		/*Prepare any compilation options to be used during compilation*/
//		//In this example, we are asking the compiler to place the output files under bin folder.
//		String[] compileOptions = new String[]
//		{
////			"-cp", classpath,
//			"-cp", classpath,
//			"-d", outputLocation
//		};
//		
//		Iterable<String> compilationOptions = Arrays.asList(compileOptions);
// 
////		if (true)return;
//		
//		/*Create a diagnostic controller, which holds the compilation problems*/
//		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
// 
//		/*Create a compilation task from compiler by passing in the required input objects prepared above*/
//		CompilationTask compilerTask = compiler.getTask(null, stdFileManager, diagnostics, compilationOptions, null, compilationUnits);
//		
//		//Perform the compilation by calling the call method on compilerTask object.
//		boolean status = compilerTask.call();
//		
//		final StringBuilder error = new StringBuilder();
//		
//		String outputFile = outputLocation + fileName + ".class";
//		
//		final String outputFiles[] = new String[] { outputFile };
//		
//		ArrayList<Diagnostic> ds = new ArrayList<Diagnostic>();
//		
//		ArrayList<String> errors = new ArrayList<String>();
//		
//		//If compilation error occurs
//		if (!status)
//		{
//			/*Iterate through each compilation problem and print it*/
//			for (Diagnostic d : diagnostics.getDiagnostics())
//			{
//				String err = d.getMessage(Locale.getDefault());
//				
//				errors.add(String.format("Error on line %d: %s", d.getLineNumber(), err));
//				
//				ds.add(d);
//			}
//			
//			String strs[] = errors.toArray(new String[0]);
//			
//			for (String str : strs)
//			{
//				error.append(str);
//			}
//			
//			Display.getDefault().syncExec(new Runnable()
//			{
//				public void run()
//				{
//					if (stream != null)
//					{
//						stream.println(error.toString());
//					}
//				}
//			});
//		}
//		
//		CompileOutput output = null;
//		
//		CompileOutput outputs[] = new CompileOutput[ds.size() > 0 ? ds.size() : 1];
//		
//		if (ds.size() > 0)
//		{
//			for (int i = 0; i < ds.size(); i++)
//			{
//				outputs[i] = new CompileOutput((int)ds.get(i).getStartPosition(), (int)ds.get(i).getEndPosition(), (int)ds.get(i).getLineNumber(), status ? 0 : 1, errors.get(i));
//			}
//		}
//		else
//		{
//			outputs[0] = new CompileOutput(0, 0, 0, status ? 0 : 1, "");
//		}
//		
//		for (int i = compilerListeners.size() - 1; i >= 0; i--)
//		{
//			CompilerEvent event = new CompilerEvent(outputFiles, outputs, stream, fileLocation);
//			
//			compilerListeners.get(i).compiled(event);
//		}
//		
//		try
//		{
//			stdFileManager.close();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}
}

/**
 * Creates a dynamic source code file object
 *
 * This is an example of how we can prepare a dynamic java source code for compilation.
 * This class reads the java code from a string and prepares a JavaFileObject
 *
 */
class DynamicJavaSourceCodeObject extends SimpleJavaFileObject
{
	private String qualifiedName;
	private String sourceCode;
 
	/**
	 * Converts the name to an URI, as that is the format expected by JavaFileObject
	 *
	 *
	 * @param fully qualified name given to the class file
	 * @param code the source code string
	 * @throws UnsupportedEncodingException 
	 */
	protected DynamicJavaSourceCodeObject(String name, String code)
	{
		super(URI.create("string:///" + name.replace(' ', '+').replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
		this.qualifiedName = name;
		this.sourceCode = code;
	}
 
	public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException
	{
		return sourceCode;
	}
 
	public String getQualifiedName()
	{
		return qualifiedName;
	}
 
	public void setQualifiedName(String qualifiedName)
	{
		this.qualifiedName = qualifiedName;
	}
 
	public String getSourceCode()
	{
		return sourceCode;
	}
 
	public void setSourceCode(String sourceCode)
	{
		this.sourceCode = sourceCode;
	}
}