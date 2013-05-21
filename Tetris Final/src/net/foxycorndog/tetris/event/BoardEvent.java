package net.foxycorndog.tetris.event;

import net.foxycorndog.tetris.board.Piece;

/**
 * Class used to describe what happened in a Board Listener.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 6:38:19 AM
 * @since	v0.1
 * @version	Apr 26, 2013 at 6:38:19 AM
 * @version	v0.1
 */
public class BoardEvent extends Event
{
 	private int		col, row;
 	private	int		lines;
 	
 	private	Piece	piece;
 	
	/**
	 * Create a BoardEvent at the specified column and row on the
	 * Board.
	 * 
	 * @param col The column that the Even has taken place.
	 * @param row The row that the Even has taken place.
	 */
	public BoardEvent(int col, int row, Piece piece, int lines)
	{
		this.col   = col;
		this.row   = row;
		this.lines = lines;
		
		this.piece = piece;
	}
	
	/**
	 * Get the column that the Event has taken place.
	 */
	public int getCol()
	{
		return col;
	}

	/**
	 * Get the row that the Event has taken place.
	 */
	public int getRow()
	{
		return row;
	}

	/**
	 * Get how many lines were deleted on the Event.
	 */
	public int getLines()
	{
		return lines;
	}
	
	/**
	 * Get the Piece that the Event has taken place with.
	 * 
	 * @return
	 */
	public Piece getPiece()
	{
		return piece;
	}
}
