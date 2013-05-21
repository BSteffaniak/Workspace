package net.foxycorndog.tetris.sidebar;

import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.event.BoardEvent;
import net.foxycorndog.tetris.event.BoardListener;

/**
 * Class that keeps track of and displays the score of the
 * Tetris game.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:54:12 PM
 * @since	v0.1
 * @version	Apr 27, 2013 at 12:43:29 PM
 * @version	v1.0
 */
public class Scoreboard implements BoardListener
{
	private int score;
	private int x, y;
	
	/**
	 * creates the scoreBoard and displays it in the sideBar
	 */
	public Scoreboard(int x, int y)
	{
		this.x = x;
		this.y = y;
		score = 0;
	}
	
	/**
	 * renders the image of the score
	 */
	public void render()
	{
		int xC = 0;
		int yC = 220;
		float scale = 0.5f;
		Tetris.getFont().render("score " + score, x + xC, y + yC, 0, scale, null);
		
//		GL.translate(100, 100, 0);
//		GL.scale(5, 5, 1);
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onPieceMove(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onPieceMove(BoardEvent event)
	{
		
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onLineDeleted(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onLineDeleted(BoardEvent event)
	{
		int lines = event.getLines();
		
		if (lines == 1)
		{
			score += 100;
		}
		else if (lines == 2)
		{
			score += 300;
		}
		else if (lines == 3)
		{
			score += 500;
		}
		else if (lines == 4)
		{
			score += 1200;
		}
	}

	/**
	 * @see net.foxycorndog.tetris.event.BoardListener#onGameLost(net.foxycorndog.tetris.event.BoardEvent)
	 */
	public void onGameLost(BoardEvent event)
	{
		
	}
}