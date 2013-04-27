package net.foxycorndog.arrowide.dialog;

import java.util.ArrayList;
import java.util.HashMap;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.components.TitleBar;
import net.foxycorndog.arrowide.components.treemenu.TreeMenu;
import net.foxycorndog.arrowide.components.treemenu.TreeMenuListener;
import net.foxycorndog.arrowide.components.window.Window;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;

import static net.foxycorndog.arrowide.ArrowIDE.CONFIG_DATA;

/**
 * Dialog for each of the IDE's properties. Just like the Properties
 * Panel.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 20, 2013 at 6:32:47 PM
 * @since	v0.7
 * @version	v0.7
 */
public class PreferencesDialog extends PanelledDialog
{
	public PreferencesDialog(Composite parent)
	{
		super(parent);
	}
}