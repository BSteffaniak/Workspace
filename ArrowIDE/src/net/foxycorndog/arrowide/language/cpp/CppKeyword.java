package net.foxycorndog.arrowide.language.cpp;

import static net.foxycorndog.arrowide.language.Language.CPP;
import static net.foxycorndog.arrowide.language.Language.GLSL;

import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.glsl.GLSLLanguage;
import net.foxycorndog.arrowide.language.java.JavaLanguage;

import static net.foxycorndog.arrowide.language.cpp.CppLanguage.KEYWORD_COLOR;
import static net.foxycorndog.arrowide.language.cpp.CppLanguage.INCLUDE_COLOR;

public class CppKeyword
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
		ABSTRACT     = new Keyword(CPP, "abstract", KEYWORD_COLOR);
		ASSERT       = new Keyword(CPP, "assert", KEYWORD_COLOR);
		BOOLEAN      = new Keyword(CPP, "boolean", KEYWORD_COLOR);
		BREAK        = new Keyword(CPP, "break", KEYWORD_COLOR);
		BYTE         = new Keyword(CPP, "byte", KEYWORD_COLOR);
		CASE         = new Keyword(CPP, "case", KEYWORD_COLOR);
		CATCH        = new Keyword(CPP, "catch, KEYWORD_COLOR");
		CHAR         = new Keyword(CPP, "char", KEYWORD_COLOR);
		CLASS        = new Keyword(CPP, "class", KEYWORD_COLOR);
		CONST        = new Keyword(CPP, "const", KEYWORD_COLOR);
		CONTINUE     = new Keyword(CPP, "continue", KEYWORD_COLOR);
		DEFAULT      = new Keyword(CPP, "default", KEYWORD_COLOR);
		DO           = new Keyword(CPP, "do", KEYWORD_COLOR);
		DOUBLE       = new Keyword(CPP, "double", KEYWORD_COLOR);
		ELSE         = new Keyword(CPP, "else", KEYWORD_COLOR);
		ENUM         = new Keyword(CPP, "enum", KEYWORD_COLOR);
		EXTENDS      = new Keyword(CPP, "extends", KEYWORD_COLOR);
		FINAL        = new Keyword(CPP, "final", KEYWORD_COLOR);
		FINALLY      = new Keyword(CPP, "finally", KEYWORD_COLOR);
		FLOAT        = new Keyword(CPP, "float", KEYWORD_COLOR);
		FOR          = new Keyword(CPP, "for", KEYWORD_COLOR);
		GOTO         = new Keyword(CPP, "goto", KEYWORD_COLOR);
		IF           = new Keyword(CPP, "if", KEYWORD_COLOR);
		IMPLEMENTS   = new Keyword(CPP, "implements", KEYWORD_COLOR);
		IMPORT       = new Keyword(CPP, "import", KEYWORD_COLOR);
		INCLUDE      = new Keyword(CPP, "#include", INCLUDE_COLOR);
		INSTANCEOF   = new Keyword(CPP, "instanceof", KEYWORD_COLOR);
		INT          = new Keyword(CPP, "int", KEYWORD_COLOR);
		INTERFACE    = new Keyword(CPP, "interface", KEYWORD_COLOR);
		LONG         = new Keyword(CPP, "long", KEYWORD_COLOR);
		NAMESPACE    = new Keyword(CPP, "namespace", KEYWORD_COLOR);
		NATIVE       = new Keyword(CPP, "native", KEYWORD_COLOR);
		NEW          = new Keyword(CPP, "new", KEYWORD_COLOR);
		PACKAGE      = new Keyword(CPP, "package", KEYWORD_COLOR);
		PRIVATE      = new Keyword(CPP, "private", KEYWORD_COLOR);
		PROTECTED    = new Keyword(CPP, "protected", KEYWORD_COLOR);
		PUBLIC       = new Keyword(CPP, "public", KEYWORD_COLOR);
		RETURN       = new Keyword(CPP, "return", KEYWORD_COLOR);
		SHORT        = new Keyword(CPP, "short", KEYWORD_COLOR);
		STATIC       = new Keyword(CPP, "static", KEYWORD_COLOR);
		STRICTFP     = new Keyword(CPP, "strictfp", KEYWORD_COLOR);
		SUPER        = new Keyword(CPP, "super", KEYWORD_COLOR);
		SWITCH       = new Keyword(CPP, "switch", KEYWORD_COLOR);
		SYNCHRONIZED = new Keyword(CPP, "synchronized", KEYWORD_COLOR);
		THIS         = new Keyword(CPP, "this", KEYWORD_COLOR);
		THROW        = new Keyword(CPP, "throw", KEYWORD_COLOR);
		THROWS       = new Keyword(CPP, "throws", KEYWORD_COLOR);
		TRANSIENT    = new Keyword(CPP, "transient", KEYWORD_COLOR);
		TRY          = new Keyword(CPP, "try", KEYWORD_COLOR);
		USING        = new Keyword(CPP, "using", KEYWORD_COLOR);
		VOID         = new Keyword(CPP, "void", KEYWORD_COLOR);
		VOLATILE     = new Keyword(CPP, "volatile", KEYWORD_COLOR);
		WHILE        = new Keyword(CPP, "while", KEYWORD_COLOR);
	}
}