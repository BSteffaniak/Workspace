package net.foxycorndog.arrowide.language.php;

import static net.foxycorndog.arrowide.language.Language.PHP;
import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.php.PHPLanguage;

public class PHPKeyword
{
	public static Keyword
			AS, IF, ELSE, ELSEIF, WHILE, PRINT, CLASS, EXTENDS, BREAK,
			NEW, RETURN, CONST, VAR, FUNCTION, THROW, TRY,CATCH, THIS,
			SELF, NULL, TRUE, PUBLIC, STATIC, FALSE, PROTECTED, SWITCH,
			CASE, BOOL, DEFAULT, FOR, FOREACH;
	
	public static void init()
	{
		AS			= new Keyword(PHP, "as", PHPLanguage.KEYWORD_COLOR);
		IF			= new Keyword(PHP, "if", PHPLanguage.KEYWORD_COLOR);
		ELSE		= new Keyword(PHP, "else", PHPLanguage.KEYWORD_COLOR);
		ELSEIF		= new Keyword(PHP, "elseif", PHPLanguage.KEYWORD_COLOR);
		WHILE		= new Keyword(PHP, "while", PHPLanguage.KEYWORD_COLOR);
		PRINT		= new Keyword(PHP, "print", PHPLanguage.KEYWORD_COLOR);
		CLASS		= new Keyword(PHP, "class", PHPLanguage.KEYWORD_COLOR);
		EXTENDS		= new Keyword(PHP, "extends", PHPLanguage.KEYWORD_COLOR);
		BREAK		= new Keyword(PHP, "break", PHPLanguage.KEYWORD_COLOR);
		NEW			= new Keyword(PHP, "new", PHPLanguage.KEYWORD_COLOR);
		RETURN		= new Keyword(PHP, "return", PHPLanguage.KEYWORD_COLOR);
		CONST		= new Keyword(PHP, "const", PHPLanguage.KEYWORD_COLOR);
		VAR			= new Keyword(PHP, "var", PHPLanguage.KEYWORD_COLOR);
		FUNCTION	= new Keyword(PHP, "function", PHPLanguage.KEYWORD_COLOR);
		THROW		= new Keyword(PHP, "throw", PHPLanguage.KEYWORD_COLOR);
		TRY			= new Keyword(PHP, "try", PHPLanguage.KEYWORD_COLOR);
		CATCH		= new Keyword(PHP, "catch", PHPLanguage.KEYWORD_COLOR);
		THIS		= new Keyword(PHP, "$this", PHPLanguage.KEYWORD_COLOR);
		SELF		= new Keyword(PHP, "self", PHPLanguage.KEYWORD_COLOR);
		NULL		= new Keyword(PHP, "null", PHPLanguage.KEYWORD_COLOR);
		TRUE		= new Keyword(PHP, "true", PHPLanguage.KEYWORD_COLOR);
		PUBLIC		= new Keyword(PHP, "public", PHPLanguage.KEYWORD_COLOR);
		STATIC		= new Keyword(PHP, "static", PHPLanguage.KEYWORD_COLOR);
		FALSE		= new Keyword(PHP, "false", PHPLanguage.KEYWORD_COLOR);
		PROTECTED	= new Keyword(PHP, "protected", PHPLanguage.KEYWORD_COLOR);
		SWITCH		= new Keyword(PHP, "switch", PHPLanguage.KEYWORD_COLOR);
		CASE		= new Keyword(PHP, "case", PHPLanguage.KEYWORD_COLOR);
		BOOL		= new Keyword(PHP, "bool", PHPLanguage.KEYWORD_COLOR);
		DEFAULT		= new Keyword(PHP, "default", PHPLanguage.KEYWORD_COLOR);
		FOR			= new Keyword(PHP, "for", PHPLanguage.KEYWORD_COLOR);
		FOREACH		= new Keyword(PHP, "foreach", PHPLanguage.KEYWORD_COLOR);
	}
}