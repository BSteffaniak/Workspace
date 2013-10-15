package net.foxycorndog.arrowide.language.c;

import static net.foxycorndog.arrowide.language.Language.C;
import static net.foxycorndog.arrowide.language.cpp.CppLanguage.INCLUDE_COLOR;
import static net.foxycorndog.arrowide.language.cpp.CppLanguage.KEYWORD_COLOR;
import net.foxycorndog.arrowide.language.Keyword;

public class CKeyword
{
	public static Keyword
			ABSTRACT, ASSERT, BOOLEAN, BREAK, BYTE, CASE, CATCH, CHAR,
			CLASS, CONST, CONTINUE, DEFAULT, DO, DOUBLE, ELSE,
			ENUM, EXTENDS, FALSE, FINAL, FINALLY, FLOAT, FOR,
			GOTO, IF, IMPLEMENTS, IMPORT, INCLUDE, INSTANCEOF, INT,
			INTERFACE, LONG, NAMESPACE, NATIVE, NEW, PACKAGE, PRIVATE,
			PROTECTED, PUBLIC, RETURN, SHORT, STATIC, STRICTFP,
			SUPER, SWITCH, SYNCHRONIZED, THIS, THROW, THROWS,
			TRANSIENT, TRUE, TRY, USING, VOID, VOLATILE, WHILE;
	
	public static void init()
	{
		ABSTRACT     = new Keyword(C, "abstract", KEYWORD_COLOR);
		ASSERT       = new Keyword(C, "assert", KEYWORD_COLOR);
		BOOLEAN      = new Keyword(C, "boolean", KEYWORD_COLOR);
		BREAK        = new Keyword(C, "break", KEYWORD_COLOR);
		BYTE         = new Keyword(C, "byte", KEYWORD_COLOR);
		CASE         = new Keyword(C, "case", KEYWORD_COLOR);
		CATCH        = new Keyword(C, "catch, KEYWORD_COLOR");
		CHAR         = new Keyword(C, "char", KEYWORD_COLOR);
		CLASS        = new Keyword(C, "class", KEYWORD_COLOR);
		CONST        = new Keyword(C, "const", KEYWORD_COLOR);
		CONTINUE     = new Keyword(C, "continue", KEYWORD_COLOR);
		DEFAULT      = new Keyword(C, "default", KEYWORD_COLOR);
		DO           = new Keyword(C, "do", KEYWORD_COLOR);
		DOUBLE       = new Keyword(C, "double", KEYWORD_COLOR);
		ELSE         = new Keyword(C, "else", KEYWORD_COLOR);
		ENUM         = new Keyword(C, "enum", KEYWORD_COLOR);
		EXTENDS      = new Keyword(C, "extends", KEYWORD_COLOR);
		FINAL        = new Keyword(C, "final", KEYWORD_COLOR);
		FINALLY      = new Keyword(C, "finally", KEYWORD_COLOR);
		FLOAT        = new Keyword(C, "float", KEYWORD_COLOR);
		FOR          = new Keyword(C, "for", KEYWORD_COLOR);
		GOTO         = new Keyword(C, "goto", KEYWORD_COLOR);
		IF           = new Keyword(C, "if", KEYWORD_COLOR);
		IMPLEMENTS   = new Keyword(C, "implements", KEYWORD_COLOR);
		IMPORT       = new Keyword(C, "import", KEYWORD_COLOR);
		INCLUDE      = new Keyword(C, "#include", INCLUDE_COLOR);
		INSTANCEOF   = new Keyword(C, "instanceof", KEYWORD_COLOR);
		INT          = new Keyword(C, "int", KEYWORD_COLOR);
		INTERFACE    = new Keyword(C, "interface", KEYWORD_COLOR);
		LONG         = new Keyword(C, "long", KEYWORD_COLOR);
		NAMESPACE    = new Keyword(C, "namespace", KEYWORD_COLOR);
		NATIVE       = new Keyword(C, "native", KEYWORD_COLOR);
		NEW          = new Keyword(C, "new", KEYWORD_COLOR);
		PACKAGE      = new Keyword(C, "package", KEYWORD_COLOR);
		PRIVATE      = new Keyword(C, "private", KEYWORD_COLOR);
		PROTECTED    = new Keyword(C, "protected", KEYWORD_COLOR);
		PUBLIC       = new Keyword(C, "public", KEYWORD_COLOR);
		RETURN       = new Keyword(C, "return", KEYWORD_COLOR);
		SHORT        = new Keyword(C, "short", KEYWORD_COLOR);
		STATIC       = new Keyword(C, "static", KEYWORD_COLOR);
		STRICTFP     = new Keyword(C, "strictfp", KEYWORD_COLOR);
		SUPER        = new Keyword(C, "super", KEYWORD_COLOR);
		SWITCH       = new Keyword(C, "switch", KEYWORD_COLOR);
		SYNCHRONIZED = new Keyword(C, "synchronized", KEYWORD_COLOR);
		THIS         = new Keyword(C, "this", KEYWORD_COLOR);
		THROW        = new Keyword(C, "throw", KEYWORD_COLOR);
		THROWS       = new Keyword(C, "throws", KEYWORD_COLOR);
		TRANSIENT    = new Keyword(C, "transient", KEYWORD_COLOR);
		TRY          = new Keyword(C, "try", KEYWORD_COLOR);
		USING        = new Keyword(C, "using", KEYWORD_COLOR);
		VOID         = new Keyword(C, "void", KEYWORD_COLOR);
		VOLATILE     = new Keyword(C, "volatile", KEYWORD_COLOR);
		WHILE        = new Keyword(C, "while", KEYWORD_COLOR);
	}
}