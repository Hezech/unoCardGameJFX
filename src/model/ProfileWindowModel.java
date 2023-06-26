package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileWindowModel 
{
	private String name;
	private String profilePic;
	private String playerPoints;
	private String shrikePoints;
	private String sharkPoints;
	private String kittensPoints;
	
	public ProfileWindowModel(String name)
	{
		this.name = name;
		
		
		//gets player's pic from DB
		DatabaseConnection dbConnection = new DatabaseConnection();
		String mysqlOperation = "SELECT profile_picture "
							   +"FROM player "
							   +"WHERE name = '"+ getName() +"'";
		try
		{
			Connection con = dbConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(mysqlOperation);
			ResultSet result = statement.executeQuery();
			while (result.next())
			{
				profilePic = result.getString("profile_picture");
			}
			
			String mysqlOperation2 = "SELECT player_points, shrike_points, shark_points, cat_points"
					               + " FROM game_history WHERE player_name ='"	+  getName() +"'";
			PreparedStatement statement2 = con.prepareStatement(mysqlOperation2);
			ResultSet result2 = statement2.executeQuery();
			while (result2.next())
			{
				playerPoints = result2.getString("player_points");
				shrikePoints = result2.getString("shrike_points");
				sharkPoints = result2.getString("shark_points");
				kittensPoints = result2.getString("cat_points");
			}
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}	
	}
	
	public String getShrikePoints()
	{
		return shrikePoints;
	}
	
	public String getSharkPoints()
	{
		return sharkPoints;
	}
	
	public String getKittensPoints()
	{
		return kittensPoints;
	}
	
	public String getProfilePic()
	{
		return profilePic;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPlayerPoints()
	{
		return playerPoints;
	}
}
