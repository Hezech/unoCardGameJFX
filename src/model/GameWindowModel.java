package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import model.CardModel.Color;
import model.CardModel.Value;


public class GameWindowModel 
{
	
	private String playerName;
	private int playerPoints;
	private int shrikePoints;
	private int sharkPoints;
	private int kittensPoints;
	private DeckModel deck;
	private ArrayList<PlayerModel> playerList;
	private boolean order; //true -> clockwise
	private CardModel.Color validColor;
	private CardModel.Value validValue;
	private int currentTurn;
	private String dogName;
	
	public GameWindowModel(String playerName, int playerPoints, int shrikePoints, int sharkPoints, int kittensPoints, String dogName)
	{
		this.playerName = playerName;
		this.playerPoints = playerPoints;
		this.shrikePoints = shrikePoints;
		this.sharkPoints = sharkPoints;
		this.kittensPoints = kittensPoints;
		this.dogName = dogName;
		deck = new DeckModel();
		ArrayList<CardModel> deckList = deck.getDeck();
		order = true;
		currentTurn = 0;
		
		//following code adds first card to discardpile
		int i = 0;
		CardModel topCard = deckList.get(i);
		String[] bannedValues = {"SKIP", "REVERSE", "DRAWTWO", "DRAWFOUR", "CHANGECOLOR"};
		while (Arrays.asList(bannedValues).contains(topCard.getValue().toString()))
		{
			deck.shuffle();
			deckList = deck.getDeck();
			topCard = deckList.get(i);
		}
		deck.setLastPlayedCard(topCard);
		deck.removeCard(topCard);
		
		//sets valid color and value
		setValidColor(deck.getPlayedCard().getColor());
		setValidValue(deck.getPlayedCard().getValue());
		
		//sets players
		PlayerModel human = new PlayerModel(getPlayerName(), getPlayerPoints(), getDogName());
		PlayerModel shrike = new PlayerModel("Shrike", shrikePoints, "shrike");
		PlayerModel shark = new PlayerModel("Shark", sharkPoints, "shark");
		PlayerModel kittens = new PlayerModel("Kittens", kittensPoints, "kittens");
		playerList = new ArrayList<PlayerModel>();
		playerList.add(human);
		playerList.add(kittens);
		playerList.add(shark);
		playerList.add(shrike);	
		//playerList = [human, kittens, shark, shrike]    <- final result
		
		
		//sets players' hands
		for (PlayerModel player: getPlayerList())
		{
			for (int j = 0; j < 7; j++)
			{
				player.addCard(getDeck().drawCard());
			}	
		}
	}

	public boolean getOrder() 
	{
		return order;
	}
	
	public void setOrder()
	{
		order = !(order);
	}

	public DeckModel getDeck() 
	{
		return deck;
	}
	
	public void setEffects()
	{
		String lastCardV = deck.getPlayedCard().getValue().toString();
		switch(lastCardV)
		{
			default:  //if card doesn't do anything like draw/change order/skip/changecolor
			{
				if (order == true)
				{
					if (getCurrentTurn() < 3) setTurn(getCurrentTurn()+1); 
					else setTurn(0);
					break;
				}
				else if (currentTurn > 0) currentTurn--; 
				else currentTurn = 3;
				break;
			}
			case "REVERSE":
			{
				if (order == true)
				{
					if (currentTurn > 0) currentTurn--; 
					else currentTurn = 3;
					order = !(order); 
					break;
				}
				else if (currentTurn < 3) //order is currently == false
				{
					currentTurn++; 
					order = !(order);
					break;
				}
				else currentTurn = 0; 
				break;
			}
			case "SKIP":
			{
				if (order == true)
				{
					switch(getCurrentTurn())
					{
						//valid for 0,1
						default: currentTurn = currentTurn+2; break;
						case 2: currentTurn = 0; break;
						case 3: currentTurn = 1; break;
					}
				}
				else //order == false
				{
					switch(currentTurn)
					{
					//valid	for 3,2
					default: currentTurn = currentTurn-2; break;
					case 1: currentTurn = 3; break;
					case 0: currentTurn = 2; break;
					}
				}
				break;
			}
			case "DRAWTWO":
			{
				if (getOrder() == true)
				{
					switch(getCurrentTurn())
					{
						default:  //valid for 0,1 
						{
							setTurn(getCurrentTurn()+1);
							playerList.get(getCurrentTurn()).addCard(deck.drawCard());
							playerList.get(getCurrentTurn()).addCard(deck.drawCard());
							setTurn(getCurrentTurn()+1);
							break;
						}
						case 2:
						{
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							currentTurn = 0;
							break;
						}
						case 3:
						{
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							currentTurn = 1;
							break;
						}
					}
				}
				else  //order == false
				{
					switch(getCurrentTurn())
					{
						default: //3,2
						{
							currentTurn--;
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							currentTurn--;
							break;
						}
						case 1:
						{
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							currentTurn = 3;
							break;
						}
						case 0:
						{
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							currentTurn = 2;
							break;
						}
					}
				}
				break;
			}
			case "DRAWFOUR":
			{
				if (order == true)
				{
					switch(currentTurn)
					{
						default:  //valid for 0,1 
						{
							System.out.println("DRAWFOUR here!");
							currentTurn++;
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							currentTurn++;
							break;
						}
						case 2:
						{
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							setTurn(0);
							break;
						}
						case 3:
						{
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							setTurn(1);
							break;
						}
					}
				}
				else  //order == false
				{
					switch(currentTurn)
					{
						default: //3,2
						{
							currentTurn--;
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							playerList.get(currentTurn).addCard(deck.drawCard());
							currentTurn--;
							break;
						}
						case 1:
						{
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							playerList.get(0).addCard(deck.drawCard());
							currentTurn = 3;
							break;
						}
						case 0:
						{
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							playerList.get(3).addCard(deck.drawCard());
							currentTurn = 2;
							break;
						}
					}
				}
				break;
			}
		}
	}

	public ArrayList<PlayerModel>  getPlayerList() 
	{
		return playerList;
	}
	
	public void setTurn(int n)
	{
		currentTurn = n;
	}
	
	public int getCurrentTurn()
	{
		return currentTurn;
	}
	
	
	public CardModel.Color getValidColor()
	{
		return validColor;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}

	public int getPlayerPoints() 
	{
		return playerPoints;
	}

	public int getShrikePoints() 
	{
		return shrikePoints;
	}

	public int getSharkPoints() 
	{
		return sharkPoints;
	}

	public int getKittensPoints() 
	{
		return kittensPoints;
	}
	
	public String getDogName()
	{
		return dogName;
	}

	public CardModel.Value getValidValue() 
	{
		return validValue;
	}

	public void setValidValue(CardModel.Value validValue) 
	{
		this.validValue = validValue;
	}
	
	public void setValidColor(CardModel.Color validColor)
	{
		this.validColor = validColor;
	}
	
	public void playAI(int indexOfPlayerList)
	{
		PlayerModel currentAIPlayer = playerList.get(indexOfPlayerList);
		boolean playFlag = false;
		for (CardModel card: currentAIPlayer.getHand())
		{
			if ((card.getColor() == getValidColor()) || (card.getValue() == getValidValue())
					|| card.getColor() == Color.SPECIAL)
			{
				currentAIPlayer.playCard(card);
				playFlag = true;
				deck.getDiscardPile().add(card);
				setEffects();
				System.out.println(card.toString());
				setValidValue(card.getValue());
				if (card.getColor() == Color.SPECIAL)
				{
					pickAnotherColor(getValidColor());
					break;
				}
				setValidColor(card.getColor());
				break;
			}
		}
		if (playFlag == false)
		{
			CardModel newCard = deck.drawCard();
			currentAIPlayer.addCard(newCard);
			playFlag = true;
			if ((newCard.getColor() == getValidColor()) || (newCard.getValue() == getValidValue())
					|| (newCard.getColor() == Color.SPECIAL))
			{
				currentAIPlayer.playCard(newCard);
				deck.getDiscardPile().add(newCard);
				setValidValue(newCard.getValue());
				setEffects();
				System.out.println(newCard.toString());
				if ((newCard.getColor() == Color.SPECIAL))
				{
					pickAnotherColor(getValidColor());
				}
				else setValidColor(newCard.getColor());
			}
			else
			{
				boolean order = getOrder();
				int nextTurn = getCurrentTurn();
				if (order == true)
				{
					if (getCurrentTurn() < 3) nextTurn++;
					else nextTurn = 0;
				}
				else
				{
					if (getCurrentTurn() > 0) nextTurn--;
					else nextTurn = 4;
				}
				setTurn(nextTurn);
			}
		}	
	}
	

	//AI method
	public void pickAnotherColor(CardModel.Color oldColor)
	{
		//R = 0; G = 1; B = 2; Y = 3;
		CardModel.Color[] colors = {CardModel.Color.RED, CardModel.Color.GREEN, CardModel.Color.BLUE, CardModel.Color.YELLOW};
		Random rand = new Random();
		
		//removing color that we want to change
		List<CardModel.Color> filteredColors = Arrays.stream(colors)
			    .filter(color -> !color.equals(oldColor))
			    .collect(Collectors.toList());
		
		//setting new random color
		int index = rand.nextInt(filteredColors.size());
		setValidColor(filteredColors.get(index));
	}

	public boolean checkValidPlay(String cardColor, String cardValue) 
	{
		//if its a special card:
		if (cardColor.equals("SPECIAL"))
		{
			//find the exact card to play from player's hand
			for (CardModel card: getPlayerList().get(0).getHand())
			{
				if (card.getValue().toString().equals(cardValue))
				{
					playCard(card);
					return true;
				}
			}
		}
		
		//otherwise, check current discarded card on discardpile
	//	CardModel currentDiscardedCard = getDeck().getPlayedCard();
		
		//check if selected card is playable on top of discarded card
		if (getValidColor().toString().equals(cardColor)
				|| getValidValue().toString().equals(cardValue))
		{
			//find the exact card to play from player's hand
			for (CardModel card: getPlayerList().get(0).getHand())
			{
				if (card.getColor().toString().equals(cardColor)
						&& card.getValue().toString().equals(cardValue))
				{
					playCard(card);
					return true;
				}
			}
		}
		return false;
	}

	private void playCard(CardModel selectedCard) 
	{
		getPlayerList().get(0).playCard(selectedCard);
		getDeck().getDiscardPile().add(selectedCard);
		if (selectedCard.getColor() != Color.SPECIAL)
		{
			setEffects();
			setValidColor(selectedCard.getColor());
		}
		setValidValue(selectedCard.getValue());
	}

	public boolean checkTurn(int index) 
	{
		if (index == getCurrentTurn()) return true;
		return false;
	}

	public void savePoints(String name) 
	{
		//set points to add to each player
		for (int i = 0; i < 4; i++)
		{
			getPlayerList().get(i).setPoints();
		}
		
		int playerPoints = getPlayerList().get(0).getPoints();
		int kittensPoints = getPlayerList().get(1).getPoints();
		int sharkPoints = getPlayerList().get(2).getPoints();
		int shrikePoints = getPlayerList().get(3).getPoints();
		
		DatabaseConnection dbConnection = new DatabaseConnection();
		String mysqlOperation = "UPDATE game_history SET player_points = ?, kittens_points = ?,"
				+ " shark_points = ?, shrike_points = ? WHERE player_name = ?";
		try
		{
			Connection con = dbConnection.getConnection();
			PreparedStatement statement = con.prepareStatement(mysqlOperation);
			statement.setInt(1, playerPoints);
		    statement.setInt(2, kittensPoints);
		    statement.setInt(3, sharkPoints);
		    statement.setInt(4, shrikePoints);
		    statement.setString(5, name);
			statement.executeUpdate();
		}
		catch (Exception e)
		{
			//e.printStackTrace();
		}	
		
	}
}
