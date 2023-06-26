package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PlayerModel 
{
	private String name;
	private String animalName;
	private ArrayList<CardModel> hand;
	private int points;
	private boolean canDraw;
	
	public PlayerModel(String name, int points, String animalName)
	{
		this.name = name;
		this.points = points;
		this.animalName = animalName;
		canDraw = true;
		hand = new ArrayList<CardModel>();
	}
	
	public ArrayList<CardModel> getHand()
	{
		return hand;
	}
	
	public int getHandSize()
	{
		return hand.size();
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints()
	{
		Map<String, Integer> map = new HashMap<>();
	    map.put("ONE", 1);
	    map.put("TWO", 2);
	    map.put("THREE", 3);
	    map.put("FOUR", 4);
	    map.put("FIVE", 5);
	    map.put("SIX", 6);
	    map.put("SEVEN", 7);
	    map.put("EIGHT", 8);
	    map.put("NINE", 9);
	    map.put("SKIP", 20);
	    map.put("REVERSE", 20);
	    map.put("DRAWTWO", 20);
	    map.put("DRAWFOUR", 50);
	    map.put("CHANGECOLOR", 50);
		for (CardModel card: hand)
		{
			String cardPoints = card.getValue().toString();
			try {int intPoints = map.get(cardPoints); this.points += intPoints;}
			catch (Exception e) {e.printStackTrace();}
		//	this.points += intPoints;
		}
	}

	public void addCard(CardModel drawnCard) 
	{
		hand.add(drawnCard);
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean getDrawPermission()
	{
		return canDraw;
	}
	
	public void setDrawPermission()
	{
		canDraw = !(canDraw);
	}
	
	public String getAnimalName()
	{
		return animalName;
	}

	public void playCard(CardModel card) 
	{
		getHand().remove(card);
		setDrawPermission();
	}

	public void setDrawPermission(boolean permission) 
	{
		canDraw = permission;
	}
	
}
