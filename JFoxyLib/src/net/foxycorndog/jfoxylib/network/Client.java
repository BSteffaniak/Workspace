package net.foxycorndog.jfoxylib.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

/**
 * 
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
	 * 
	 */
	public void connect()
	{
		try
		{
			System.out.println("Attempting connection on " + ip + ":" + port);
			
			socket = new Socket(ip, port);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream(socket.getInputStream()));
			
			setConnected(socket.isConnected());
			
			if (socket.isConnected())
			{
				System.out.println("Connected!!!");
			}
			else
			{
				System.out.println("Connection error!");
			}
			
			setConnection(socket);
		}
		catch (ConnectException ex)
		{
			System.out.println("Connection Refused!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		startInputLoop();
	}
}