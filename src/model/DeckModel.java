package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import model.CardModel.Color;
import model.CardModel.Value;

/**
 * Model of class Deck
 * @author Petito Fulvio
 *
 */
public class DeckModel 
{
	private ArrayList<CardModel> deck;
	private int remainingCards;
	private ArrayList<CardModel> discardPile;
	
	/**
	 * Constructor of DeckModel
	 */
	public DeckModel()
	{
		deck = new ArrayList<>();
		remainingCards = 108;
		Color[] colorsWithoutSpecial = Arrays.copyOf(Color.values(), 4);
		Value[] valuesWithoutSpecial = Arrays.copyOf(Value.values(), 13);   //skip, reverse and +2 included
		for (Color c : colorsWithoutSpecial) 
		{
			for (Value nValue : valuesWithoutSpecial) 
			{
				CardModel card = new CardModel(c, nValue);
				if (card.getValue() != Value.ZERO) 
				{
					for(int i = 0; i < 2; i++) 
					{
						deck.add(card);
					}
				}
				else 
				{
					deck.add(card);
				}
			}
		}
		Value[] SpecialValues = {Value.CHANGECOLOR, Value.DRAWFOUR};
		for (Value value : SpecialValues)
		{
			for (int i = 0; i < 4; i++)
			{
				CardModel card = new CardModel(Color.SPECIAL, value);
				deck.add(card);
			}
		}
		
		shuffle();
		discardPile = new ArrayList<>();
	}
	
	/**
	 * Getter of remaining cards
	 * @return remainingCards
	 */
	public int getRemainingCards()
	{
		return remainingCards;
	}
	
	/**
	 * Setter of remaining cards
	 * @param remValue
	 */
	public void setRemainingCards(int remValue)
	{
		remainingCards = remValue;
	}
	
	/**
	 * Getter of deck
	 * @return deck
	 */
	public ArrayList<CardModel> getDeck()
	{
		return deck;
	}
	
	/**
	 * Setter of deck
	 * @param newCardList
	 */
	public void setDeck(ArrayList<CardModel> newCardList)
	{
		deck = newCardList;
	}
	
	/**
	 * Resets the deck
	 */
	public void reset()
	{
		DeckModel nDeck = new DeckModel();
		this.setRemainingCards(nDeck.getRemainingCards());
		this.setDeck(nDeck.getDeck());
	}
	
	/**
	 * Getter of deck size
	 * @return size of the deck
	 */
	public int getDeckSize()
	{
		return deck.size();
	}
	
	/**
	 * Prints each card in the deck
	 */
	public void printer()
	{
		for (CardModel card : deck)
		{
			System.out.println(card);
		}
	}
	
	/**
	 * Shuffles cards in the deck
	 */
	public void shuffle()
	{
		Collections.shuffle(getDeck());
	}
	
	/**
	 * Getter of discard pile
	 * @return discard pile
	 */
	public ArrayList<CardModel> getDiscardPile()
	{
		return discardPile;
	}
	
	/**
	 * Adds a card to discard pile
	 * @param card
	 */
	public void setLastPlayedCard(CardModel card)
	{
		discardPile.add(card);
	}
	
	/**
	 * Getter of card on top of discard pile
	 * @return discard pile top card
	 */
	public CardModel getPlayedCard()         //current visible card on the discard pile
	{
		return discardPile.get(discardPile.size()-1);
	}

	
	/**
	 * removes given card from deck
	 * @param card
	 */
	public void removeCard(CardModel card)
	{
		remainingCards--;
		deck.remove(card);
	}
	
	public CardModel drawCard()
	{
		CardModel card = deck.get(deck.size()-1);
		removeCard(card);
		shuffle();
		return card;
	}
}