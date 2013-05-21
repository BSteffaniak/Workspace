package net.foxycorndog.jfoxylib.network;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * Class that is used to represent a Network connection.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:04:11 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:04:11 PM
 * @version	v0.2
 */
public abstract class Network
{
	private boolean            connected;
	
	private ObjectOutputStream out;
	private ObjectInputStream  in;
	
	private Socket             connection;
	
	private InputLoop          inputLoop;
	
	private Network            thisNetwork;
	
	/**
	 * Construct a Network instance.
	 */
	public Network()
	{
		thisNetwork = this;
	}
	
	/**
	 * Start the loop that waits for input from any clients or servers.
	 */
	public void startInputLoop()
	{
		inputLoop = new InputLoop()
		{
			public void onReceivedPacket(Packet packet)
			{
				thisNetwork.onReceivedPacket(packet);
			}
		};
		
		inputLoop.start();
	}
	
	/**
	 * Abstract method that is called whenever a packet is received.
	 * 
	 * @param packet The Packet instance that was received.
	 */
	public abstract void onReceivedPacket(Packet packet);
	
	/**
	 * Method used to send a packet to the other Network.
	 * 
	 * @param object The Packet to send to the other Network.
	 */
	public void sendPacket(Packet object)
	{
		if (!isConnected())
		{
			throw new NetworkException("The Network must be connected to another Network in order to send a Packet.");
		}
		
		try
		{
			out.writeObject(object);
			
			out.writeObject(new Ping());
		}
		catch (SocketException e)
		{
			throw new NetworkException("The Network connection had a io error.");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to send an already created Ping object to the other
	 * Network.
	 * 
	 * @param ping The Ping that has been received from the other
	 * 		Network.
	 */
	public void ping(Ping ping)
	{
		try
		{
			out.writeObject(ping);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Close the current Network connection.
	 */
	public void close()
	{
		try
		{
			if (isConnected())
			{
				out.close();
				in.close();
			}
		
			if (connection != null)
			{
				connection.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Method that gets whether the Network is connected to another
	 * Network or not.
	 * 
	 * @return Whether the Network is connected to another Network or not.
	 */
	public boolean isConnected()
	{
		return connected;
	}
	
	/**
	 * Method that sets whether the Network is connected to another
	 * Network or not.
	 * 
	 * @param connected Whether the Network is connected to another
	 * 		Network or not.
	 */
	public void setConnected(boolean connected)
	{
		this.connected = connected;
	}
	
	/**
	 * Method to get the ObjectOutputStream of the Network.
	 * 
	 * @return The ObjectOutputStream of the Network.
	 */
	public ObjectOutputStream getOut()
	{
		return out;
	}
	
	/**
	 * Method to set the ObjectOutputStream for the Network.
	 * 
	 * @param out The ObjectOutputStream for the Network to send objects
	 * 		to the other Network.
	 */
	public void setOut(ObjectOutputStream out)
	{
		this.out = out;
	}
	
	/**
	 * Method to get the ObjectInputStream of the Network.
	 * 
	 * @return The ObjectInputStream of the Network.
	 */
	public ObjectInputStream getIn()
	{
		return in;
	}
	
	/**
	 * Method to set the ObjectInputStream for the Network.
	 * 
	 * @param in The ObjectInputStream for the Network to read objects
	 * 		from the other Network.
	 */
	public void setIn(ObjectInputStream in)
	{
		this.in = in;
	}
	
	/**
	 * Method to set the connection to a Socket.
	 * 
	 * @param connection The Socket to set as the connection.
	 */
	public void setConnection(Socket connection)
	{
		this.connection = connection;
	}
	
	/**
	 * Class that is used for the loop that loops through all
	 * of the input received from the other Network.
	 * 
	 * @author	Braden Steffaniak
	 * @since	Apr 26, 2013 at 11:05:14 PM
	 * @since	v0.1
	 * @version	Apr 26, 2013 at 11:05:14 PM
	 * @version	v0.2
	 */
	private abstract class InputLoop
	{
		/**
		 * Default Constructor for the InputLoop class.
		 */
		public InputLoop()
		{
			
		}

		/**
		 * Method that is called when a Ping is received from the
		 * other Network.
		 * 
		 * @param player The Ping that was received from the other
		 * 		Network.
		 */
		public void onReceivedPing(Ping ping)
		{
			if (ping.wasReceived())
			{
				ping.calculatePing();
				
				System.out.println("Ping: " + ping.getPing());
			}
			else
			{
				ping.receive();
				
				ping(ping);
			}
		}
		
		public abstract void onReceivedPacket(Packet packet);
		
		/**
		 * The loop method that runs forever while the Socket is
		 * connected.
		 */
		public final void start()
		{
			new Thread()
			{
				public void run()
				{
					while (connection.isConnected())
					{
						Packet packet = null;
						
						try
						{
							packet = (Packet)in.readObject();
						}
						catch (SocketException e)
						{
//							e.printStackTrace();
							
							break;
						}
						catch (EOFException e)
						{
//							e.printStackTrace();
							
							break;
						}
						catch (ClassNotFoundException e)
						{
							e.printStackTrace();
							
							break;
						}
						catch (IOException e)
						{
							e.printStackTrace();
							
							break;
						}
						
						if (packet instanceof Ping)
						{
							onReceivedPing((Ping)packet);
						}
						else
						{
							onReceivedPacket(packet);
						}
					}
				}
			}.start();
		}
	}
}