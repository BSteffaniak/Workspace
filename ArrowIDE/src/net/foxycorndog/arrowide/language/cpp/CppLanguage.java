package net.foxycorndog.arrowide.language.cpp;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.Program;
import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.command.CommandListener;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.event.CompilerEvent;
import net.foxycorndog.arrowide.event.CompilerListener;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.CompileOutput;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class CppLanguage
{
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public  static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0),
			INCLUDE_COLOR = new Color(Display.getCurrent(), 180, 180, 0);
	
	static
	{
		COMMENT_PROPERTIES    = new CommentProperties("//", "/*", "*/", new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES     = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties(' ', new char[] { '=' }, new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		CppKeyword.init();
	}
	
	public static Program run(final String fileLocation, final PrintStream stream)
	{
		int lastInd = fileLocation.lastIndexOf('.');
		lastInd = lastInd < 0 ? fileLocation.length() : lastInd;
		
		String exe  = "\"" + fileLocation.substring(0, lastInd) + ".exe\"";
		
		Command command = new Command(Display.getDefault(), exe, null);
		
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
		if (!CONFIG_DATA.containsKey("g++.location") || !new File(CONFIG_DATA.get("g++.location")).isDirectory())
		{
			FileBrowseDialog gppSearch = new FileBrowseDialog("Specify your c++ compiler. (EX: C:\\MinGW\\bin)", "Location:", FileBrowseDialog.EITHER);
			
			String gppLoc = gppSearch.open();
			
			if (gppLoc != null)
			{
				String location = FileUtils.removeEndingSlashes(gppLoc.replace('\\', '/'));
				
				ArrowIDE.setConfigDataValue("g++.location", location);
			}
			else
			{
				stream.println("You must specify a valid c++ compiler to compile this program.");
				
				return;
			}
		}
		
		try
		{
//			g++ hello.C -o hello
			String text = "\"" + CONFIG_DATA.get("g++.location") + "/g++\" \"" + fileLocation + "\" -o \"" + outputLocation + (outputLocation.equals("") ? "" : "/") + FileUtils.getFileNameWithoutExtension(fileLocation) + "\"";
			
			final Command command = new Command(Display.getDefault(), text, null);
			
			String outputFile = outputLocation + FileUtils.getFileName(fileLocation);
			
			final String outputFiles[] = new String[] { outputFile };
			
			command.addCommandListener(new CommandListener()
			{
				public void resultReceived(int result)
				{
					CompileOutput outputs[] = new CompileOutput[1];
					
					outputs[0] = new CompileOutput(0, 0, 0, result, "ASDF");
					
					System.out.println(command.getProgram().getText() + "!");
					
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
}