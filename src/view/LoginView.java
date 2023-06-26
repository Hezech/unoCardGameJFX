package view;

import java.io.IOException;
import controller.LoginWindowController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class LoginView extends Application 
{	
	
	@Override
	public void start(Stage primaryStage) throws IOException 
	{	
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
		LoginWindowController logWinCtrl = new LoginWindowController();
		loader.setController(logWinCtrl);
		Parent root = loader.load();	
		
		Image icon = new Image("file:src/view/images/unoLogo.png");
		primaryStage.getIcons().add(icon);
		
		logWinCtrl.getDogMenu().setItems(FXCollections.observableArrayList("Chihuahua", "Husky", "German Shepherd"));
		
		Scene scene = new Scene(root);
		primaryStage.setTitle("UNO login window");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		Music.playMusic("src/loginMusic.wav");
	}
	
	public static void main(String[] args)
	{
		launch();
	}
	
}