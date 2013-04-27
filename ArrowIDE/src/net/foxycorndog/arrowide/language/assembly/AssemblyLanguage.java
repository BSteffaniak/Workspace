package net.foxycorndog.arrowide.language.assembly;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;
import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.internal.C;
import org.eclipse.swt.widgets.Display;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.Program;
import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.command.CommandListener;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.dialog.PreferencesDialog;
import net.foxycorndog.arrowide.dialog.preferencesdialogpanel.AssemblyPanel;
import net.foxycorndog.arrowide.event.CompilerEvent;
import net.foxycorndog.arrowide.event.CompilerListener;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.CompileOutput;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;
import net.foxycorndog.arrowide.language.foxy.FoxyKeyword;

public class AssemblyLanguage
{
	private static String						lastFile;
	
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	public static final int FASM = 1, NASM = 2, MASM = 3;
	
	static
	{
		COMMENT_PROPERTIES    = new CommentProperties(";", new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES     = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties((char)0, ' ', new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		AssemblyKeyword.init();
	}
	
	public static Program run(String fileLocation, PrintStream stream)
	{
		if (PROPERTIES.get("os.name").equals("macosx"))
		{
			throw new UnsupportedOperationException("Running assembly on macosx is unsupported.");
		}
		
		String compilerName = CONFIG_DATA.get("assembly.compiler");
		
		boolean bit16Supported = false;//PROPERTIES.get("os.arch").equals(32) || compilerName.equals("FASM");
		
		Command command = null;
		
		String loc = FileUtils.removeExtension(fileLocation);
		
		String name = FileUtils.getFileNameWithoutExtension(fileLocation);
		
		int compiler = getCompilerValue(compilerName);
		
		String fileEnding = compiler == FASM ? ".exe" : ".com";
		
		if (compiler == MASM)
		{
			command = new Command(Display.getDefault(), "\"" + loc + ".exe" + "\"", null);
		}
		else
		{
			if (bit16Supported)
			{
				command = new Command(Display.getDefault(), "\"" + loc + fileEnding + "\"", null);
			}
			else
			{
				if (!CONFIG_DATA.containsKey("dosbox.location") || !new File(CONFIG_DATA.get("dosbox.location")).exists())
				{
					FileBrowseDialog gppSearch = new FileBrowseDialog("Specify your DOSBox location.", "Location:", FileBrowseDialog.FILE);
					
					String dosboxLoc = gppSearch.open();
					
					if (dosboxLoc != null)
					{
						ArrowIDE.setConfigDataValue("dosbox.location", dosboxLoc);
					}
					else
					{
						stream.println("You must specify a valid dosbox to run this 16 bit program.");
						
						return null;
					}
				}
				
				String dosboxLocation = CONFIG_DATA.get("dosbox.location");
				String confLocation   = (String)PROPERTIES.get("arrowide.location") + "/res/assembly";
				String fileLoc        = FileUtils.getParentFolder(fileLocation);
				
				if (!fileLocation.equals(lastFile))
				{
					try
					{
					    PrintWriter out = new PrintWriter((new FileWriter(confLocation + "/dosbox.conf")));
					    out.println("[autoexec]\r\n" +
					    		"mount c \"" + fileLoc + "\"\r\n" +
					    		"c:\r\n" +
					    		"cls\r\n" +
					    		name + "\r\n" +
					    		"exit");
					    out.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
				lastFile = fileLocation;
				
				command = new Command(Display.getDefault(), new String[] { dosboxLocation, "-conf '" + confLocation + "/dosbox.conf'", "-noconsole" }, confLocation);//new String[] { "\"C:/Program Files (x86)/DOSBox-0.74/DOSBox\"", "-name '" + FileUtils.getParentFolder(loc) + "'", "-noconsole" }, stream);
	//			try
	//			{
	////				Runtime.getRuntime().exec(new String[] { "\"C:/Program Files (x86)/DOSBox-0.74/DOSBox\"", "-conf \"" + PROPERTIES.get("arrowide.location") + "/res/assembly/new.conf\"", "-noconsole", "-printconf" });
	//				Runtime.getRuntime().exec(new String[] { "res/DOSBox", "-c mount c", "-noconsole" });
	//			}
	//			catch (IOException e)
	//			{
	//				e.printStackTrace();
	//			}
			}
		}
		
		String fileName = FileUtils.getFileNameWithoutExtension(fileLocation);
		
		try
		{
			command.execute(fileName);
			
			return command.getProgram();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void compile(final String fileLocation, String outputLocation, final PrintStream stream, final ArrayList<CompilerListener> compilerListeners)
	{
//		if (PROPERTIES.get("os.name").equals("macosx"))
//		{
//			throw new UnsupportedOperationException("Compiling assembly on macosx is unsupported.");
//		}
		
		if (!CONFIG_DATA.containsKey("assembly.compiler") || CONFIG_DATA.get("assembly.compiler").equals(""))
		{
			PreferencesDialog.getDefault().openToPanel(AssemblyPanel.class);
			
			return;
		}
		
		try
		{
			String text[] = null;
			
			final String fileName = FileUtils.getFileNameWithoutExtension(fileLocation);
			
			String compilerName = "";
			
			if (CONFIG_DATA.containsKey("assembly.compiler"))
			{
				compilerName = CONFIG_DATA.get("assembly.compiler");
			}
			
//			String fileEnding = compilerName.equals("FASM") ? ".exe" : ".img";
			String fileEnding = ".obj";
			
			final int compiler = getCompilerValue(compilerName);
			
			String resLoc = (String)PROPERTIES.get("arrowide.location");
			
			String outputFile = null;
			final String compilerLocation = getCompilerLocation(compiler, resLoc);
			
			if (compiler == NASM)
			{
				String inputFile        = "\"" + fileLocation + "\"";
				outputFile              = "\"" + outputLocation + fileName + fileEnding + "\"";
				
				text = new String[] { compilerLocation, "-f bin", inputFile, " -o " + outputFile, "-w+orphan-labels" };
			}
			else if (compiler == FASM)
			{
				String inputFile        = "\"" + fileLocation + "\"";
				outputFile              = "\"" + outputLocation + fileName + fileEnding + "\"";
				
				text = new String[] { compilerLocation, inputFile, outputFile };
			}
			else if (compiler == MASM)
			{
				String inputFile        = "\"" + fileLocation + "\"";
				outputFile              = "\"" + outputLocation + fileName + fileEnding + "\"";
				
				text = new String[] { '"' + compilerLocation + "ml" + PROPERTIES.get("os.executable.extension") + '"', "/c", "/coff", fileName + ".asm" };
			}
			
			final String directory = FileUtils.getParentFolder(fileLocation);
			
			final String outputFiles[] = new String[] { outputFile.replace("\"", "") };
			
			Command command = new Command(Display.getDefault(), text, directory);
			
			command.addCommandListener(new CommandListener()
			{
				public void resultReceived(final int result)
				{
					CompileOutput outputs[] = new CompileOutput[1];
					
					outputs[0] = new CompileOutput(0, 0, 0, result, "ASDF");
					
					for (int i = compilerListeners.size() - 1; i >= 0; i--)
					{
						CompilerEvent event = new CompilerEvent(outputFiles, outputs, stream, fileLocation);
						
						compilerListeners.get(i).compiled(event);
					}
				}
								
				public void commandExecuted()
				{
//					if (result == 0)
//					{
						if (compiler == MASM)
						{
							String text[] = new String[] { '"' + compilerLocation + "link" + PROPERTIES.get("os.executable.extension") + '"', "/SUBSYSTEM:CONSOLE", "/LIBPATH:C:/masm32/lib", fileName + ".obj" };
							
							Command command = new Command(Display.getDefault(), text, directory);
							
							command.addCommandListener(new CommandListener()
							{
								public void resultReceived(final int result)
								{
									CompileOutput outputs[] = new CompileOutput[1];
									
									outputs[0] = new CompileOutput(0, 0, 0, result, "ASDF");
									
									for (int i = compilerListeners.size() - 1; i >= 0; i--)
									{
										CompilerEvent event = new CompilerEvent(outputFiles, outputs, stream, fileLocation);
										
										compilerListeners.get(i).compiled(event);
									}
								}
								
								public void commandExecuted()
								{
									
								}
							});
							
							try
							{
								command.execute();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
//					}
				}
			});
			
			command.execute();
						
			
//			if (p.exitValue() == 0)
//			{
//				result = "Compiled successfully!!!";
//			}
//			else
//			{
//				result = "";
//			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static final String getCompilerLocation(int compiler, String resLoc)
	{
		if (compiler == NASM)
		{
			return '"' + resLoc + "/res/assembly/nasm/" + PROPERTIES.get("os.name") + "/nasm" + PROPERTIES.get("os.executable.extension") + "\"";
		}
		else if (compiler == FASM)
		{
			return '"' + resLoc + "/res/assembly/fasm/" + PROPERTIES.get("os.name") + "/fasm" + PROPERTIES.get("os.executable.extension") + "\"";
		}
		else if (compiler == MASM)
		{
			return resLoc + "/res/assembly/masm/" + PROPERTIES.get("os.name") + "/";
		}
		
		return null;
	}
	
	private static final int getCompilerValue(String compilerName)
	{
		if (compilerName.equals("NASM"))
		{
			return NASM;
		}
		else if (compilerName.equals("FASM"))
		{
			return FASM;
		}
		else if (compilerName.equals("MASM"))
		{
			return MASM;
		}
		
		return 0;
	}
}