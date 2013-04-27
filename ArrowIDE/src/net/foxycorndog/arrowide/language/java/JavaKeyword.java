package net.foxycorndog.arrowide.language.java;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.Keyword;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import static net.foxycorndog.arrowide.language.Language.JAVA;
import static net.foxycorndog.arrowide.language.java.JavaLanguage.KEYWORD_COLOR;;

public class JavaKeyword
{
	public static Keyword
			ABSTRACT, ASSERT, BOOLEAN, BREAK, BYTE, CASE, CATCH, CHAR,
			CLASS, CONST, CONTINUE, DEFAULT, DO, DOUBLE, ELSE,
			ENUM, EXTENDS, FALSE, FINAL, FINALLY, FLOAT, FOR,
			GOTO, IF, IMPLEMENTS, IMPORT, INSTANCEOF, INT,
			INTERFACE, LONG, NATIVE, NEW, NULL, PACKAGE, PRIVATE,
			PROTECTED, PUBLIC, RETURN, SHORT, STATIC, STRICTFP,
			SUPER, SWITCH, SYNCHRONIZED, THIS, THROW, THROWS,
			TRANSIENT, TRUE, TRY, VOID, VOLATILE, WHILE;
	
	public static void init()
	{
		ABSTRACT     = new Keyword(JAVA, "abstract", KEYWORD_COLOR);
		ASSERT       = new Keyword(JAVA, "assert", KEYWORD_COLOR);
		BOOLEAN      = new Keyword(JAVA, "boolean", KEYWORD_COLOR);
		BREAK        = new Keyword(JAVA, "break", KEYWORD_COLOR);
		BYTE         = new Keyword(JAVA, "byte", KEYWORD_COLOR);
		CASE         = new Keyword(JAVA, "case", KEYWORD_COLOR);
		CATCH        = new Keyword(JAVA, "catch", KEYWORD_COLOR);
		CHAR         = new Keyword(JAVA, "char", KEYWORD_COLOR);
		CLASS        = new Keyword(JAVA, "class", KEYWORD_COLOR);
		CONST        = new Keyword(JAVA, "const", KEYWORD_COLOR);
		CONTINUE     = new Keyword(JAVA, "continue", KEYWORD_COLOR);
		DEFAULT      = new Keyword(JAVA, "default", KEYWORD_COLOR);
		DO           = new Keyword(JAVA, "do", KEYWORD_COLOR);
		DOUBLE       = new Keyword(JAVA, "double", KEYWORD_COLOR);
		ELSE         = new Keyword(JAVA, "else", KEYWORD_COLOR);
		ENUM         = new Keyword(JAVA, "enum", KEYWORD_COLOR);
		EXTENDS      = new Keyword(JAVA, "extends", KEYWORD_COLOR);
		FALSE        = new Keyword(JAVA, "false", KEYWORD_COLOR);
		FINAL        = new Keyword(JAVA, "final", KEYWORD_COLOR);
		FINALLY      = new Keyword(JAVA, "finally", KEYWORD_COLOR);
		FLOAT        = new Keyword(JAVA, "float", KEYWORD_COLOR);
		FOR          = new Keyword(JAVA, "for", KEYWORD_COLOR);
		GOTO         = new Keyword(JAVA, "goto", KEYWORD_COLOR);
		IF           = new Keyword(JAVA, "if", KEYWORD_COLOR);
		IMPLEMENTS   = new Keyword(JAVA, "implements", KEYWORD_COLOR);
		IMPORT       = new Keyword(JAVA, "import", KEYWORD_COLOR);
		INSTANCEOF   = new Keyword(JAVA, "instanceof", KEYWORD_COLOR);
		INT          = new Keyword(JAVA, "int", KEYWORD_COLOR);
		INTERFACE    = new Keyword(JAVA, "interface", KEYWORD_COLOR);
		LONG         = new Keyword(JAVA, "long", KEYWORD_COLOR);
		NATIVE       = new Keyword(JAVA, "native", KEYWORD_COLOR);
		NEW          = new Keyword(JAVA, "new", KEYWORD_COLOR);
		NULL         = new Keyword(JAVA, "null", KEYWORD_COLOR);
		PACKAGE      = new Keyword(JAVA, "package", KEYWORD_COLOR);
		PRIVATE      = new Keyword(JAVA, "private", KEYWORD_COLOR);
		PROTECTED    = new Keyword(JAVA, "protected", KEYWORD_COLOR);
		PUBLIC       = new Keyword(JAVA, "public", KEYWORD_COLOR);
		RETURN       = new Keyword(JAVA, "return", KEYWORD_COLOR);
		SHORT        = new Keyword(JAVA, "short", KEYWORD_COLOR);
		STATIC       = new Keyword(JAVA, "static", KEYWORD_COLOR);
		STRICTFP     = new Keyword(JAVA, "strictfp", KEYWORD_COLOR);
		SUPER        = new Keyword(JAVA, "super", KEYWORD_COLOR);
		SWITCH       = new Keyword(JAVA, "switch", KEYWORD_COLOR);
		SYNCHRONIZED = new Keyword(JAVA, "synchronized", KEYWORD_COLOR);
		THIS         = new Keyword(JAVA, "this", KEYWORD_COLOR);
		THROW        = new Keyword(JAVA, "throw", KEYWORD_COLOR);
		THROWS       = new Keyword(JAVA, "throws", KEYWORD_COLOR);
		TRANSIENT    = new Keyword(JAVA, "transient", KEYWORD_COLOR);
		TRUE         = new Keyword(JAVA, "true", KEYWORD_COLOR);
		TRY          = new Keyword(JAVA, "try", KEYWORD_COLOR);
		VOID         = new Keyword(JAVA, "void", KEYWORD_COLOR);
		VOLATILE     = new Keyword(JAVA, "volatile", KEYWORD_COLOR);
		WHILE        = new Keyword(JAVA, "while", KEYWORD_COLOR);
	}
}