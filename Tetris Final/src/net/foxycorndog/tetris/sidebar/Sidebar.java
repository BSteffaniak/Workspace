package net.foxycorndog.tetris.sidebar;

/**
 * Class that holds everything in the sidebar and renders it
 * too.
 * 
 * @author	Henry Rybolt
 * @author	Braden Steffaniak
 * @since	Apr 23, 2013 at 4:43:29 PM
 * @since	v0.1
 * @version	Apr 27, 2013 at 12:43:29 PM
 * @version	v1.0
 */
public class Sidebar
{
	NextPiece		nP;
	Scoreboard		sB;
	LinesCompleted	lC;

	/**
	 * creates a sidebar
	 */
	public Sidebar()
	{
		int x = 400;
		int y = 200;
		nP = new NextPiece(x, y);
		sB = new Scoreboard(x, y);
		lC = new LinesCompleted(x, y);
	}

	/**
	 * Get the NextPiece part of the Sidebar.
	 * 
	 * @return The NextPiece part of the Sidebar.
	 */
	public NextPiece getNextPiece()
	{
		return nP;
	}

	/**
	 * Get the Scoreboard part of the Sidebar.
	 * 
	 * @return The Scoreboard part of the Sidebar.
	 */
	public Scoreboard getScoreboard()
	{
		return sB;
	}

	/**
	 * Get the LinesCompleted part of the Sidebar.
	 * 
	 * @return The LinesCompleted part of the Sidebar.
	 */
	public LinesCompleted getLinesCompleted()
	{
		return lC;
	}
	
	/**
	 * renders the image of the Sidebar(displays lines completed, score, and the
	 * next piece to enter gameplay)
	 */
	public void render()
	{
		sB.render();
		nP.render();
		lC.render();
	}
}