import info.gridworld.actor.Actor;
import info.gridworld.actor.Bug;
import info.gridworld.actor.Flower;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;

public class Butterfly extends Bug
{
	public Butterfly()
	{
		
	}
	
	public void turn()
	{
		setDirection(getDirection() + 15);
		
		new Location(3, 3).getAdjacentLocation(0);
	}
}