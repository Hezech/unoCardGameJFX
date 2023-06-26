package model;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoginWindowModel
{
	/**
	 * Main class for the login window
	 * It allows the user to type his/her name and pick a profile picture
	 */

	/**
	 * fields
	 */
	private String playerName;
	private String playerImagePath;
			
	public String getPlayerImagePath()
	{
		return playerImagePath;
	}
		
	public String getPlayerName()
	{
		return playerName;
	}
		
	public void setPlayerName(String newName)
	{
		playerName = newName;
	}
		
	public void SetPlayerImagePath(String imagePath)
	{
		playerImagePath = imagePath;
	}
	
	public void insertPlayer()
	{
		DatabaseConnection dbConnection = new DatabaseConnection();
		String mysqlOperation = "INSERT INTO player (name,profile_picture)"
				+ " VALUES (\'"+getPlayerName()+"\',"
				+ "\'"+ getPlayerImagePath()+"\');";
		try
		{
			Connection con = dbConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(mysqlOperation);
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}	
	}
	
	public void insertPoints()
	{
		DatabaseConnection dbConnection = new DatabaseConnection();
		String mysqlOperation = "INSERT INTO game_history (player_name)"
				+ "VALUES (\'"+getPlayerName()+"\');";
		try
		{
			Connection con = dbConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(mysqlOperation);
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}	
	}
	
	
}