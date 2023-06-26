package controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.CardModel.Color;
import model.GameWindowModel;
import view.GameWindow;


public class GameWindowController 
{
	
	private GameWindowModel gameModel;
	private GameWindow gameWin;
	
	@FXML
	private ButtonBar buttonBar1;
	@FXML
	private ButtonBar buttonBar2;
	@FXML
	private AnchorPane playerPane;
	@FXML
	private AnchorPane kittensPane;
	@FXML
	private Label orderLabel;
	@FXML
	private ImageView discardPile;
	@FXML
	private Label deckSizeLabel;
	@FXML
	private Label kittensHandLabel;
	@FXML
	private Label shrikeHandLabel;
	@FXML
	private Label sharkHandLabel;
	@FXML
	private Label colorLabel;
	@FXML
	private Label turnLabel;
	@FXML
	private StackPane humanPic;
	@FXML
	private Label playerNameL;
	@FXML
	private Button passButton;
	
	public GameWindowController(GameWindowModel gameWinMod, GameWindow gameWindow)
	{
		gameModel = gameWinMod;
		gameWin = gameWindow;
	}
	
	
	public void gameLoop() 
	{     	
		//check if someone has 0 cards (=has won)
		for (int i = 0; i < 4; i++)
		{
			if (gameModel.getPlayerList().get(i).getHandSize() == 0)
			{
				System.out.println("Game has finished!");
				gameModel.savePoints(gameModel.getPlayerList().get(i).getName());
				gameWin.declareWin(gameModel.getPlayerList().get(i).getName());
			}
		}
		
		//set waiting time
		PauseTransition pause = new PauseTransition(Duration.seconds(6));

	    //set the action to be taken when the pause is finished
	    pause.setOnFinished(event -> {
	        //play the AI's turn
	        System.out.println(gameModel.getPlayerList().get(gameModel.getCurrentTurn()).getName() + " is about to play!");
	    	gameModel.playAI(gameModel.getCurrentTurn());
	        
	        //print a message indicating the name of the next player
	        System.out.println(gameModel.getPlayerList().get(gameModel.getCurrentTurn()).getName() + " is next!");
	        System.out.println("current turn is: "+ gameModel.getCurrentTurn());
	        //call the repaint method
	        gameWin.repaint();

	        //if the game is not over (currentTurn is not 0), start the next iteration of the loop
	        if (gameModel.getCurrentTurn() != 0) pause.play();
	    });

	    //start the first iteration of the loop
	    pause.play();
		
	}
	
	@FXML
	public void passTurn(ActionEvent leftclick) 
	{
		if (gameModel.getCurrentTurn() == 0)
		{
			if (gameModel.getOrder() == true) gameModel.setTurn(1);
			else gameModel.setTurn(3);
			gameLoop();
			gameWin.repaint();
			gameModel.getPlayerList().get(0).setDrawPermission(true); //overloaded
		}
		else gameWin.notYourTurn();
	}
	
	@FXML
	public void drawCard(ActionEvent leftclick) 
	{
		if (gameModel.getPlayerList().get(0).getDrawPermission() == true
				&& gameModel.getCurrentTurn() == 0)
		{
			gameModel.getPlayerList().get(0).addCard(gameModel.getDeck().drawCard());
			gameModel.getPlayerList().get(0).setDrawPermission();
			gameWin.repaint();
		}
		else gameWin.drawPermissionDenied();
		
	}
	
	@FXML
	public void sayUNO(ActionEvent event)
	{
		if (gameModel.getPlayerList().get(0).getHandSize() == 1) gameWin.sayUNO();
		else gameWin.cantSayUNO();
	}
	
	@FXML
	public void playCard(ActionEvent event) 
	{
		if (gameModel.checkTurn(0) == true)
		{
			Button sourceButton =  (Button) event.getSource();
			ImageView imageView = (ImageView) sourceButton.getGraphic();
			Image image = imageView.getImage();
			String path = image.getUrl();
			int lastSlashIndex = path.lastIndexOf("/");
			String cardName = path.substring(lastSlashIndex + 1);
			cardName = cardName.replace(".png", "");
			
			String[] parts = cardName.split("_");
			String cardColor = parts[0];
			String cardValue = parts[1];
			
			if (gameModel.checkValidPlay(cardColor, cardValue) == false) gameWin.cantPlayCard();
			else 
			{
				gameWin.repaint();
				gameLoop();
				gameModel.getPlayerList().get(0).setDrawPermission(true);
				
				//check if you played a drawfour or changecolor
				if (gameModel.getDeck().getPlayedCard().getColor().toString().equals("SPECIAL"))
				{
					gameModel.setValidColor(Color.valueOf(gameWin.pickColor()));
					gameModel.setEffects();
					gameWin.repaint();
				}
			}
		}
		else gameWin.notYourTurn();
	}
	
	public ButtonBar getButtonBar1()
	{
		return buttonBar1;
	}
	
	public ButtonBar getButtonBar2()
	{
		return buttonBar2;
	}
	
	public AnchorPane getPlayerPane()
	{
		return playerPane;
	}
	
	public AnchorPane getKittensPane()
	{
		return kittensPane;
	}
	
	public Label getOrderLabel()
	{
		return orderLabel;
	}
	
	public ImageView getDiscardPile()
	{
		return discardPile;
	}
	
	public Label getDeckSizeLabel()
	{
		return deckSizeLabel;
	}

	public Label getHandSizeKittensL() 
	{
		return kittensHandLabel;
	}

	public Label getHandSizeSharkL() 
	{
		return sharkHandLabel;
	}

	public Label getHandSizeShrikeL() 
	{
		return shrikeHandLabel;
	}
	
	public Label getColorLabel()
	{
		return colorLabel;
	}
	
	public Label getTurnLabel()
	{
		return turnLabel;
	}
	
	public StackPane getHumanPic()
	{
		return humanPic;
	}
	
	public Label getPlayerNameL()
	{
		return playerNameL;
	}

}
