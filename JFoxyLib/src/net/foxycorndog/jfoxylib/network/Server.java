package net.foxycorndog.jfoxylib.network;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Class used to create a Server instance that can be used
 * to recieve and transfer data along a Network.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:11:21 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:11:21 PM
 * @version	v0.2
 */
public abstract class Server extends Network
{
	private int				port;
	
	private	String			ip;
	
	private Socket			socket;
	
	private ServerSocket	server;
	
	/**
	* Creates a Server that extends the Network class. This constructor
	* method is used for creating a new ServerSocket.
	* 
	* @param port The port of the created Server.
	*/
	public Server(int port)
	{
		this.port = port;
	}
	
	/**
	 * Create the Server's connection.
	 */
	public void create()
	{
		try
		{
			server = new ServerSocket(port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			try
			{
				ip = InetAddress.getLocalHost().getHostAddress();
				
				System.out.println("Server started on " + ip + ":" + port);
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			
			try
			{
				socket = server.accept();
			}
			catch (SocketException e)
			{
				if (e.getMessage().equals("socket closed"))
				{
					// Was probably purposely closed.
				}
				else
				{
					e.printStackTrace();
				}
				
				return;
			}
			
			setConnection(socket);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream(socket.getInputStream()));
			
			setConnected(socket.isConnected());
			
//			System.out.println("Connected!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		startInputLoop();
	}
	
	/**
	 * Get the IP address that the Server was started on.
	 * 
	 * @return The IP address that the Server was started on.
	 */
	public String getIP()
	{
		return ip;
	}

	/**
	 * Close the current Servers Network connection.
	 */
	public void close()
	{
		super.close();
		
		try
		{
			server.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
