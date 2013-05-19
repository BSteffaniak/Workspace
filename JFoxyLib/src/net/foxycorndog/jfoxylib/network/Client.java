package net.foxycorndog.jfoxylib.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * Class used to connect to a Server across a Network connection.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:03:11 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:03:11 PM
 * @version	v0.2
 */
public abstract class Client extends Network
{
	private int		port;
	
	private String	ip;
	
	private Socket	socket;
	
	/**
	 * The Constructor method that attempts to connect to the specified
	 * Server.
	 * 
	 * @param ip The ip to the Server.
	 * @param port The port to the Server.
	 */
	public Client(String ip, int port)
	{
		this.ip   = ip;
		this.port = port;
	}
	
	/**
	 * Attempt a connection to the specified ip and port.
	 */
	public void connect()
	{
		try
		{
			socket = new Socket(ip, port);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream(socket.getInputStream()));
			
			setConnected(socket.isConnected());
			
			if (socket.isConnected())
			{
//				System.out.println("Connected!!!");
			}
			else
			{
				System.err.println("Connection error!");
			}
			
			setConnection(socket);
		}
		catch (ConnectException ex)
		{
			System.err.println("Connection Refused!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		startInputLoop();
	}
}
