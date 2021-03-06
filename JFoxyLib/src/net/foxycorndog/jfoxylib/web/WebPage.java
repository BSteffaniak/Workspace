package net.foxycorndog.jfoxylib.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Class used to get Output from web-pages on a Server.
 * 
 * @author	Braden Steffaniak
 * @since	Apr 26, 2013 at 11:26:27 PM
 * @since	v0.1
 * @version	Apr 26, 2013 at 11:26:27 PM
 * @version	v0.2
 */
public class WebPage
{
	/**
	 * Returns the first line of the output from the url webpage.
	 * 
	 * @param url The url to the webpage.
	 * @return The first line of the output.
	 */
	public static String getFirstOutput(String url)
	{
		try
		{
			URL myurl = new URL(url);
			URLConnection yc = myurl.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String answer = in.readLine();
			in.close();
			
			return answer;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (UnknownHostException e)
		{
			throw new ConnectionException("Can not connect to url: " + url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Get all of the lines of output from the web-page at the URL given.
	 * 
	 * @param url The URL of the web-page.
	 * @return A String array of each line of the output from the
	 * 		web-page at the specified URL.
	 */
	public static String[] getOutput(String url)
	{
		ArrayList<String> arr = new ArrayList<String>();
		
		try
		{
			URL            myurl = new URL(url);
			URLConnection  yc    = myurl.openConnection();
			BufferedReader in    = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			
			String         line  = null;
			
			while ((line = in.readLine()) != null)
			{
				arr.add(line);
			}
			
			in.close();
			
			String answer[] = arr.toArray(new String[0]);
			
			return answer;
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (UnknownHostException e)
		{
			throw new ConnectionException("Can not connect to url: " + url);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
}