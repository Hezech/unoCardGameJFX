package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection
{
	
	private Connection databaseLink;
	
	public Connection getConnection()
	{
		String url = "****";
		
		try
		{
			databaseLink = DriverManager.getConnection(url, "****", "****");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return databaseLink;
	}
}
