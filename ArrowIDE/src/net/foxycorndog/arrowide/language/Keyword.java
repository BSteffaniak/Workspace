package net.foxycorndog.arrowide.language;

import java.util.HashMap;
import java.util.HashSet;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.language.assembly.AssemblyKeyword;
import net.foxycorndog.arrowide.language.cpp.CppKeyword;
import net.foxycorndog.arrowide.language.foxy.FoxyKeyword;
import net.foxycorndog.arrowide.language.glsl.GLSLKeyword;
import net.foxycorndog.arrowide.language.java.JavaKeyword;
import net.foxycorndog.arrowide.language.php.PHPKeyword;

import org.eclipse.swt.graphics.Color;

import static net.foxycorndog.arrowide.language.Language.*;

public class Keyword
{
	private String													word;

	private Color													color;

	private static final HashMap<Integer, HashMap<String, Keyword>> keywords;
	
	static
	{
		keywords = new HashMap<Integer, HashMap<String, Keyword>>();
	}
	
	public static void addLanguage(int language)
	{
		keywords.put(language, new HashMap<String, Keyword>());
	}
	
	public Keyword(int language, String word)
	{
		this.word = word;
		
		keywords.get(language).put(word, this);
	}
	
	public Keyword(int language, String word, Color color)
	{
		this.word = word;
		
		keywords.get(language).put(word, this);
		
		setColor(color);
	}
	
	public String getWord()
	{
		return word;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public static boolean isKeyword(int language, String word)
	{
		if (!keywords.containsKey(language))
		{
			return false;
		}
		
		return keywords.get(language).containsKey(word);
	}
	
	public static Keyword getKeyword(int language, String word)
	{
		return keywords.get(language).get(word);
	}
}