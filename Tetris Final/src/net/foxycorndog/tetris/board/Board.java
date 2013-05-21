package net.foxycorndog.tetris.board;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.Frame;
import net.foxycorndog.jfoxylib.components.Button;
import net.foxycorndog.jfoxylib.events.ButtonEvent;
import net.foxycorndog.jfoxylib.events.ButtonListener;
import net.foxycorndog.jfoxylib.events.KeyEvent;
import net.foxycorndog.jfoxylib.events.KeyListener;
import net.foxycorndog.jfoxylib.input.Keyboard;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

/**
 * Class that holds the information for the Pieces in the Tetris game, as well
 * as demonstrating the interactions of the Pieces.
 * 
 * @author Jeremiah Blackburn
 * @author Braden Steffaniak
 * @since May 6, 2013 at 3:31:08 PM
 * @since v0.1
 * @version May 6, 2013 at 3:31:08 PM
 * @version v0.1
 */
public class Board extends AbstractBoard
{
	private boolean						lost;
	
	private	int							ticks;
	private	int							lastSpeedTick;
	
	private	float						speedChangeAmount;
	private	float						speedChangeFactor;
	
	private long						pressStartTime;
	
	private	Button						backButton;

	private Piece						currentPiece;

	private	KeyListener					keyListener;
	
	private Tetris						tetris;

	private ArrayList<BoardListener>	events;

	/**
	 * Instantiate the image for the Board as well as other instantiations.
	 * 
	 * @param width
	 *            The number of horizontal grid spaces the Board will contain.
	 * @param height
	 *            The number of vertical grid spaces the Board will contain.
	 * @param gridSpaceSize
	 *            The size (in pixels) that each space on the Board will take
	 *            up. eg: passing 10 would create 10x10 grid spaces across the
	 *            board.
	 */
	public Board(int width, int height, int gridSpaceSize, final Tetris tetris)
	{
		super(width, height, gridSpaceSize);

		this.tetris = tetris;

		events = new ArrayList<BoardListener>();
		
		pressStartTime = Long.MAX_VALUE;

		keyListener = new KeyListener()
		{
			public void keyPressed(KeyEvent event)
			{
				if (event.getKeyCode() == Keyboard.KEY_LEFT)
				{
					movePiece(currentPiece, -1, 0);
					
					pressStartTime = System.currentTimeMillis();
				}
				if (event.getKeyCode() == Keyboard.KEY_RIGHT)
				{
					movePiece(currentPiece, 1, 0);
					
					pressStartTime = System.currentTimeMillis();
				}
				if (event.getKeyCode() == Keyboard.KEY_UP)
				{
					currentPiece.rotateClockwise();
				}
				if (event.getKeyCode() == Keyboard.KEY_DOWN)
				{
					setTicksPerSecond(getTicksPerSecond() * 4);
				}
			}

			public void keyReleased(KeyEvent event)
			{
				if (event.getKeyCode() == Keyboard.KEY_DOWN)
				{
					setTicksPerSecond(getTicksPerSecond() / 4);
				}
				if (event.getKeyCode() == Keyboard.KEY_LEFT)
				{
					if (!Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
					{
						pressStartTime = Long.MAX_VALUE;
					}
				}
				if (event.getKeyCode() == Keyboard.KEY_RIGHT)
				{
					if (!Keyboard.isKeyDown(Keyboard.KEY_LEFT))
					{
						pressStartTime = Long.MAX_VALUE;
					}
				}
			}

			public void keyTyped(KeyEvent event)
			{

			}

			public void keyDown(KeyEvent event)
			{

			}
		};

		Keyboard.addKeyListener(keyListener);

		// setTicksPerSecond(8f);
		
		backButton = new Button(null);
		backButton.setAlignment(Button.RIGHT, Button.BOTTOM);
		backButton.setLocation(-30, 80);
		
		try
		{
			backButton.setImage("res/images/back.png");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		backButton.addButtonListener(new ButtonListener()
		{
			public void buttonUnHovered(ButtonEvent event)
			{
				
			}
			
			public void buttonReleased(ButtonEvent event)
			{
				Button source = event.getSource();
				
				if (source == backButton)
				{
					quitGame();
					tetris.openMainMenu();
				}
			}
			
			public void buttonPressed(ButtonEvent event)
			{
				
			}
			
			public void buttonHovered(ButtonEvent event)
			{
				
			}
		});
		
		speedChangeFactor = 1.8f;
		speedChangeAmount = 0.5f;
		
		lastSpeedTick     = 10;
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#tick()
	 * 
	 * Moves a piece down
	 * one space after half a second until the piece hits the bottom of the
	 * board or another piece on the board.
	 */
	public void tick()
	{
		if (!lost)
		{
			if (ticks >= lastSpeedTick * speedChangeFactor)
			{
				lastSpeedTick = ticks;
				
				setTicksPerSecond(getTicksPerSecond() + speedChangeAmount);
			}
			
			if (System.currentTimeMillis() - pressStartTime >= 200)
			{
				if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				{
					movePiece(currentPiece, -1, 0);
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				{
					movePiece(currentPiece, 1, 0);
				}
			}
			
			boolean moved = movePiece(currentPiece, 0, -1);

			if (!moved)
			{
				currentPiece.kill();

				clearRows();

				currentPiece = tetris.getSidebar().getNextPiece().getNextPiece();
				tetris.getSidebar().getNextPiece().generateNextPiece();
				
				addPiece(currentPiece, getWidth() / 2 - currentPiece.getWidth() / 2, getHeight() - currentPiece.getHeight());

				if (currentPiece.yallHitTheBottomBaby())
				{
					Keyboard.removeKeyListener(keyListener);
					
					lost = true;

					for (BoardListener listener : events)
					{
						listener.onGameLost(null);
					}
					
					quitGame();
					Tetris.SOUND_LIBRARY.playSound("lose.wav");
				}
				else
				{
					Tetris.SOUND_LIBRARY.playSound("pop.wav");
				}
			}
			
			ticks++;
		}
	}
	
	/**
	 * Quit the game and stop the music.
	 */
	private void quitGame()
	{
		Tetris.SOUND_LIBRARY.stopSound("music.wav");
	}
	
	/**
	 * Move the specified Piece the specified amount.
	 * 
	 * @param piece The Piece to move.
	 * @param dx The amount of squares to move it horizontally.
	 * @param dy The amount of squares to move it vertically.
	 * @return Whether it moved successfully or not.
	 */
	private boolean movePiece(Piece piece, int dx, int dy)
	{
		boolean moved = piece.move(dx, dy);
		
		if (moved)
		{
			BoardEvent event = new BoardEvent(piece.getX(), piece.getY(), piece, 0);
			
			for (BoardListener listener : events)
			{
				listener.onPieceMove(event);
			}
		}
		
		return moved;
	}

	/**
	 * Sets lost to l.
	 */
	public void setLost(boolean l)
	{
		lost = l;
	}

	/**
	 * @return lost. Lost is either true or false. If lost is true, the piece is
	 *         still able to move.
	 */
	public boolean hasLost()
	{
		return lost;
	}

	/**
	 * Coordinates use the Cartesian system.
	 * 
	 * @see net.foxycorndog.tetris.board.AbstractBoard#isValid(int, int)
	 * 
	 * checks to see it the coordinate (x,y) is valid for the piece to
	 * move to.
	 */
	public boolean isValid(int x, int y)
	{
		return (x >= 0 && x < getWidth()) && (y >= 0 && y < getHeight());
	}

	/**
	 * Coordinates use the Cartesian system.
	 * 
	 * @see net.foxycorndog.tetris.board.AbstractBoard#isValid(int, int) checks
	 *      to see it the coordinate (x,y) is valid for the piece to move to.
	 */
	public boolean isValid(Location loc)
	{
		return isValid(loc.getX(), loc.getY());
	}

	/**
	 * Looks at one row of the board at a time and checks each location in that
	 * row to see if it has a square. If all of the locations in the row have a
	 * square the squares are deleted.
	 */
	public void clearRows()
	{
		// if (true)return;
		int counter = 0;
		
		int r       = 0;
		
		int lines   = 0;

		while (r < getHeight())
		{
			counter = 0;

			for (int c = 0; c < getWidth(); c++)
			{
				if (getPieces(new Location(c, r)).length > 0)
				{
					counter++;
				}

				if (counter == getWidth())
				{
					for (int dRow = 0; dRow < getWidth(); dRow++)
					{
						Location l = new Location(dRow, r);
						
						Piece pieces[] = getPieces(l);
						
						pieces[0].deleteSquare(l);
					}
					
					for (int y = r; y < getHeight(); y++)
					{
						for (int x = 0; x < getWidth(); x++)
						{
							Location l = new Location(x, y);
							
							Piece pieces[] = getPieces(l);
							
							if (pieces.length > 0)
							{
								pieces[0].moveSquare(l, new Location(0, -1));
							}
						}
					}
					
					lines++;
				}
			}
			
			if (counter < getWidth())
			{
				r++;
			}
		}
		
		if (lines > 0)
		{
			BoardEvent event = new BoardEvent(0, r, null, lines);
	
			for (BoardListener listener : events)
			{
				listener.onLineDeleted(event);
			}
			
			Tetris.SOUND_LIBRARY.playSound("lineremoved.wav");
		}
	}

	/**
	 * Adds a BoardListener to the ArrayList events.
	 * 
	 * @param b
	 */
	public void addListener(BoardListener b)
	{
		events.add(b);
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#newGame()
	 */
	public void newGame()
	{
		currentPiece = Piece.getRandomPiece();

		addPiece(currentPiece, 4, 18);
		
		Tetris.SOUND_LIBRARY.playSound("pop.wav");
		Tetris.SOUND_LIBRARY.loopSound("music.wav");
	}

	/**
	 * @see net.foxycorndog.tetris.board.AbstractBoard#addPiece(net.foxycorndog.tetris.board.Piece,
	 *      int, int)
	 */
	public void addPiece(Piece piece, int x, int y)
	{
		piece.setBoard(this);
		piece.setLocation(x, y);

		getPieces().add(piece);
	}
	
	/**
	 * Render the back Button.
	 * 
	 * @see net.foxycorndog.tetris.board.AbstractBoard#render()
	 */
	public void render()
	{
		super.render();
		
		GL.pushMatrix();
		{
			GL.scale(0.75f, 0.75f, 1);
			
			backButton.render();
		}
		GL.popMatrix();
	}
	
	/**
	 * Dispose of the Components used by the Board.
	 */
	public void dispose()
	{
		backButton.dispose();
	}
}