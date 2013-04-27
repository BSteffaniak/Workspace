package net.foxycorndog.arrowide.language.python;

import static net.foxycorndog.arrowide.language.Language.PYTHON;
import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.python.PythonLanguage;

public class PythonKeyword
{
	public static Keyword
			AND, AS, ASSERT, BREAK, CLASS, CONTINUE, DEF, DEL, ELIF, ELSE,
			EXCEPT, EXEC, FINALLY, FOR, FROM, GLOBAL, IF, IMPORT, IN, IS,
			LAMBDA, NOT, OR, PASS, RAISE, RETURN, TRY, WHILE, WITH,
			YEILD;
	
	public static void init()
	{
		AND = new Keyword(PYTHON, "and", PythonLanguage.KEYWORD_COLOR);
		AS = new Keyword(PYTHON, "as", PythonLanguage.KEYWORD_COLOR);
		ASSERT = new Keyword(PYTHON, "assert", PythonLanguage.KEYWORD_COLOR);
		BREAK = new Keyword(PYTHON, "break", PythonLanguage.KEYWORD_COLOR);
		CLASS = new Keyword(PYTHON, "class", PythonLanguage.KEYWORD_COLOR);
		CONTINUE = new Keyword(PYTHON, "continue", PythonLanguage.KEYWORD_COLOR);
		DEF = new Keyword(PYTHON, "def", PythonLanguage.KEYWORD_COLOR);
		DEL = new Keyword(PYTHON, "del", PythonLanguage.KEYWORD_COLOR);
		ELIF = new Keyword(PYTHON, "elif", PythonLanguage.KEYWORD_COLOR);
		ELSE = new Keyword(PYTHON, "else", PythonLanguage.KEYWORD_COLOR);
		EXCEPT = new Keyword(PYTHON, "except", PythonLanguage.KEYWORD_COLOR);
		EXEC = new Keyword(PYTHON, "exec", PythonLanguage.KEYWORD_COLOR);
		FINALLY = new Keyword(PYTHON, "finally", PythonLanguage.KEYWORD_COLOR);
		FOR			= new Keyword(PYTHON, "for", PythonLanguage.KEYWORD_COLOR);
		FROM = new Keyword(PYTHON, "from", PythonLanguage.KEYWORD_COLOR);
		GLOBAL = new Keyword(PYTHON, "global", PythonLanguage.KEYWORD_COLOR);
		IF = new Keyword(PYTHON, "if", PythonLanguage.KEYWORD_COLOR);
		IMPORT = new Keyword(PYTHON, "import", PythonLanguage.KEYWORD_COLOR);
		IN = new Keyword(PYTHON, "in", PythonLanguage.KEYWORD_COLOR);
		IS = new Keyword(PYTHON, "is", PythonLanguage.KEYWORD_COLOR);
		LAMBDA = new Keyword(PYTHON, "lambda", PythonLanguage.KEYWORD_COLOR);
		NOT = new Keyword(PYTHON, "not", PythonLanguage.KEYWORD_COLOR);
		OR = new Keyword(PYTHON, "or", PythonLanguage.KEYWORD_COLOR);
		PASS = new Keyword(PYTHON, "pass", PythonLanguage.KEYWORD_COLOR);
		RAISE = new Keyword(PYTHON, "raise", PythonLanguage.KEYWORD_COLOR);
		RETURN = new Keyword(PYTHON, "return", PythonLanguage.KEYWORD_COLOR);
		TRY = new Keyword(PYTHON, "try", PythonLanguage.KEYWORD_COLOR);
		WHILE = new Keyword(PYTHON, "while", PythonLanguage.KEYWORD_COLOR);
		WITH = new Keyword(PYTHON, "with", PythonLanguage.KEYWORD_COLOR);
		YEILD = new Keyword(PYTHON, "yeild", PythonLanguage.KEYWORD_COLOR);
	}
}