package net.foxycorndog.arrowide.language.foxy;

import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.MethodProperties;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class FoxyLanguage
{
	public  static final CommentProperties		COMMENT_PROPERTIES;
	public  static final MethodProperties		METHOD_PROPERTIES;
	public  static final IdentifierProperties	IDENTIFIER_PROPERTIES;
	
	public static final Color
			KEYWORD_COLOR = new Color(Display.getCurrent(), 150, 0, 0);
	
	static
	{
		COMMENT_PROPERTIES    = new CommentProperties("//", "/*", "*/", new Color(Display.getCurrent(), 40, 140, 0));
		METHOD_PROPERTIES     = new MethodProperties();
		IDENTIFIER_PROPERTIES = new IdentifierProperties(' ', new char[] { '=', ';' }, new Color(Display.getDefault(), 4, 150, 120));
	}
	
	public static void init()
	{
		FoxyKeyword.init();
	}
}