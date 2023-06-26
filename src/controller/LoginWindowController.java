package controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.LoginWindowModel;
import model.ProfileWindowModel;
import view.Music;
import view.ProfileWindow;

public class LoginWindowController 
{
	
	private LoginWindowModel logWinMod = new LoginWindowModel();
	
	@FXML
	private ComboBox<String> dogMenu;
	@FXML
	private ImageView profilePicture;
	@FXML
	private TextField nameField;
	
	@FXML
	public void dogSelection() 
	{
	    String dogName = dogMenu.getValue().toString();
	    logWinMod.SetPlayerImagePath(dogName);
	    setProfilePicture(dogName);
	}
	
	public ComboBox<String> getDogMenu()
	{
		return dogMenu;
	}
	
	public void setProfilePicture(String dogName)
	{
		profilePicture.setImage(new Image("file:src/view/images/"+dogName+".png"));
	}
	
	@FXML
	private void submit(MouseEvent leftClick) throws IOException 
	{
		String name = nameField.getText();
		String imagePath = profilePicture.getImage().getUrl();
		Object dogName = dogMenu.getValue();
		
		if (name.equals("") || name.equals(" "))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid name");
			alert.setContentText("Please write at least a character as a name");
			alert.showAndWait();
			
		}
		
		else if (name.equals("Cat") || name.equals("Shark") || name.equals("Otter"))
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Name already taken");
			alert.setContentText("This name has already been taken");
			alert.showAndWait();
		}
		
		else if (dogName == null)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Invalid profile picture");
			alert.setContentText("Please select a dog as your profile picture");
			alert.showAndWait();
		}
		
		else 
		{
			logWinMod.setPlayerName(name);
			logWinMod.SetPlayerImagePath(imagePath);
			logWinMod.insertPlayer();
			logWinMod.insertPoints();
			Stage stage = (Stage) dogMenu.getScene().getWindow();
			ProfileWindowModel profWinMod = new ProfileWindowModel(logWinMod.getPlayerName());
			ProfileWindowController profWinCtrl = new ProfileWindowController(profWinMod);
			profWinCtrl.setPreScene(dogMenu.getScene());
			ProfileWindow profileWindow = new ProfileWindow(profWinMod, profWinCtrl);
			profileWindow.start(stage);
			Music.stopMusic("src/loginMusic.wav");
		}
	}
}