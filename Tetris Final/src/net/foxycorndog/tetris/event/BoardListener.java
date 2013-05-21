package net.foxycorndog.tetris.event;

/**
 * Interface used to describe what to do when events on a
 * Board occur.
 * 
 * @author	Braden Steffaniak
 * @since	May 13, 2013 at 4:48:58 PM
 * @since	v0.1
 * @version	May 13, 2013 at 4:48:58 PM
 * @version	v0.1
 */
public interface BoardListener
{
	/**
	 * Method called when a Piece is moved on the Board.
	 * 
	 * @param event The BoardEvent that describes what happened.
	 */
	public void onPieceMove(BoardEvent event);
	
	/**
	 * Method called when a line of Pieces is completed and then
	 * deleted.
	 * 
	 * @param event The BoardEvent that describes what happened.
	 */
	public void onLineDeleted(BoardEvent event);
	
	/**
	 * Method called when a Tetris game is lost.
	 * 
	 * @param event The BoardEvent that describes what happened.
	 */
	public void onGameLost(BoardEvent event);
}
