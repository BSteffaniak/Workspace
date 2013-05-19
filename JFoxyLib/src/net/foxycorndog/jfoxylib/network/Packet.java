package net.foxycorndog.jfoxylib.network;

import java.io.Serializable;

/**
 * Class that is used to represent a Packet of information being sent
 * over a Network connection.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:07:52 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:07:52 PM
 * @version	v0.2
 */
public class Packet implements Serializable
{
	private	Object	data;
	
	private	int		id;
	
	/**
	 * Creates a new Packet. Packets are used for sending information
	 * across a network.
	 * 
	 * @param object The object that is being sent.
	 * @param id The id of the request that is being sent.
	 */
	public Packet(Object object, int id)
	{
		this.data = object;
		this.id   = id;
	}
	
	/**
	 * Method used for getting the id of the Packet.
	 * 
	 * @return The id of the Packet.
	 */
	public int getId()
	{
		return id;
	}
	
	/**
	 * Get the Object data that was sent with the Packet.
	 * 
	 * @return The Object data that was sent with the Packet.
	 */
	public Object getData()
	{
		return data;
	}
}