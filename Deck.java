// This class represents the deck of cards from which cards are dealt to players.
public class Deck
{
	// define fields here
	private Card[] deck = new Card[52];
	public int cardsUsed;
	
	// This constructor builds a deck of 52 cards.
	public Deck()
	{
		int cardNumber = 0;
		for (int i = 0; i < 4; i++){
			for (int j = 1; j <= 13; j++){
				this.deck[cardNumber] = new Card (i, j);
				cardNumber++;
			}
		}
		cardsUsed = 0;
	}

	// This method takes the top card off the deck and returns it.
	public Card deal()
	{
		if(cardsUsed == deck.length){
			return null;
		}else{
			cardsUsed++;
			return deck[cardsUsed - 1];
		}
	}
	
	// this method returns true if there are no more cards to deal, false otherwise
	public boolean isEmpty()
	{
		if(cardsUsed == deck.length){
			return true;
		}
		else{
			return false;
		}
	}
	
	// this method puts the deck into some random order
	public void shuffle()
	{
		for (int i = 51; i > 0; i--){
			int randNumber = (int)(Math.random()*(i+1));
			Card temp = deck[i];
			deck[i] = deck[randNumber];
			deck[randNumber] = temp;
		}
		cardsUsed = 0;
	}
}