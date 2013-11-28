package net.foxycorndog.arrowide.components;

import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.formatter.Formatter;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.Language;
import net.foxycorndog.arrowide.language.MethodProperties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.custom.VerifyKeyListener;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * Class that extends StyledText, but colors the text according
 * to the language.
 * 
 * @author	Braden Steffaniak
 * @since	Feb 13, 2013 at 4:53:31 PM
 * @since	v0.7
 * @version	v0.7
 */
public class CodeField extends StyledText
{
	private boolean										syntaxUpdaterRunning;
	private boolean										commentStarted, textStarted;
	private boolean										isEscape;
	private boolean										redrawReady;
	private boolean										autoUpdate;

	private char										textBeginning;

	private int											commentType, commentStartLocation;
	private int											textBeginningLocation;
	private int											lineNumberOffset;
	private int											language;
	private int											charWidth;
	private int											numThreads;
	private int											escapeCount;
	
	private float										widthPercent, heightPercent;

	private String										text;

	private StringBuilder								commentTransText;

	private CommentProperties							commentProperties;
	private MethodProperties							methodProperties;
	private IdentifierProperties						identifierProperties;

	private Listener									identifierSelectorListener;
	
	private Thread										syntaxUpdater;

	private LineStyleListener							lineNumbers, lineSpaces, syntaxHighlighting;
	
	private LineNumberPanel								lineNumberPanel;
	
	private StyledText									lineNumberText;

	private Composite									composite;

	private CodeField									thisField;

	private StyleRange									styles[];

	// private ArrayList<ArrayList<Boolean>> tabs;

	private ArrayList<ErrorLocation>					errorLocations;
	private ArrayList<ContentListener>					contentListeners;
	private ArrayList<CodeFieldListener>				codeFieldListeners;
	private ArrayList<Integer>							scopeStartLocations, scopeEndLocations;
	private ArrayList<WordRange>						idRanges, methodRanges;

	private HashMap<String, WordList>					identifierLists;
	private HashMap<WordList, String>					identifierWords;
	private HashMap<String, WordList>					methodLists;
	private HashMap<WordList, String>					methodWords;
	
	private static int									ids;

	private static final String							whitespaceRegex;

	private static final char							whitespaceArray[];
	
	static
	{
//		whitespaceRegex = "[.,[ ]/*=()\r\n\t\\[\\]{};[-][+]['][\"]:[-][+]><!]";
		whitespaceRegex = "[.,[ ]/*=()\r\n\t[\\\\]\\[\\]{};[-][+]['][\"]:[-][+]><!]";
		
		whitespaceArray = new char[] { ' ', '.', ',', '/', '*', '=', '(', ')', '[', ']', '{', '}', ';', '\n', '\t', '\r', '-', '\\', '+', '\'', '"', ':', '-', '+', '>', '<', '!' };
	}
	
	/**
	 * Private class that holds the location of the start and the end
	 * of an error within the text field.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 13, 2013 at 4:54:46 PM
	 * @since	v0.7
	 * @version	v0.7
	 */
	private class ErrorLocation
	{
		private int start, end;
		
		public ErrorLocation(int start, int end)
		{
			this.start = start;
			this.end   = end;
		}
	}
	
	/**
	 * Class that holds the StyleRanges for several words in the
	 * text field.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 13, 2013 at 5:02:28 PM
	 * @since	v0.7
	 * @version	v0.7
	 */
	private class WordList
	{
		private HashMap<WordLocation, WordRange> styles;
		
		public WordList()
		{
			styles = new HashMap<WordLocation, WordRange>();
		}
		
		public void add(WordRange loc)
		{
			styles.put(new WordLocation(loc.range.start, loc.range.length), loc);
		}
		
		public WordRange[] stylesToArray()
		{
			return styles.values().toArray(new WordRange[0]);
		}
		
		public WordLocation[] locationsToArray()
		{
			return styles.keySet().toArray(new WordLocation[0]);
		}
		
		public boolean containsWordLocation(int start, int length)
		{
			return containsWordLocation(new WordLocation(start, length));
		}
		
		public boolean containsWordLocation(WordLocation loc)
		{
			WordLocation arr[] = locationsToArray();
			
			for (int i = 0; i < arr.length; i++)
			{
				if (arr[i].length == loc.length && arr[i].start == loc.start)
				{
					return true;
				}
			}
			
			return false;
		}
		
		public StyleRange getWordStyle(WordLocation loc)
		{
			WordLocation arr[] = locationsToArray();
			
			for (int i = 0; i < arr.length; i++)
			{
				if (arr[i].length == loc.length && arr[i].start == loc.start)
				{
					return styles.get(arr[i]).range;
				}
			}
			
			return null;
		}
		
		public int size()
		{
			return styles.size();
		}
	}
	
	/**
	 * Class that holds the information of a word, such as the
	 * start index and end index.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 13, 2013 at 5:03:43 PM
	 * @since	v0.7
	 * @version	v0.7
	 */
	private class WordLocation
	{
		private int start, length;
		
		public WordLocation(int start, int length)
		{
			this.start  = start;
			this.length = length;
		}
	}
	
	/**
	 * A word and a StyleRange mixed into one class!!!
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 13, 2013 at 6:48:15 PM
	 * @since	v0.7
	 * @version	v0.7
	 */
	private class WordRange
	{
		private boolean		isDefinition;
		
		private int			scopeStartLocation;
		private int			id;
		
		private String		word;
		private StyleRange	range;
		
		public WordRange(String word, StyleRange range, boolean isDefinition)
		{
			this.isDefinition = isDefinition;
			
			this.word  = word;
			this.range = range;
			
			if (isDefinition)
			{
				id = ++ids;
			}
		}
	}
	
	/**
	 * Class that holds the information from the calculateSpaceBetween
	 * method.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Feb 13, 2013 at 6:49:34 PM
	 * @since	v0.7
	 * @version	v0.7
	 */
	private class SpaceBetweenResult
	{
		private char	firstCharOtherThanSpace;
		
		private int		count;
		
		private char	chars[];
		
		public boolean containsWhitespace()
		{
			return containsWhitespace(new char[0]);
		}
		
		public boolean containsWhitespace(char exceptions[])
		{
			for (int i = 0; i < chars.length; i++)
			{
				char c = chars[i];
				
				if (c != '\t' && c != ' ')
				{
					boolean pass = false;
					
					for (int j = 0; j < exceptions.length; j++)
					{
						if (c == exceptions[j])
						{
							pass = true;
						}
					}
					
					if (!pass)
					{
						return false;
					}
				}
			}
			
			return true;
		}
	}
	
	/**
	 * Instantiates all of the variables needed for the CodeField.
	 * 
	 * @param comp The parent Composite to place it in.
	 */
	public CodeField(final Composite comp)
	{
		super(comp, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | (Integer)PROPERTIES.get("composite.modifiers"));
		
		thisField = this;
		
		this.composite     = comp;
		
		errorLocations     = new ArrayList<ErrorLocation>();
		contentListeners   = new ArrayList<ContentListener>();
		codeFieldListeners = new ArrayList<CodeFieldListener>();
		
		identifierLists    = new HashMap<String, WordList>();
		identifierWords    = new HashMap<WordList, String>();
		methodLists        = new HashMap<String, WordList>();
		methodWords        = new HashMap<WordList, String>();
		
		syntaxHighlighting = new LineStyleListener()
	    {
			public void lineGetStyle(LineStyleEvent event)
			{
				StyleRange styles[] = thisField.getStyles();
				
				event.styles = styles;
			}
	    };
	    
	    syntaxUpdater = new Thread()
		{
			public void run()
			{
				syntaxUpdaterRunning = true;
				
				if (language > 0)
				{
					Display.getDefault().syncExec(new Runnable()
					{
						public void run()
						{
							text = getText();//.replace("\\", "\\\\");
						}
					});
					
					generateScopes();
					
					createSyntaxStyles();
					createSyntaxStyles();
				}
				else
				{
					clearRanges();
					clearScopes();
					
					identifierLists.clear();
					methodLists.clear();
					identifierWords.clear();
					methodWords.clear();
					
					if (methodRanges != null)
					{
						methodRanges.clear();
						idRanges.clear();
					}
				}
				
				clearErrors();
				
				select();
				
				Display.getDefault().syncExec(new Runnable()
				{
					public void run()
					{
						thisField.redraw();
					}
				});
				
				syntaxUpdaterRunning = false;
			}
		};
	    
	    addLineStyleListener(syntaxHighlighting);
		
		setText("");
		setBounds(new Rectangle(0, 0, 100, 100));
	    setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1));
	    
	    int fontSize = 10;
	    
	    if (PROPERTIES.get("os.name").equals("macosx"))
	    {
	    	fontSize = 15;
	    }
	    
	    Font f = FileUtils.loadMonospacedFont(Display.getDefault(), "courier new", PROPERTIES.get("resources.location") + "res/fonts/CECOUR.ttf", fontSize, SWT.NORMAL);
	    setFont(f);
	    
//	    tabs = new ArrayList<ArrayList<Boolean>>();
//	    tabs.add(new ArrayList<Boolean>());
	    setTabs(4);
//		setAlwaysShowScrollBars(false);
	    
//	    highlightSyntax();
	    
	    redrawReady = true;
	    
		comp.addControlListener(new ControlListener()
		{
			public void controlResized(ControlEvent e)
			{
				if (autoUpdate)
				{
					updateSize();
				}
			}
			
			public void controlMoved(ControlEvent e)
			{
				
			}
		});
	    
	    identifierSelectorListener = new Listener()
	    {
			public void handleEvent(final Event e)
			{
				if (idRanges == null)
				{
					return;
				}
				
				final int caretOffset = thisField.getCaretOffset();
				
				new Thread()
				{
					public void run()
					{
						WordRange identifier = null;
						
						for (int i = 0; i < idRanges.size(); i++)
						{
							StyleRange loc = idRanges.get(i).range;
							
							if (caretOffset >= loc.start && caretOffset <= loc.start + loc.length)
							{
								identifier = idRanges.get(i);
							}
							else
							{
								loc.borderStyle = SWT.NONE;
							}
						}
						
						WordRange method = null;
						
						for (int i = 0; i < methodRanges.size(); i++)
						{
							StyleRange loc = methodRanges.get(i).range;
							
							if (caretOffset >= loc.start && caretOffset <= loc.start + loc.length)
							{
								method = methodRanges.get(i);
							}
							else
							{
								loc.borderStyle = SWT.NONE;
							}
						}
						
						if (identifier != null)
						{
							setIdentifierSelected(identifier);
						}
						else if (method != null)
						{
							setMethodSelected(method);
						}
						
						if (e == null || e.type == SWT.MouseDown)
						{
							Display.getDefault().syncExec(new Runnable()
							{
								public void run()
								{
									redrawRange(0, thisField.getText().length(), true);
								}
							});
						}
					}
				}.start();
				
				comp.redraw();
			}
	    };
	    
	    addListener(SWT.MouseDown, identifierSelectorListener);
	    addListener(SWT.KeyDown, identifierSelectorListener);
	    
		addTraverseListener(new TraverseListener()
		{
			public void keyTraversed(TraverseEvent e)
			{
				if (e.detail == SWT.TRAVERSE_TAB_PREVIOUS)
				{
					e.doit = false;
				}
			}
		});
	    
	    addVerifyKeyListener(new VerifyKeyListener()
		{
			public void verifyKey(VerifyEvent e)
			{
				Point range = getSelection();
				
				int lines = getLineAtOffset(range.y) - getLineAtOffset(range.x);
				
				if (e.character == '\t' && lines > 0)
				{
					if ((e.stateMask & SWT.SHIFT) != 0)
					{
						Formatter.unIndent(thisField);
					}
					else
					{
						Formatter.indent(thisField);
					}
					
					e.doit = false;
				}
				else if (e.character == '/' && (e.stateMask & (Integer)PROPERTIES.get("key.control")) != 0)
				{
					Formatter.outcomment(thisField);
					
					e.doit = false;
				}
				else if (e.character == 6 && (e.stateMask & (Integer)PROPERTIES.get("key.control")) != 0 && (e.stateMask & SWT.SHIFT) != 0)
				{
					Formatter.format(thisField);

					contentChanged();
				}
				else if (e.character == 1 && (e.stateMask & (Integer)PROPERTIES.get("key.control")) != 0)
				{
					int pos = getTopPixel();
					
					selectAll();
					
					setTopPixel(pos);
				}
			}
		});
	    
	    addKeyListener(new KeyListener()
	    {
			public void keyPressed(KeyEvent e)
			{
//				System.out.println(e.keyCode);
//				if (getSelectionCount() > 0)
//				{
//					selectionLines  = getLineAtOffset(getSelectionCount() + getSelection().x) - getLineAtOffset(getSelection().x);
//					selectionLength = getSelectionCount();
//				}
//				else
//				{
//					selectionLines  = 0;
//					selectionLength = 0;
//				}
//				
////				int xPosition     = getCaretXPosition();
				int caretPosition = getCaretPosition();
				int lineNum       = getCaretLineNumber();
				
				if (isPrintable(e.character) || e.keyCode == 13 || e.keyCode == 127 || e.keyCode == SWT.BS || e.keyCode == SWT.CR || e.character == '\t')
				{
					contentChanged();
				}
				
				/*
				 * Carry tabs if new line.
				 */
				if (e.character == '\r' || e.character == '\n')
				{
					String tabsStr = "";
					
					int tabsCount  = lineTabCount(lineNum, 1);
					
					char lastChar  = lastChar(lineNum, 1);
					tabsCount     += tabIncrease(lastChar);
					
					for (int i = 0; i < tabsCount; i ++)
					{
						tabsStr += "\t";
					}
					
					insert(tabsStr);
					
					setCaretOffset(caretPosition + tabsStr.length());
					
					if (scopeStartLocations != null && scopeStartLocations.size() > scopeEndLocations.size())
					{
						if (lastChar == '{')
						{
							String endingBrace = "\r\n" + tabsStr.substring(0, tabsStr.length() - 1) + "}";
							
							insert(endingBrace);
						}
					}
				}
				
				CodeFieldEvent event = new CodeFieldEvent(e.character, e.stateMask, e.keyCode, thisField);
				
				for (int i = codeFieldListeners.size() - 1; i >= 0; i --)
				{
					codeFieldListeners.get(i).keyPressed(event);
				}
			}

			public void keyReleased(KeyEvent e)
			{
				
			}
	    });
	}
	
	public void select()
	{
//		Display.getDefault().syncExec(new Runnable()
//		{
//			public void run()
//			{
				identifierSelectorListener.handleEvent(null);
//			}
//		});
	}
	
	public void setIdentifierSelected(WordRange word)
	{
		WordRange list[] = identifierLists.get(word.word).stylesToArray();
		
		for (int i = 0; i < list.length; i++)
		{
			if (list[i].id == word.id)
			{
				StyleRange loc = list[i].range;
				
				loc.borderStyle = SWT.BORDER_DASH;
			}
		}
	}
	
	public void setMethodSelected(WordRange word)
	{
		WordRange list[] = methodLists.get(word.word).stylesToArray();
		
		for (int i = 0; i < list.length; i++)
		{
			if (list[i].id == word.id)
			{
				StyleRange loc = list[i].range;
				
				loc.borderStyle = SWT.BORDER_DASH;
			}
		}
	}
	
	public void highlightSyntax()
	{
		if (syntaxUpdaterRunning)
		{
//			try
//			{
//				syntaxUpdater.join(0);
//			System.out.println("DONE--");
//			}
//			catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
		}
		
		if (syntaxUpdater != null && syntaxUpdater.isAlive())
		{
			try
			{
				syntaxUpdater.join(1);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		syntaxUpdater.run();
	}
	
	public void createSyntaxStyles()
	{
		if (methodProperties == null || identifierProperties == null)
		{
			clearRanges();
			clearScopes();
			
			return;
		}
		
		HashMap<String, WordList> tempIdLists     = null;
		HashMap<String, WordList> tempMethodLists = null;
		HashMap<WordList, String> tempIdWords     = null;
		HashMap<WordList, String> tempMethodWords = null;
		
		tempIdLists     = copyString(identifierLists);
		tempMethodLists = copyString(methodLists);
		tempIdWords     = copyWordList(identifierWords);
		tempMethodWords = copyWordList(methodWords);
		
		idRanges     = new ArrayList<WordRange>();
		methodRanges = new ArrayList<WordRange>();
		
		identifierLists.clear();
		methodLists.clear();
		identifierWords.clear();
		methodWords.clear();
		
//		setStyleRange(styleRange);
//		setStyleRanges(new StyleRange[] { styleRange });
		
		ArrayList<StyleRange> styles = new ArrayList<StyleRange>();
		
		String strings[] = text.split("\\s*" + whitespaceRegex + "+\\s*");//whitespaceRegex);
		int    offsets[] = new int[strings.length];
		
		int charCount    = 0;
		
		commentTransText = new StringBuilder();
		
		commentStarted   = false;
		
		commentType          = 0;
		commentStartLocation = 0;
		
		isEscape    = false;
		
		escapeCount = 0;
		
		
		SpaceBetweenResult newResult, oldResult;
		
		newResult = calculateSpaceBetween(text, 0, whitespaceArray, styles);
		oldResult = newResult;
		
		for (int i = 0; i < strings.length; i++)
		{
			String prevWord = i > 0 ? strings[i - 1] : "";
			String word     = strings[i];
			String nextWord = "";
			
			if (i < strings.length - 1)
			{
				nextWord = strings[i + 1];
			}
			
			offsets[i] = charCount;
			
			boolean isKeyword    = Keyword.isKeyword(language, word);
		
			boolean alreadyAdded = false;
			
			if (commentStarted || textStarted)
			{
				
			}
			else if (isKeyword)
			{
				Keyword keyword  = Keyword.getKeyword(language, word);
				
				int offset       = offsets[i];
				int length       = word.length();
				
				StyleRange range = new StyleRange(offset, length, keyword.getColor(), null);
				
				addStyleRange(styles, range);
			}
			else if (tempIdLists.containsKey(word))
			{
				if (!identifierLists.containsKey(word))
				{
					if (tempIdLists.containsKey(word))
					{
						identifierLists.put(word, tempIdLists.get(word));
					}
					else
					{
						identifierLists.put(word, new WordList());
					}
				}
				
				int offset = offsets[i];
				int length = word.length();
				
				WordList     list = tempIdLists.get(word);
				
				WordLocation loc  = new WordLocation(offset, length);
				
				synchronized (list)
				{
//					System.out.println(word + newResult.firstCharOtherThanSpace + "!" + charCount);
					
					StyleRange range = null;
					
					if (list.containsWordLocation(loc))
					{
						range = list.getWordStyle(loc);
					}
					else
					{
						range = new StyleRange(offset, length, new Color(Display.getDefault(), 4, 150, 120), null);
					}
					
					boolean isDefinition = false;
					
					if (oldResult.containsWhitespace())
					{
						if (!prevWord.equals("return"))//!Keyword.isKeyword(language, prevWord))
						{
							isDefinition = true;
						}
					}
					else
					{
						if (idRanges.size() > 0)
						{
							WordRange id = idRanges.get(idRanges.size() - 1);
							
							if (id.isDefinition)
							{
								isDefinition = oldResult.containsWhitespace(new char[] { ',' });
							}
						}
					}
					
					boolean isGlobal = prevWord.equals("this") && oldResult.firstCharOtherThanSpace == '.' && oldResult.chars.length == 1;
					
					WordRange wordRange = new WordRange(word, range, isDefinition);
					wordRange.scopeStartLocation = getScopeStartLocation(charCount);
					
					int id = 0;
					
					if (!isDefinition)
					{
						for (int j = idRanges.size() - 1; j >= 0; j--)
						{
							WordRange r = idRanges.get(j);
							
							if (r.word.equals(word))
							{
								if (r.isDefinition)
								{
									if (isGlobal && getScopeDepth(r.range.start) == 1 || !isGlobal)
									{
										if (isDefinitionOf(r, wordRange))
										{
											id = r.id;
											
											break;
										}
									}
								}
							}
						}
					}
					
					if (id == 0)
					{
						id = ++ids;
					}
					
					wordRange.id        = id;
						
					idRanges.add(wordRange);

					addStyleRange(styles, range);
						
					identifierWords.put(list, word);
				}
				
				alreadyAdded = true;
			}
			
			boolean textWasStarted    = textStarted;
			boolean commentWasStarted = commentStarted;
			
			newResult = calculateSpaceBetween(text, charCount + word.length(), whitespaceArray, styles);
			
			charCount += word.length() + newResult.count;
			
			StyleRange lastStyle = null;
			
			int lastEnd = 0;
			
			if (styles.size() > 0)
			{
				lastStyle = styles.get(styles.size() - 1);
				
				if (lastStyle != null)
				{
					lastEnd = lastStyle.start + lastStyle.length;
				}
			}
			
//			for (int j = 0; j < errorLocations.size(); j++)
//			{
//				if (lastStyle == null)
//				{
//					break;
//				}
//				
//				ErrorLocation loc = errorLocations.get(j);
//				
//				int length = loc.end - loc.start;
//				
//				if (loc.start > lastEnd && newResult.count > 0)
//				{
//					StyleRange range = new StyleRange();
//					
//					int offsetStart  = length <= 1 && !isPrintable(text.charAt(loc.start)) ? 1 : 0;
//					int offsetLength = length == 0 ? (offsetStart == 1 ? 1 : 0) : 0;
//					
//					range.start     = loc.start - offsetStart;
//					range.length    = loc.end - loc.start + offsetLength;
//					range.underline = true;
//					range.underlineStyle = SWT.UNDERLINE_SQUIGGLE;
//					range.underlineColor = new Color(Display.getDefault(), 240, 0, 0);
//					
//					styles.add(range);
//				}
//			}
			
			if (isKeyword || (commentWasStarted) || textWasStarted)//commentStartLocation < offsets[i] || textStartLocation < offsets[i])
			{
				
			}
			else if (newResult.firstCharOtherThanSpace == '(')
			{
				int offset = offsets[i];
				int length = word.length();
				
				if (tempMethodLists.containsKey(word))
				{
					if (!methodLists.containsKey(word))
					{
						if (tempMethodLists.containsKey(word))
						{
							methodLists.put(word, tempMethodLists.get(word));
						}
						else
						{
							methodLists.put(word, new WordList());
						}
					}
					
					WordList list    = tempMethodLists.get(word);
					
					WordLocation loc = new WordLocation(offset, length);
					
					synchronized (list)
					{
						StyleRange range = null;
						
						if (list.containsWordLocation(loc))
						{
							range = list.getWordStyle(loc);
							
							methodWords.put(list, word);
						}
						else
						{
							range = new StyleRange(offset, length, methodProperties.COLOR, null);
						}
						
						boolean isDefinition = false;
						
						if (oldResult.firstCharOtherThanSpace == '\t' || oldResult.firstCharOtherThanSpace == 0)
						{
//							if (Keyword.isKeyword(language, prevWord))
							{
								isDefinition = true;
							}
						}
						
						WordRange wordRange = new WordRange(word, range, isDefinition);
						
						if (isDefinition)
						{
							wordRange.scopeStartLocation = getScopeStartLocation(charCount);
						}
							
						methodRanges.add(wordRange);
						addStyleRange(styles, range);
						
						if (!list.containsWordLocation(loc))
						{
							tempMethodLists.get(word).add(wordRange);
						}
					}
				}
				else
				{
					StyleRange range = new StyleRange(offset, length, methodProperties.COLOR, null);
					
					WordList locs = new WordList();
					
					boolean isDefinition = false;
					
					if (oldResult.firstCharOtherThanSpace == '\t' || oldResult.firstCharOtherThanSpace == 0)
					{
//						if (Keyword.isKeyword(language, prevWord))
						{
							isDefinition = true;
						}
					}
					
					WordRange wordRange = new WordRange(word, range, isDefinition);
					
					if (isDefinition)
					{
						wordRange.scopeStartLocation = getScopeStartLocation(charCount);
					}
						
					methodRanges.add(wordRange);
					locs.add(wordRange);
					
					methodLists.put(word, locs);
					methodWords.put(locs, word);
					tempMethodLists.put(word, locs);
					tempMethodWords.put(locs, word);

					addStyleRange(styles, range);
				}
			}
			else if (identifierProperties != null)
			{
				if (!alreadyAdded && identifierProperties.isQualified(oldResult.chars, newResult.chars, word, prevWord, nextWord))
				{
					int offset = offsets[i];
					int length = word.length();
					
					boolean added = false;
					
					if (tempIdLists.containsKey(word))
					{
						if (!identifierLists.containsKey(word))
						{
							if (tempIdLists.containsKey(word))
							{
								identifierLists.put(word, tempIdLists.get(word));
							}
							else
							{
								identifierLists.put(word, new WordList());
							}
						}
						
						WordList     list  = tempIdLists.get(word);
						
						WordLocation loc   = new WordLocation(offset, length);
						
						StyleRange   range = null;
						
						if (list.containsWordLocation(loc))
						{
							range = list.getWordStyle(loc);

							identifierWords.put(list, word);

							addStyleRange(styles, range);
						}
						else
						{
							range = new StyleRange(offset, length, new Color(Display.getDefault(), 4, 150, 120), null);

							addStyleRange(styles, range);
						}
						
						boolean isDefinition = false;
						
						if (newResult.firstCharOtherThanSpace == '\t' || newResult.firstCharOtherThanSpace == 0)
						{
							if (!Keyword.isKeyword(language, prevWord))
							{
								isDefinition = true;
							}
						}
						
						WordRange wordRange = new WordRange(word, range, isDefinition);
						
						if (isDefinition)
						{
							wordRange.scopeStartLocation = getScopeStartLocation(charCount);
						}
							
						idRanges.add(wordRange);
						
						if (!list.containsWordLocation(loc))
						{
							tempIdLists.get(word).add(wordRange);
						}
					}
					else
					{
						StyleRange range = new StyleRange(offset, length, identifierProperties.COLOR, null);

						addStyleRange(styles, range);
						
						WordList locs = new WordList();
						
						boolean isDefinition = false;
						
						if (newResult.firstCharOtherThanSpace == '\t' || newResult.firstCharOtherThanSpace == 0)
						{
							if (!Keyword.isKeyword(language, prevWord))
							{
								isDefinition = true;
							}
						}
						
						WordRange wordRange = new WordRange(word, range, isDefinition);
						
						if (isDefinition)
						{
							wordRange.scopeStartLocation = getScopeStartLocation(charCount);
						}
							
						idRanges.add(wordRange);
						locs.add(wordRange);
						
						identifierLists.put(word, locs);
						identifierWords.put(locs, word);
						tempIdLists.put(word, locs);
						tempIdWords.put(locs, word);
					}
				}
			}
			
			oldResult = newResult;
		}
		
		clearRanges();
		
		for (int i = 0; i < idRanges.size(); i++)
		{
			WordList list = identifierLists.get(idRanges.get(i).word);
			
			if (list == null)
			{
				continue;
			}
			
			list.add(idRanges.get(i));
		}
		
		for (int i = 0; i < methodRanges.size(); i++)
		{
			WordList list = methodLists.get(methodRanges.get(i).word);
			
			if (list == null)
			{
				continue;
			}
			
			list.add(methodRanges.get(i));
		}
		
		StyleRange range = null;
		
		if ((range = endComment(text.length() - commentStartLocation + 1)) != null)
		{
			addStyleRange(styles, range);
		}
		
		else if ((range = endText(text.length() - commentStartLocation)) != null)
		{
			addStyleRange(styles, range);
		}
		
		for (int j = 0; j < errorLocations.size(); j++)
		{
			int i = 1;
			
			while (i < styles.size())
			{
				StyleRange lastStyle = styles.get(i - 1);
				StyleRange currentStyle = styles.get(i);
				
				int lastEnd = lastStyle.start + lastStyle.length;
				
				ErrorLocation loc = errorLocations.get(j);
				
				int length = loc.end - loc.start;
				
				if (loc.end >= lastEnd && loc.start <= currentStyle.start)
				{
					StyleRange newRange = new StyleRange();
					
					int offsetStart  = length == 0 ? 1 : 0;
					
					int newStart  = Math.max(lastEnd, loc.start);
					int newLength = Math.min(currentStyle.start - newStart + offsetStart, loc.end - newStart + offsetStart);
					
					newRange.start     = newStart;//loc.start - offsetStart;
					newRange.length    = newLength;//loc.end - loc.start + offsetLength;
					newRange.underline = true;
					newRange.underlineStyle = SWT.UNDERLINE_SQUIGGLE;
					newRange.underlineColor = new Color(Display.getDefault(), 240, 0, 0);
					
//					System.out.println("Adding to (" + i + ")" + newStart + ", " + newLength);
					
					// TODO: Potential error with replacing the next lastStyle with the new style.
					styles.add(i++, newRange);
					
					if (loc.end < currentStyle.start)
					{
						break;
					}
				}
				
				i++;
			}
		}
		
		setStyles((StyleRange[])styles.toArray(new StyleRange[0]));
	}
	
	private HashMap<WordList, String> copyWordList(HashMap<WordList, String> map)
	{
		HashMap<WordList, String> copy = new HashMap<WordList, String>();
		
		Set<WordList> keys = copy.keySet();
		
		Iterator<WordList> i = keys.iterator();
		
		while (i.hasNext())
		{
			WordList key = i.next();
			
			copy.put(key, map.get(key));
		}
		
		return copy;
	}
	
	private HashMap<String, WordList> copyString(HashMap<String, WordList> map)
	{
		HashMap<String, WordList> copy = new HashMap<String, WordList>();
		
		Set<String> keys = copy.keySet();
		
		Iterator<String> i = keys.iterator();
		
		while (i.hasNext())
		{
			String key = i.next();
			
			copy.put(key, map.get(key));
		}
		
		return copy;
	}
	
	private void generateScopes()
	{
		scopeStartLocations = new ArrayList<Integer>();
		scopeEndLocations   = new ArrayList<Integer>();
		
		int index = 0;
		
		while (true)
		{
			int index1 = text.indexOf('{', index + 1);
			int index2 = text.indexOf('}', index + 1);
			int index3 = text.indexOf('(', index + 1);
			int index4 = text.indexOf(')', index + 1);
			
			index1 = index1 < 0 ? Integer.MAX_VALUE : index1;
			index2 = index2 < 0 ? Integer.MAX_VALUE : index2;
			index3 = index3 < 0 ? Integer.MAX_VALUE : index3;
			index4 = index4 < 0 ? Integer.MAX_VALUE : index4;
			
			int min = Math.min(index1, Math.min(index2, Math.min(index3, index4)));
			
			index   = min;
			
			if (min >= Integer.MAX_VALUE)
			{
				break;
			}
			
			char c = text.charAt(min);
			
			if (c == '{' || c == '(')
			{
//				if (c == '(' && !isMethodDefinition(min - 1))
//				{
//					System.out.println("def not " + (min - 1));
//					index = index4;
//				}
//				else
				{
					scopeStartLocations.add(scopeStartLocations.size(), min);
				}
			}
			else
			{
//				if (scopeEndLocations.size() >= scopeStartLocations.size())
//				{
////					System.out.println("def not2 " + (min - 1));
//				}
//				else
				{
					scopeEndLocations.add(scopeEndLocations.size(), min);
				}
			}
		}
	}
	
	private void clearScopes()
	{
		if (scopeStartLocations != null)
		{
			scopeStartLocations.clear();
		} 
		if (scopeEndLocations != null)
		{
			scopeEndLocations.clear();
		}
	}
	
	private void clearRanges()
	{
		setStyles(new StyleRange[0]);
		
		if (idRanges != null)
		{
			for (int i = 0; i < idRanges.size(); i++)
			{
				WordList list = identifierLists.get(idRanges.get(i).word);
				
				if (list == null)
				{
					continue;
				}
				
				list.styles.clear();
			}
		}
		
		if (methodRanges != null)
		{
			for (int i = 0; i < methodRanges.size(); i++)
			{
				WordList list = methodLists.get(methodRanges.get(i).word);
				
				if (list == null)
				{
					continue;
				}
				
				list.styles.clear();
			}
		}
	}
	
	private void addStyleRange(ArrayList<StyleRange> styles, StyleRange range)
	{
		for (int i = 0; i < errorLocations.size(); i++)
		{
			ErrorLocation loc = errorLocations.get(i);
			
			if (range.start + range.length >= loc.start && loc.end >= range.start)
			{
				range.underline = true;
				range.underlineStyle = SWT.UNDERLINE_SQUIGGLE;
				range.underlineColor = new Color(Display.getDefault(), 240, 0, 0);
			}
		}
		
		styles.add(range);
	}
	
	private StyleRange endText(int length)
	{
		StyleRange style = null;
		
		if (textStarted)
		{
			textStarted = false;
			
			{
				int offset = textBeginningLocation;
				
				style = new StyleRange(offset, length, new Color(Display.getDefault(), 180, 100, 30), null);
			}
			
			textBeginning = 0;
		}
		
		return style;
	}
	
	private StyleRange endComment(int length)
	{
		StyleRange style = null;
		
		if (commentStarted)
		{
			commentStarted = false;
			
			{
				int offset = commentStartLocation;
				
				style = new StyleRange(offset, length, commentProperties.COLOR, null);
			}
			
			commentType = 0;
			
			commentTransText = new StringBuilder();
		}
		
		return style;
	}
	
	private int getScopeDepth(int location)
	{
		if (scopeStartLocations.size() <= 0)
		{
			return 0;
		}
		
		int index = 0;
		int i     = 0;
		int scope = 0;
		
		do
		{
			int start = scopeStartLocations.get(i);
			
			index = start;
			
			++scope;
			
			++i;
		}
		while (location > index);
		
		index = 0;
		i     = 0;
		
		do
		{
			int start = scopeEndLocations.get(i);
			
			index = start;
			
			--scope;
			
			++i;
		}
		while (location > index);
		
		return scope;
	}
	
	private int getScopeStartLocation(int location)
	{
		if (scopeStartLocations.size() <= 0)
		{
			return -1;
		}
		
		int index = 0;
		int i     = scopeStartLocations.size() + 0;
		
		do
		{
			i--;
			
			int start = scopeStartLocations.get(i);
			
			index = start;
		}
		while (location < index && i > 0);
		
		return scopeStartLocations.get(i);
	}
	
	private boolean isDefinitionOf(WordRange definitionCandidate, WordRange identifier)
	{
		if (identifier.scopeStartLocation < definitionCandidate.scopeStartLocation)
		{
			if (getScopeDepth(definitionCandidate.range.start) == 1)
			{
				return true;
			}
			
			return false;
		}
		
		int delta = 0;
		
//		int i     = scopeStartLocations.size() - 1;
//		
//		while (scopeStartLocations.get(i) > identifier.range.start)
//		{
//			System.out.println("a: " + scopeStartLocations.get(i) + " " + i + " " + scopeStartLocations.size());
//			i--;
//		}
//		
//		System.out.println(scopeStartLocations.get(i));
		
//		while (i > 0)
//		{
//			// If it is a field then skip.
//			if (i > 1 && scopeEndLocations.get(i) > definitionCandidate.scopeStartLocation)
//			{
//				delta++;
//			}
//			if (scopeStartLocations.get(i) > definitionCandidate.scopeStartLocation)
//			{
//				if (delta > 0)
//				{
//					delta--;
//				}
//			}
//			System.out.println(identifier.word + " " + scopeStartLocations.get(i) + " " + definitionCandidate.scopeStartLocation);
//			
//			i--;
//		}
		
		int start = scopeStartLocations.size() - 1;
		
		while (start >= 0 && scopeStartLocations.get(start) >= identifier.range.start)
		{
			start--;
		}
		
		if (start < 0)
		{
			start = 0;
		}
		
		int end = scopeEndLocations.size() - 1;
		
		while (end >= 0 && scopeEndLocations.get(end) >= identifier.range.start)
		{
			end--;
		}
		
		if (start < 0)
		{
			end = 0;
		}
		
		//System.out.println(identifier.word + " " + identifier.range.start + " " + delta);
		
		return delta <= 0;
	}
	
	private boolean isMethodDefinition(int location)
	{
		if (methodRanges == null)
		{
			return false;
		}
		
		for (int i = methodRanges.size() - 1; i >= 0; i--)
		{
			WordRange r = methodRanges.get(i);
			
			if (r.range.start <= location && r.range.start + r.range.length > location)
			{
				return r.isDefinition;
			}
		}
		
		return false;
	}
	
	private SpaceBetweenResult calculateSpaceBetween(String text, int start, char chars[], ArrayList<StyleRange> styles)
	{
		boolean noOtherCharOtherThanSpace = false;
		
		SpaceBetweenResult result = new SpaceBetweenResult();
		
		result.count = 0;
		
		char prevChar = 0;
		
		ArrayList<Character> cs = new ArrayList<Character>();
		
		for (int i = start; i < text.length(); i ++)
		{
			char c = text.charAt(i);
			
			if (i > 0)
			{
				prevChar = text.charAt(i - 1);
			}
			
			if (commentProperties != null && !textStarted)
			{
				commentTransText.append(c);
				
				boolean isTrans =
						(!commentStarted && commentProperties.startsToStartComment(commentTransText.toString()))
						|| (commentStarted && commentProperties.startsToEndComment(commentTransText.toString()));
				
				if (!isTrans)
				{
					commentTransText = new StringBuilder();
				}
				
				int type = 0;
				
				if (!commentStarted && (type = commentProperties.startsComment(commentTransText.toString())) != 0)
				{
					commentStarted = true;
					
					commentType    = type;
					
					commentStartLocation = start + result.count - commentTransText.length() + 1;
					
					commentTransText = new StringBuilder();
				}
				else if (commentStarted && (type = commentProperties.endsComment(commentTransText.toString())) != 0)
				{
					styles.add(endComment(start + result.count - commentStartLocation + 1));
				}
				
				if (c == '\n')
				{
					if (commentStarted && commentType == CommentProperties.SINGLE_LINE)
					{
						styles.add(endComment(start + result.count - commentStartLocation + 1));
					}
				}
			}
			
			if (!commentStarted)
			{
				if (c == '\\')
				{
					escapeCount++;
				}
				
				isEscape = escapeCount % 2 == 1;
				
				if (!isEscape && (c == '"' || c == '\''))
				{
					if (!textStarted)
					{
						textStarted = true;
						textBeginning = c;
						textBeginningLocation = start + result.count;
					}
					else
					{
						if (c == textBeginning)
						{
							textStarted = false;
							
							{
								int offset = textBeginningLocation;
								int length = start + result.count - offset + 1;
								
								styles.add(new StyleRange(offset, length, new Color(Display.getDefault(), 180, 100, 30), null));
							}
							
							textBeginning = 0;
						}
					}
				}
				
				if (c != '\\')
				{
					escapeCount = 0;
				}
			}
			
			if (containsChar(chars, c))
			{
				result.count++;
				
				cs.add(c);
				
				// Possible bug here with isEscape?? dont know...
				if (c != ' ' && !isEscape)
				{
					
					if (result.firstCharOtherThanSpace == 0)
					{
						result.firstCharOtherThanSpace = c;
					}
				}
			}
			else
			{
				char chs[] = new char[cs.size()];
				
				for (int l = 0; l < chs.length; l++)
				{
					chs[l] = cs.get(l);
//					System.out.print(chs[l] + ", ");
				}
//				System.out.println();
				result.chars = chs;
				
				return result;
			}
		}
		
		char chs[] = new char[cs.size()];
		
		for (int i = 0; i < chs.length; i++)
		{
			chs[i] = cs.get(i);
		}
		
		result.chars = chs;
		
		return result;
	}
	
	private boolean containsChar(char chars[], char key)
	{
		for (int i = 0; i < chars.length; i ++)
		{
			if (key == chars[i])
			{
				return true;
			}
		}
		
		return false;
	}
	
	private int getCaretLineNumber()
	{
		return getLineAtOffset(getCaretPosition());
	}
	
	private int getCaretPosition()
	{
		return getCaretOffset();
	}
	
	private char lastCharacter(int charactersBack)
	{
		int textLength = getText().length();
		
		if (textLength >= charactersBack)
		{
			return getText().charAt(textLength - charactersBack);
		}
		
		return 0;
	}
	
	private boolean lastCharacterIsTab(int lineNum, int charactersBack)
	{
		String line = getLine(lineNum);
		
		int size    = line.length();
		
		if (size >= charactersBack)
		{
			return line.charAt(size - charactersBack - 1) == '\t';
		}
		else if (size == 0)
		{
			return true;
		}
		
		return false;
	}
	
	private char lastChar(int lineNumber, int linesBack)
	{
		String line = getLine(lineNumber - linesBack).toLowerCase();
		
		if (line.length() > 0)
		{
			return line.charAt(line.length() - 1);
		}
		
		return 0;
	}
	
	private int tabIncrease(char c)
	{
		if (c == '{')
		{
			return 1;
		}
		
		return 0;
	}
	
	private int lineTabCount(int lineNumber, int linesBack)
	{
		String line = getLine(lineNumber - linesBack);
		
		int size    = line.length();
		
		for (int i = 0; i < size; i ++)
		{
			if (line.charAt(i) != '\t')
			{
				return i;
			}
		}
		
		return size;
	}
	
	public boolean isPrintableChar(char c)
	{
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
	    
	    return ((!Character.isISOControl(c)) &&
	            c != java.awt.event.KeyEvent.CHAR_UNDEFINED &&
	            block != null &&
	            block != Character.UnicodeBlock.SPECIALS) ||
	            (c == '\n' || c == '\t' || c == '\r');
	}
	
	private boolean isPrintable(char c)
	{
//		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
//		
//		return (!Character.isISOControl(c) && block != null && block != Character.UnicodeBlock.SPECIALS);
		
		return (((int)c >= 32 && (int)c < 127));
	}
	
	private void contentChanged()
	{
		for (int i = contentListeners.size() - 1; i >= 0; i--)
		{
			ContentEvent event = new ContentEvent();
			
			event.setSource(this);
			
			contentListeners.get(i).contentChanged(event);
		}
		
		highlightSyntax();
	}
	
	public void addError(int startIndex, int endIndex)
	{
//		errorLocations.add(new ErrorLocation(startIndex, endIndex));
		
//		int length = endIndex - startIndex;
//		
//		ArrayList<StyleRange> ranges = new ArrayList<StyleRange>();
//		
//		for (int i = 0; i < styles.length; i++)
//		{
//			StyleRange style = styles[i];
//			
//			int sStart = style.start;
//			int sEnd   = style.start + style.length;
//			
//			if (!(style.length == getText().length()) && (sEnd >= startIndex && endIndex >= sStart))
//			{
//				style.underline = true;
//				style.underlineStyle = SWT.UNDERLINE_SQUIGGLE;
//				style.underlineColor = new Color(Display.getDefault(), 240, 0, 0);
//			}
//			
//			ranges.add(style);
//		}
//		
//		setStyles(ranges.toArray(new StyleRange[0]));
//		
//		redraw();
	}
	
	public void clearErrors()
	{
		errorLocations.clear();
	}
	
	public void setText(String text)
	{
		setText(text, false);
	}
	
	public void setText(String text, boolean loaded)
	{
		setText(text, loaded, true);
	}
	
	public void setText(String text, boolean loaded, boolean parse)
	{
		super.setText(text);
		
		redraw();
	}
	
	public String getRawText()
	{
		return getText().replace(new Character((char)13), ' ');
	}
	
	public String getWritableText()
	{
		return getText();//.replace("\n", "\r\n");
	}
	
	public int getX()
	{
		return getBounds().x;
	}
	
	public int getY()
	{
		return getBounds().y;
	}
	
	public int getWidth()
	{
		int width = getBounds().width;
		
		return width;
	}
	
	public int getHeight()
	{
		return getBounds().height;
	}
	
	public Rectangle getBounds()
	{
		Rectangle bounds = super.getBounds();
		
		if (lineNumberPanel != null)
		{
			bounds.width += lineNumberPanel.getSize().x;
			bounds.x     -= lineNumberPanel.getSize().x;
		}
		
		return bounds;
	}
	
	public void setBounds(int x, int y, int width, int height)
	{
		if (lineNumberText != null)
		{
			lineNumberText.setLocation(x, y);
		}
		
		if (lineNumberPanel != null)
		{
			lineNumberPanel.setLocation(x, y);
			
			super.setLocation(x + lineNumberPanel.getSize().x, y);
			super.setSize(width - lineNumberPanel.getSize().x, height);
		}
		else
		{
			super.setLocation(x, y);
			super.setSize(width, height);
		}
		
		updatePercent();
	}
	
	public Point getSize()
	{
		Point size = super.getSize();
		
		if (lineNumberPanel != null)
		{
			size.x += lineNumberPanel.getSize().x;
		}
		
		return size;
	}
	
	private Point getSuperSize()
	{
		return super.getSize();
	}
	
	/**
	 * Overridden method that set the size of the CodeField.
	 * 
	 * @param width The new width of the CodeField.
	 * @param height The new height of the CodeField.
	 */
	public void setSize(int width, int height)
	{
		setBounds(getX(), getY(), width, height);
	}
	
	private void setSuperSize(int width, int height)
	{
		super.setSize(width, height);
	}
	
	public Point getLocation()
	{
		Point location = super.getLocation();
		
		if (lineNumberPanel != null)
		{
			location.x = lineNumberPanel.getLocation().x;
		}
		
		return location;
	}
	
	private Point getSuperLocation()
	{
		return super.getLocation();
	}
	
	public void setLocation(int x, int y)
	{
		setBounds(x, y, getWidth(), getHeight());
	}
	
	private void setSuperLocation(int x, int y)
	{
		super.setLocation(x, y);
	}
	
	public CommentProperties getCommentProperties()
	{
		return commentProperties;
	}
	
	public MethodProperties getMethodProperties()
	{
		return methodProperties;
	}
	
	public IdentifierProperties getIdentifierProperties()
	{
		return identifierProperties;
	}
	
	public int getLanguage()
	{
		return language;
	}
	
	public void setLanguage(int language)
	{
		this.language        = language;
		
		commentProperties    = Language.getCommentProperties(language);
		methodProperties     = Language.getMethodProperties(language);
		identifierProperties = Language.getIdentifierProperties(language);
			
		identifierLists.clear();
		methodLists.clear();
		identifierWords.clear();
		methodWords.clear();
		
//		if (language > 0)
//		{
		highlightSyntax();
//		}
	}
	
	public void addContentListener(ContentListener listener)
	{
		contentListeners.add(listener);
	}
	
	public void addCodeFieldListener(CodeFieldListener listener)
	{
		codeFieldListeners.add(listener);
	}
	
//	public void setSize(int width, int height)
//	{
//		if (lineNumberText != null)
//		{
//			lineNumberText.setSize((new String(getLineCount() + ".").length()) * charWidth, height - getHorizontalBar().getSize().y + 1);
//		}
//		
//		super.setSize(width, height);
//	}
	
	public void paste()
	{
		String before = getText();
		
		super.paste();
		
		if (!getText().equals(before))
		{
			contentChanged();
		}
	}
	
	public StyleRange[] getStyles()
	{
		return styles;
	}
	
	private void setStyles(StyleRange styles[])
	{
		this.styles = styles;
	}
	
	public void setShowLineNumbers(boolean show)
	{
		if (show)
		{
			lineNumberPanel = new LineNumberPanel(getParent(), SWT.NONE, this);
			lineNumberPanel.setMargin(1, 5);
			
			lineNumberPanel.addControlListener(new ControlListener()
			{
				public void controlResized(ControlEvent e)
				{
					int offset = (lineNumberPanel.getLocation().x + lineNumberPanel.getSize().x) - thisField.getSuperLocation().x;
					
					setSuperLocation(getSuperLocation().x + offset, getSuperLocation().y);
					setSuperSize(getSuperSize().x - offset, getSuperSize().y);
				}
				
				public void controlMoved(ControlEvent e)
				{
					
				}
			});
		}
		else
		{
			lineNumberPanel = null;
			
			if (lineNumberText != null)
			{
				lineNumberText.removeLineStyleListener(lineNumbers);
				removeLineStyleListener(lineSpaces);
				lineNumberText.dispose();
				lineNumberText = null;
			}
		}
	}
	
	/**
	 * Set whether this CodeField should auto update its size whenever
	 * its parent Composite is resized.
	 * 
	 * @param autoUpdate Whether this CodeField should auto update its
	 * 		size whenever its parent Composite is resized.
	 */
	public void setAutoUpdate(boolean autoUpdate)
	{
		this.autoUpdate = autoUpdate;
	}
	
	/**
	 * Update the percent values for this CodeField.
	 */
	private void updatePercent()
	{
		float width  = getWidth();
		float height = getHeight();
		
		widthPercent  = width  / composite.getSize().x;
		heightPercent = height / composite.getSize().y;
	}
	
	/**
	 * Update the size of this CodeField according to the percent values.
	 */
	private void updateSize()
	{
		int width  = Math.round(widthPercent * composite.getSize().x);
		int height = Math.round(heightPercent * composite.getSize().y);
		
		setSize(width, height);
	}
}