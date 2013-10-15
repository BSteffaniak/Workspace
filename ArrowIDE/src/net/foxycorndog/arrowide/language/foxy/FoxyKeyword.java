package net.foxycorndog.arrowide.language.foxy;

import static net.foxycorndog.arrowide.language.Language.FOXY;
import static net.foxycorndog.arrowide.language.foxy.FoxyLanguage.KEYWORD_COLOR;
import net.foxycorndog.arrowide.language.Keyword;

public class FoxyKeyword
{
	public static Keyword
			INT;
	
	public static void init()
	{
		INT	= new Keyword(FOXY, "int", KEYWORD_COLOR);
	}
}