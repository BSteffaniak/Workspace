package net.foxycorndog.tetris.sidebar;

import java.io.IOException;
import java.util.ArrayList;

import net.foxycorndog.jfoxylib.components.Image;
import net.foxycorndog.jfoxylib.opengl.GL;
import net.foxycorndog.jfoxylib.opengl.texture.Texture;
import net.foxycorndog.tetris.Tetris;
import net.foxycorndog.tetris.board.AbstractPiece;
import net.foxycorndog.tetris.board.Piece;

/**
 * Class that displays and keeps track of the next piece.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:55:17 PM
 * @since	v0.1
 * @version	Apr 23, 2013 at 4:55:17 PM
 * @version	v1.0
 */
public class NextPiece
{
	private	int		x;
	private	int		y;
	
	private	Piece	nextPiece;
	
	private	Image	bgImage;
	
	/**
	 * creates the next piece to enter the game and displays it in the
	 * sideBar
	 */
	public NextPiece(int x, int y)
	{
		this.x  = x;
		this.y  = y;
		
		bgImage = new Image(null);
		
		generateNextPiece();
		
		try
		{
			bgImage.setImage(new Texture("res/images/nextpiece.png"));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Get the Piece that will be used next in the Tetris game.
	 * 
	 * @return The Piece that will be used next in the Tetris game.
	 */
	public Piece getNextPiece()
	{
		return nextPiece;
	}
	
	/**
	 * Tells this instance to generate the next random Piece.
	 */
	public void generateNextPiece()
	{
		nextPiece = Piece.getRandomPiece();
	}
	
	/**
	 * renders the image of the next piece
	 */
	public void render()
	{
		int   xC    = 30;
		int   yC    = 280;
		
		float scale = 2;
		
		GL.pushMatrix();
		{
			GL.translate(x, y + yC, 0);
			GL.scale(scale, scale, 1);
			
			bgImage.render();
			
			GL.translate(xC / scale, 20 / scale, 0);
			nextPiece.render();

			GL.translate(-xC / scale + 20 / scale, 60 / scale, 0);
			Tetris.getFont().render("Next:", 0, 0, 0, 0.25f, null);
		}
		GL.popMatrix();
	}
}