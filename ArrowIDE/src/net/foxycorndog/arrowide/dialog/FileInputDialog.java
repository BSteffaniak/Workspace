package net.foxycorndog.arrowide.dialog;

import java.io.File;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.file.FileUtils;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.ShellListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class FileInputDialog extends TextInputDialog
{
	private boolean directory;
	
	private String  preLocation;
	
	public FileInputDialog(String windowInstruction, String textFieldInstruction, boolean directory, String preLocation, boolean overwrite)
	{
		this(windowInstruction, textFieldInstruction, "", directory, preLocation, overwrite);
	}
	
	public FileInputDialog(String windowInstruction, String textFieldInstruction, String defaultTextField, final boolean directory, final String preLocation, boolean overwrite)
	{
		super(windowInstruction, textFieldInstruction, defaultTextField);
		
		this.directory   = directory;
		this.preLocation = preLocation;
		
		addDialogFilter(new DialogFilter()
		{
			public String filter(String text)
			{
				String location = text.replace("\\", "/");
				
				if (location.length() <= 0)
				{
					return "You must enter the " + (directory ? "directory" : "file") + " name.";
				}
				
				location = FileUtils.removeEndingSlashes(preLocation) + "/" + FileUtils.removeEndingSlashes(location);
				
				File file = new File(location);
				
				if (!file.exists())
				{
					setText(location);
				}
				else
				{
					return "A " + (directory ? "directory" : "file") + " already exists at that location.";
				}
				
				return null;
			}
		});
	}
	
	public boolean isDirectory()
	{
		return directory;
	}
	
	public String getPreLocation()
	{
		return preLocation;
	}
}