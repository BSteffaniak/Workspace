import java.util.ArrayList;

import info.gridworld.actor.Actor;
import info.gridworld.actor.Critter;

/**
 * File:          RockHound.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 11Jan2013
 * Description:   A rock hound class that extends Critter
 * 	and eats rocks in its way.
 */
public class RockHound extends Critter
{
	/**
     * Processes the elements of <code>actors</code>. New actors
     * may be added to empty locations. Implemented to "eat"
     * (i.e. remove) selected actors that are critters. Override
     * this method in subclasses to process actors in a different
     * way. <br /> Postcondition: (1) The state of all actors in
     * the grid other than this critter and the elements of
     * <code>actors</code> is unchanged. (2) The location of
     * this critter is unchanged.
     * 
     * @param actors the actors to be processed
     */
	public void processActors(ArrayList<Actor> actors)
    {
        for (Actor a : actors)
        {
            if (!(a instanceof Critter))
            {
                a.removeSelfFromGrid();
            }
        }
    }
}