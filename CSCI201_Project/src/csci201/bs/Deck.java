package csci201.bs;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

	ArrayList<Card> deck = new ArrayList<Card>();
	int topIndex = 0;
	
	public Deck()
	{
		char suits[] = {'C','D','H','S'};
		for(int i=0; i<52; i++)
			this.deck.add(new Card(suits[i/13], i%13));
	}
	
	public void shuffle()
	{
		Collections.shuffle(this.deck);
		topIndex = 0;
	}
	
	public Card getTop()
	{
		Card topCard = null;
		if(!this.deck.isEmpty())
		{
			topCard = this.deck.get(topIndex);
			this.deck.remove(topIndex);
		}
		return topCard;
	}
	
	public void addCard(Card card)
	{
		this.deck.add(card);
	}
	
	public void addCard(ArrayList<Card> card)
	{
		for(int i=0; i<card.size(); i++)
			this.deck.add(card.get(i));
	}
	
	public boolean removeCards(int num)
	{
		if(num>this.deck.size())
			return false;
		for(int i=0; i<num; i++)
			this.deck.remove(0);
		return true;
	}
	
	public boolean isEmpty()
	{
		if(this.deck.size()<=0)
			return true;
		else
			return false;
	}
	
	public ArrayList<Card> getDeck()
	{
		return this.deck;
	}
}
