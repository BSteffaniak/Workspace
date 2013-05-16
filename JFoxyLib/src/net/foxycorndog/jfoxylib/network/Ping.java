package net.foxycorndog.jfoxylib.network;

/**
 * Class that is used to ping back and forth between two connections
 * to find how fast the connection speed is.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:08:55 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:08:55 PM
 * @version	v0.2
 */
public class Ping extends Packet
{
	private boolean	received;
	
	private long	timeSent;
	private long	timeReceived;
	
	private int		ping;
	
	/**
	 * Creates a Ping object used for receiving the ping count of
	 * the network.
	 */
	public Ping()
	{
		super(null, 0);
		
		timeSent = System.currentTimeMillis();
	}
	
	/**
	 * Tell the network that the ping has been received.
	 */
	public void receive()
	{
		received = true;
	}
	
	/**
	 * A check that the network checks to see if it has already
	 * been sent and just now been received back.
	 * 
	 * @return Whether it has been received by the other network.
	 */
	public boolean wasReceived()
	{
		return received;
	}
	
	/**
	 * Method used for calculating the ping of the network. Called
	 * when the ping has been sent and received to the same network.
	 */
	public void calculatePing()
	{
		timeReceived = System.currentTimeMillis();
		
		ping         = (int)(timeReceived - timeSent);
	}
	
	/**
	 * Method used for getting the integer value of the ping.
	 * 
	 * @return The integer value of the ping.
	 */
	public int getPing()
	{
		return ping;
	}
}
