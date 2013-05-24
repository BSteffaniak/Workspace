package net.foxycorndog.jfoxylib.network;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Class used to create a Server instance that can be used
 * to receive and transfer data along a Network.
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
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			
			String fallback = null;
			
			/* Loop through all of the available network ips and check
			 * them to see if they fit the criteria
			 */
			while (interfaces.hasMoreElements())
			{
				NetworkInterface current = interfaces.nextElement();
				
				if (current.getDisplayName().toLowerCase().contains("virtual")) continue;

				if (!current.isUp() || current.isLoopback() || current.isVirtual()) continue;

				Enumeration<InetAddress> addresses = current.getInetAddresses();

				while (addresses.hasMoreElements())
				{
					InetAddress current_addr = addresses.nextElement();
					
					if (current_addr.isLoopbackAddress()) continue;

					if (current_addr instanceof Inet4Address)
					{
						fallback = current_addr.getHostAddress();
						
						if (current_addr.isSiteLocalAddress())
						{
							ip = current_addr.getHostAddress();
							
							break;
						}
					}
				}
				
				if (ip != null)
				{
					break;
				}
			}
			
			if (ip == null)
			{
				ip = fallback;
			}
			
//			try
//			{
//				ip = InetAddress.getByName("FoxyCorndog").getHostAddress();
//			}
//			catch (UnknownHostException e)
//			{
//				e.printStackTrace();
//			}
			
//			System.out.println("Server started on " + ip + ":" + port + ", " + server.getLocalPort());
			
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
