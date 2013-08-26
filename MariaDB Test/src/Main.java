import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main
{
	public static void main(String args[])
	{
		new Main();
	}
	
	public Main()
	{
		try
		{
			Connection  connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", "root", "server");
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE a (id int not null primary key, value varchar(20))");
			stmt.close();
			connection.close();
		}
		catch (SQLException e)
		{
			
		}
	}
}