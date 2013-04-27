import info.gridworld.actor.Critter;

/**
 * 
 * 
 * Description:		A BlusterCritter looks at all of the neighbors within
 * 	two steps of its current location. (For a BlusterCritter not near an
 * 	edge, this includes 24 locations). It counts the number of critters
 * 	in those locations. If there are fewer than c critters, the
 * 	BlusterCritter's color gets brighter (color values increase). If
 * 	there are c or more critters, the BlusterCritter's color darkens
 * 	(color values decrease). Here, c is a value that indicates the courage
 * 	of the critter. It should be set in the constructor.
 */
public class BlusterCritter extends Critter
{
	
}