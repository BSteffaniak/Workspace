import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class ZBug extends Bug
{
	private int sideLength;
	private int stepsTaken;
	
	public ZBug(int sideLength)
	{
		this.sideLength = sideLength;
		this.stepsTaken = 0;
	}
	
	public void move()
	{
		
        
		
		if (stepsTaken / sideLength == 0)
		{
			moveDirection(Location.EAST);
		}
		else if (stepsTaken / sideLength == 1)
		{
			moveDirection(Location.SOUTHWEST);
		}
		else if (stepsTaken / sideLength == 2)
		{
			moveDirection(Location.EAST);
		}
		else
		{
			super.move();
		}
		
		stepsTaken ++;
	}
	
	private void moveDirection(int direction)
	{
		Grid<Actor> gr = getGrid();
	    if (gr == null)
	        return;
    
        Location loc = getLocation();
		this.setDirection(direction);
        Location next = loc.getAdjacentLocation(getDirection());
        if (gr.isValid(next))
            moveTo(next);
        else
            removeSelfFromGrid();
        Flower flower = new Flower(getColor());
        flower.putSelfInGrid(gr, loc);
	}
}