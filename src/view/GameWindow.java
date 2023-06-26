package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import controller.GameWindowController;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.GameWindowModel;
import model.CardModel;

public class GameWindow 
{
	private List<Button> buttonList;
	private Label order;
	private Label color;
	private GameWindowModel gameWinMod;
	private Label deckSize;
	private ImageView discardPile;
	private Label playerTurn;
	private Label handSizeKittensL;
	private Label handSizeSharkL;
	private Label handSizeShrikeL;
	
	
	public GameWindow(GameWindowModel gameWinMod)
	{
		this.gameWinMod = gameWinMod;
	}
	
	public void start(Stage stage) throws IOException
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("GameWindow.fxml"));
		GameWindowController gameWinCtrl = new GameWindowController(gameWinMod, this);
		loader.setController(gameWinCtrl);
		Parent root = loader.load();
		
		ButtonBar btnBar1 = gameWinCtrl.getButtonBar1();
		ButtonBar btnBar2 = gameWinCtrl.getButtonBar2();

		//setting human player's profile picture and name
		String currentPath = System.getProperty("user.dir")+"/";
		String imagePath = currentPath+gameWinMod.getPlayerList().get(0).getAnimalName().replace("file:","");
		Image image = new Image("file:"+imagePath);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(98);
		imageView.setFitHeight(73);
		gameWinCtrl.getHumanPic().getChildren().add(imageView);
		gameWinCtrl.getHumanPic().setAlignment(Pos.CENTER);
		gameWinCtrl.getPlayerNameL().setText(gameWinMod.getPlayerList().get(0).getName());
		gameWinCtrl.getPlayerNameL().setFont(Font.font(null, FontWeight.BOLD, 16));
		
		//setting card buttons
		buttonList = new ArrayList<Button>();	
		buttonList.addAll(getCardButtons(btnBar1, 1));
		buttonList.addAll(getCardButtons(btnBar2, 11));
		
		//setting card images to card buttons
		setCardImages(buttonList, 7);
		
		//setting some buttons invisible
		setInvisibility(buttonList, 7);
		
		//setting order
		order = gameWinCtrl.getOrderLabel();
		writeOrder(order);
		
		//setting dicardpile pic
		discardPile = gameWinCtrl.getDiscardPile();
		putImage(discardPile);
		
		//setting decksize label
		deckSize = gameWinCtrl.getDeckSizeLabel();
		setDeckSize(deckSize);
		
		//setting handsize labels
		handSizeKittensL = gameWinCtrl.getHandSizeKittensL();
		handSizeSharkL = gameWinCtrl.getHandSizeSharkL();
		handSizeShrikeL = gameWinCtrl.getHandSizeShrikeL();
		setHandSizeLabel(handSizeKittensL, 1);
		setHandSizeLabel(handSizeSharkL, 2);
		setHandSizeLabel(handSizeShrikeL, 3);
		
		//setting turn label
		playerTurn = gameWinCtrl.getTurnLabel();
		setPlayerTurnLabel(playerTurn);
		
		//setting color label
		color = gameWinCtrl.getColorLabel();
		setColor(color);
		
		//setting scene
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		
		Music.playMusic("src/gameHistoryMusic.wav");
	}
	
	private void setPlayerTurnLabel(Label playerTurn)
	{
		String turnText1= "It's ";
		String turnText2= "'s turn";
		String playerName = gameWinMod.getPlayerList().get(gameWinMod.getCurrentTurn()).getName();
		String textTurnLabel = turnText1+playerName+turnText2;
		playerTurn.setText(textTurnLabel);
		playerTurn.setFont(Font.font(null, FontWeight.BOLD, 16));
	}
	
	private void setColor(Label colorL)
	{
		String colorText = "Current valid color is: ";
		colorL.setText(colorText+gameWinMod.getValidColor().toString().toUpperCase());
		color.setFont(Font.font(null, FontWeight.BOLD, 16));
	}
	
	private void setInvisibility(List<Button> buttonList, int i) 
	/**
	 * it gets list of buttons, it sets buttons from given index and onward as invisible
	 */
	{
		for (int j = i; j < 20; j++)
		{
			buttonList.get(j).setVisible(false);
		}
	}

	private void setCardImages(List<Button> buttonList, int max) 
	{
		for (int i = 0; i < max; i++)
		{
			Button button = buttonList.get(i);
			CardModel card = gameWinMod.getPlayerList().get(0).getHand().get(i);
			String currentPath = System.getProperty("user.dir");
			String cardPath = "/src/unoCards/"+ card.toString()+".png";
			Image cardImage = new Image("file:"+currentPath+cardPath);
			ImageView imageView = new ImageView(cardImage);
			imageView.setFitWidth(60);
			imageView.setFitHeight(100);
			imageView.setSmooth(true);
			button.setPrefWidth(60);
			button.setPrefHeight(100);
			button.setAlignment(Pos.TOP_LEFT);
			button.setGraphic(imageView);
			button.setPadding(Insets.EMPTY);	
			button.setVisible(true);  //added
		}		
	}

	private void setHandSizeLabel(Label sizeLabel, int index) 
	{
		sizeLabel.setText("x"+gameWinMod.getPlayerList().get(index).getHandSize());
	}

	public List<Button> getCardButtons(ButtonBar btnBar, int i)
	{
		ObservableList<Node> nodes = btnBar.getButtons();
		FilteredList<Node> filteredNodes = new FilteredList<>(nodes, node -> node instanceof Button);
		SortedList<Node> sortedNodes = new SortedList<>(filteredNodes);
		List<Button> buttonList = sortedNodes.stream()
			.map(node -> (Button)node)
			.collect(Collectors.toList());
		
		return buttonList;
	}
	
	public void writeOrder(Label orderL)
	{
		/**
		 *  writes current order on order's label
		 */
		boolean order = gameWinMod.getOrder();
		if (order == true) { orderL.setText("CLOCKWISE ORDER");}
		else { orderL.setText("COUNTERCLOCKWISE ORDER");}
		orderL.setFont(Font.font(null, FontWeight.BOLD, 16));
	}
	
	public void putImage(ImageView image)
	{
		/**
		 * sets image to discardpile's imageview
		 */
		String currentPath = System.getProperty("user.dir");
		String cardPath = "/src/unoCards/"+gameWinMod.getDeck().getPlayedCard().getColor().toString()+"_"+gameWinMod.getDeck().getPlayedCard().getValue().toString()+".png";
		
		Image cardImage = new Image("file:"+currentPath+cardPath);
		image.setImage(cardImage);
	}
	
	public void setDeckSize(Label deckLabel)
	{
		/**
		 * sets text to the label corresponding to size of deck
		 */
		deckLabel.setText("x"+gameWinMod.getDeck().getRemainingCards());
	}
	
	public void repaint() 
	{	
		/**
		 * repaints the view
		 */
		setCardImages(buttonList, gameWinMod.getPlayerList().get(0).getHandSize());
		setInvisibility(buttonList, gameWinMod.getPlayerList().get(0).getHandSize());
		writeOrder(order);
		setDeckSize(deckSize);
		setColor(color);
		putImage(discardPile);
		setPlayerTurnLabel(playerTurn);
		setHandSizeLabel(handSizeKittensL, 1);
		setHandSizeLabel(handSizeSharkL, 2);
		setHandSizeLabel(handSizeShrikeL, 3);
	}

	public void sayUNO() 
	{
		Dialog dialogWindow = new Dialog();
		dialogWindow.setTitle("UNO");
		dialogWindow.setContentText(gameWinMod.getPlayerList().get(0).getName() + " has only ONE card left!!!");
		dialogWindow.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
		dialogWindow.show();
	}
	
	public void drawPermissionDenied()
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Draw permission handler");
		alert.setHeaderText("Draw permission denied");
		alert.setContentText("You can't draw now");
		alert.showAndWait();
	}
	
	public String pickColor()
	{
		ChoiceDialog<String> colorWindow = new ChoiceDialog<>("RED", "GREEN", "YELLOW", "BLUE");
		colorWindow.setTitle("Pick a color");
		colorWindow.setContentText("Please pick one of the following colors:");
		
		Optional<String> result = colorWindow.showAndWait();
		System.out.println(result.get());
		return result.get();
	}

	public void cantSayUNO() 
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("UNO permission handler");
		alert.setHeaderText("UNO permission denied");
		alert.setContentText("You have more than one card");
		alert.showAndWait();
	}

	public void cantPlayCard() 
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Play permission handler");
		alert.setHeaderText("Play permission denied");
		alert.setContentText("You can't play this card");
		alert.showAndWait();
	}

	public void notYourTurn() 
	{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Turn permission handler");
		alert.setHeaderText("Turn permission denied");
		alert.setContentText("It's not your turn");
		alert.showAndWait();
	}

	public void declareWin(String name) 
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Game handler");
		alert.setHeaderText("Win alert");
		alert.setContentText(name+" has won!");
		alert.showAndWait();
	}
}
