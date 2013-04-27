package net.foxycorndog.arrowide.language.python;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.Program;
import net.foxycorndog.arrowide.command.Command;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;
import net.foxycorndog.arrowide.language.python.PythonKeyword;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

public class PythonLanguage
{
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	static
	{
		COMMENT_PROPERTIES    = new CommentProperties(new String[] { "#" }, new String[] {  }, new String[] {  }, new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES     = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties(new char[][] { { 0 } }, new char[][] { { '=' } }, new char[][] { { ' ' } }, new char[][] { { ' '} }, new String[][] {  }, new String[][] {  }, new String[][] { {  }, { "in" } }, new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		PythonKeyword.init();
	}
	
	public static Program run(String fileLocation, PrintStream stream)
	{
		if (!CONFIG_DATA.containsKey("python.location") || !(new File(CONFIG_DATA.get("python.location")).isDirectory()))
		{
			FileBrowseDialog pythonSearch = new FileBrowseDialog("Specify your Python location.", "Location:", FileBrowseDialog.DIRECTORY);
			
			String pythonLoc = pythonSearch.open();
			
			if (pythonLoc != null)
			{
				String location = FileUtils.removeEndingSlashes(pythonLoc.replace('\\', '/'));
			
				ArrowIDE.setConfigDataValue("python.location", location);
			}
			else
			{
				stream.println("You must specify a valid python directory to compile this program.");
				
				return null;
			}
		}
		
		Command command = new Command(Display.getDefault(), new String[] { CONFIG_DATA.get("python.location") + "/python", fileLocation }, FileUtils.getParentFolder(fileLocation));
		
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
}