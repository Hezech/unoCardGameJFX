package model;

/**
 * Model of cards
 * @author Petito Fulvio
 *
 */
public class CardModel 
{
	/**
	 * Enumerations of colors
	 * 
	 */
	public enum Color
	{
		RED, GREEN, BLUE, YELLOW, SPECIAL;
		
		private static final Color[] colors = Color.values();
		public static Color getColor(int c)  //0,1,2,3,4 for R,G,B,Y,S
		{
			return Color.colors[c]; 
		}
	}
	
	/**
	 * Enumerations of values
	 *
	 */
	public enum Value
	{
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, SKIP, 
		REVERSE, DRAWTWO, CHANGECOLOR, DRAWFOUR;
		
		private static final Value[] values = Value.values();
		public static Value getValue(int v)
		{
			return Value.values[v];
		}
	}
	
	private final Color color;
	private final Value numericValue;
	
	/**
	 * Constructor of CardModel
	 * @param color
	 * @param number
	 */
	public CardModel(final Color color, final Value number)
	{
		this.color = color;
		numericValue = number;
	}
	
	/**
	 * Color getter
	 * @param no parameters
	 * @return color
	 */
	public Color getColor()
	{
		return color;
	}
	
	/**
	 * Value getter
	 * @param no parameters
	 * @return numericValue
	 */
	public Value getValue()
	{
		return numericValue;
	}
	
	/**
	 * Makes a card printable as a string
	 * @param no parameters
	 * @return string of color+its value
	 */
	public String toString()
	{
		return color +"_"+numericValue;
	}
}