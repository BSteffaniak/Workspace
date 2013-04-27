import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Description:		A ChameleonKid changes color to the color of one of the actors
 * 	immediately in front or behind. If there is no actor in either of
 * 	these locations, then the ChameleonKid darkens like the modified
 * 	ChameleonCritter.
 */
public class ChameleonKid extends ChameleonCritter
{
	public void makeMove(Location loc)
	{
		super.makeMove(loc);
		
		loc = getLocation();
		
		Grid<Actor> grid = getGrid();
		
		ArrayList<Location> occupied = grid.getOccupiedAdjacentLocations(loc);
		
		if (occupied.size() > 0)
		{
			
		}
		else
		{
			Color col = getColor();
			
			int r = col.getRed();
			int g = col.getGreen();
			int b = col.getBlue();
			
			int amount = 5;
			
			r -= amount;
			g -= amount;
			b -= amount;
			
			if (r < 0)
				r = 0;

			if (g < 0)
				g = 0;

			if (b < 0)
				b = 0;
			
			Color newCol = new Color(r, g, b);
			
			setColor(newCol);
		}
	}
}
