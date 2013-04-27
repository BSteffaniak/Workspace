import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.actor.Rock;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

/**
 * This class runs a world that contains a bug and a rock, added at random
 * locations. Click on empty locations to add additional actors. Click on
 * populated locations to invoke methods on their occupants.
 * To build your own worlds, define your own actors and a runner class. See the
 * BoxBugRunner (in the boxBug folder) for an example.
 * This class is not tested on the AP CS A and AB exams.
 */
public class BugRunner
{
    public static void main(String[] args)
    {
        ActorWorld world = new ActorWorld();
        
        world.add(new Location(2, 2), new QuickCrab());
        world.add(new Rock());
        world.show();
        
        Grid<Actor> grid = world.getGrid();
        
        for (int i = 0; i < grid.getNumRows(); i ++)
        {
        	for (int j = 0; j < grid.getNumCols(); j ++)
            {
            	Location loc = new Location(i, j);
            	if (grid.getValidAdjacentLocations(loc).size() < 8)
            	{
            		Rock r = new Rock();
            		world.add(loc, r);
            	}
            }
        }
    }
}
