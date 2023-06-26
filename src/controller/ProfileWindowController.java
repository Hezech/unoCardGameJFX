package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.GameWindowModel;
import model.ProfileWindowModel;
import view.GameWindow;
import view.Music;

public class ProfileWindowController 
{
	
	public ProfileWindowController(ProfileWindowModel profWinMod)
	{
		this.profWinMod = profWinMod;
	}
	
	@FXML
	private ImageView profilePic;
	@FXML
	private Label nickname;
	@FXML
	private Button playButton;
	@FXML
	private Button backButton;
	@FXML
	private Label catsPoints;
	@FXML
	private Label sharkPoints;
	@FXML
	private Label shrikePoints;
	
	private Scene preScene;
	private ProfileWindowModel profWinMod;
	
	public void setProfilePicture(String imagePath)
	{
		profilePic.setImage(new Image(imagePath));
	}
	
	public void setName(String name)
	{
		nickname.setText(name); 
	}
	
	@FXML
	private void startGame(MouseEvent leftClick) throws IOException 
	{
		Stage stage = (Stage) playButton.getScene().getWindow();
		String playerName = profWinMod.getName();
		int playerPoints = Integer.parseInt(profWinMod.getPlayerPoints());
		int shrikePoints = Integer.parseInt(profWinMod.getShrikePoints());
		int sharkPoints = Integer.parseInt(profWinMod.getSharkPoints());
		int kittensPoints = Integer.parseInt(profWinMod.getKittensPoints());
		GameWindowModel gameWinMod = new GameWindowModel(playerName, playerPoints, shrikePoints, sharkPoints, kittensPoints, profWinMod.getProfilePic());
		GameWindow gameWindow = new GameWindow(gameWinMod);
		gameWindow.start(stage);
		Music.stopMusic("src/gameHistoryMusic.wav");
		Music.playMusic("src/profileMusic.wav");
	}
	
	@FXML
	public Button getPlayButton()
	{
		return playButton;
	}
	
	public void setPreScene(Scene scene)
	{
		preScene = scene;
	}
	
	@FXML
	private void goBack(MouseEvent leftclick)
	{
		Music.stopMusic("src/gameHistoryMusic.wav");
		Stage stage = (Stage) nickname.getScene().getWindow();
	    stage.setScene(preScene);
	    stage.setTitle("UNO login window");
	    Music.playMusic("src/loginMusic.wav");
	    stage.show();
	}

	public void setShrike(String shrikeData) 
	{
		shrikePoints.setText(shrikeData);
	}
	
	public void setShark(String sharkData) 
	{
		sharkPoints.setText(sharkData);
	}
	
	public void setKittens(String kittensData) 
	{
		catsPoints.setText(kittensData);
	}
}
