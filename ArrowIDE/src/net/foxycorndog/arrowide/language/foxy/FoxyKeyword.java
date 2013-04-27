package net.foxycorndog.arrowide.language.foxy;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.foxy.FoxyLanguage;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.arrowide.language.Language.FOXY;
import static net.foxycorndog.arrowide.language.foxy.FoxyLanguage.KEYWORD_COLOR;

public class FoxyKeyword
{
	public static Keyword
			INT;
	
	public static void init()
	{
		INT	= new Keyword(FOXY, "int", KEYWORD_COLOR);
	}
}