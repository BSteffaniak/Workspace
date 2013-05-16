package net.foxycorndog.jfoxylib.network;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
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
	private int          port;
	
	private Socket       socket;
	
	private ServerSocket server;
	
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
				System.out.println("Server started on " + InetAddress.getLocalHost().getHostAddress() + ":" + port);
			}
			catch (UnknownHostException e)
			{
				e.printStackTrace();
			}
			
			socket = server.accept();
			
			setConnection(socket);
			
			setOut(new ObjectOutputStream(socket.getOutputStream()));
			setIn(new ObjectInputStream(socket.getInputStream()));
			
			setConnected(socket.isConnected());
			
			System.out.println("Connected!");
			
			sendPacket(new Packet(new Point(3, 1), 2));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		startInputLoop();
	}
}
