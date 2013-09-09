import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;

public class Driver
{
	public static void main(String args[])
	{
		new Driver();
	}
	
	public Driver()
	{
		JFrame frame = new JFrame();
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("MariaDB Java Client");
		
		try
		{
			String url      = null;
			String username = null;
			String password = null;
			
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader("res/credentials.txt"));

				url      = reader.readLine();
				username = reader.readLine();
				password = reader.readLine();
				
				reader.close();
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
				System.exit(1);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			Connection  connection = DriverManager.getConnection(url, username, password);
			Statement stmt = connection.createStatement();
//			stmt.executeUpdate("CREATE DATABASE mydata");
			stmt.executeUpdate("USE mydata;");
//			stmt.executeUpdate("INSERT INTO a VALUES(2, 'test12')");
//			stmt.executeUpdate("CREATE TABLE a (id int not null primary key, value varchar(20))");
			ResultSet set = stmt.executeQuery("SELECT id, value from a;");
			
			printResultSet(set);
			
			stmt.close();
			connection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		frame.setVisible(true);
		
		System.out.println("done");
	}
	
	private static void printResultSet(ResultSet set) throws SQLException
	{
		while (set.next())
		{
			for (int i = 1;; i++)
			{
				try
				{
					System.out.print(set.getString(i) + " ");
				}
				catch (SQLException e)
				{
					break;
				}
			}
			
			System.out.println();
		}
	}
}