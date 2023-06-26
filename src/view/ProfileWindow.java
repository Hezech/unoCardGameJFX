package view;

import java.io.IOException;
import controller.ProfileWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.ProfileWindowModel;

public class ProfileWindow 
{	
	private ProfileWindowModel profWinMod;
	private ProfileWindowController profWinCtrl;
	
	public ProfileWindowModel getModel()
	{
		return profWinMod;
	}
	
	public ProfileWindow(ProfileWindowModel profWinMod, ProfileWindowController profWinCtrl) 
	{
		this.profWinMod = profWinMod;
		this.profWinCtrl = profWinCtrl;
	}
	
	public void start(Stage stage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileWindow.fxml"));
		loader.setController(profWinCtrl);
		Parent root = loader.load();
		
		profWinCtrl.setProfilePicture(profWinMod.getProfilePic());
		String playerData = profWinMod.getName()+": "+profWinMod.getPlayerPoints();  //modify this
		String shrikeData = "Shrike: "+ profWinMod.getShrikePoints();
		String sharkData = "Shark: "+ profWinMod.getSharkPoints();
		String kittensData = "Kittens: "+ profWinMod.getKittensPoints();
		profWinCtrl.setShrike(shrikeData);
		profWinCtrl.setShark(sharkData);
		profWinCtrl.setKittens(kittensData);
		profWinCtrl.setName(playerData);
		
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		Music.playMusic("src/gameHistoryMusic.wav");
	}
}