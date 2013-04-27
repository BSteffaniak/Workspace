import java.util.ArrayList;

import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/**
 * File:          QuickCritter.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Jan2013
 * Description:   A quick critter class that extends crabcritter
 * and moves two spaces instead of one, when available.
 */
public class QuickCritter extends CrabCritter
{
	/**
	 * Overridden method from CrabCritter to move it two spaces instead
	 * of one.
	 * Finds the valid adjacent locations of this critter in different
	 * directions.
	 * 
	 * @param directions - an array of directions (which are relative
	 * 		to the current direction).
	 * @return a set of valid locations that are neighbors of neighbors
	 * 		of the current location in the given directions.
	 */
	public ArrayList<Location> getLocationsInDirections(int[] directions)
	{
		ArrayList<Location> locs = new ArrayList<Location>();
		Grid gr = getGrid();
		Location loc = getLocation();

		for (int d : directions)
		{
			Location neighborLoc = loc.getAdjacentLocation(getDirection()
					+ d).getAdjacentLocation(getDirection() + d);
			
			if (gr.isValid(neighborLoc) && gr.get(neighborLoc) == null)
			{
				locs.add(neighborLoc);
			}
			else
			{
				neighborLoc = loc.getAdjacentLocation(getDirection() + d);
				
				if (gr.isValid(neighborLoc) && gr.get(neighborLoc)
						== null)
				{
					locs.add(neighborLoc);
				}
				else
				{
					super.getLocationsInDirections(directions);
				}
			}
		}
		return locs;
	}
}