package csci201.bs;

public class Card implements Comparable<Card>{
	
	private final String cardValues[] = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	private char suit;
	private int value;

	
	public Card(char suit, int value)
	{
		this.suit = suit;
		this.value = value;
	}
	
	public String [] getCardValues()
	{
		return cardValues;
	}
	
	public String toString()
	{
		String card = cardValues[this.value] + "_" + this.suit;
		// Card String format
		return card;
	}
	
	public char getSuit()
	{
		return this.suit;
	}
	
	public int getRankNum()
	{
		return value;
	}

	public void setCard(char s, int v)
	{
		this.suit = s;
		this.value = v;
	}
	
	public boolean cardIsEqual(Card c)
	{
		if (this.suit == c.suit && this.value == c.value)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int compareTo(Card otherCard) {
		return Integer.compare(value, otherCard.value);
	}
	
}
