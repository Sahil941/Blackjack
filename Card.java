// This class represents one playing card.
public class Card
{
	// Card suits (provided for your convenience - use is optional)
	public static final int SPADES   = 0;
	public static final int HEARTS   = 1;
	public static final int CLUBS    = 2;
	public static final int DIAMONDS = 3;

	// Card faces (provided for your convenience - use is optional)
	public static final int ACE      = 1;
	public static final int TWO      = 2;
	public static final int THREE    = 3;
	public static final int FOUR     = 4;
	public static final int FIVE     = 5;
	public static final int SIX      = 6;
	public static final int SEVEN    = 7;
	public static final int EIGHT    = 8;
	public static final int NINE     = 9;
	public static final int TEN      = 10;
	public static final int JACK     = 11;
	public static final int QUEEN    = 12;
	public static final int KING     = 13;

	// define fields here
	int suit;
	int face;
	int value;
	
	// This constructor builds a card with the given suit and face, turned face down.
	public Card(int cardSuit, int cardFace)
	{
		if (cardSuit == 0){
			this.suit = SPADES;
		}else if (cardSuit == 1){
			this.suit = HEARTS;
		}else if (cardSuit == 2){
			this.suit = CLUBS;
		}else if (cardSuit == 3){
			this.suit = DIAMONDS;
		}else{
			System.out.println("This is not a valid Suit.");
		}
		
		if (cardFace == 1){
			this.face = ACE;
		}else if (cardFace == 2){
			this.face = TWO;
		}else if (cardFace == 3){
			this.face = THREE;
		}else if (cardFace == 4){
			this.face = FOUR;
		}else if (cardFace == 5){
			this.face = FIVE;
		}else if (cardFace == 6){
			this.face = SIX;
		}else if (cardFace == 7){
			this.face = SEVEN;
		}else if (cardFace == 8){
			this.face = EIGHT;
		}else if (cardFace == 9){
			this.face = NINE;
		}else if (cardFace == 10){
			this.face = TEN;
		}else if (cardFace == 11){
			this.face = JACK;
		}else if (cardFace == 12){
			this.face = QUEEN;
		}else if (cardFace == 13){
			this.face = KING;
		}else{
			System.out.println("This is not a valid Face.");
		}
	}

	// This method retrieves the suit (spades, hearts, etc.) of this card.
	public int getSuit()
	{
		return this.suit;
	}
	
	// This method retrieves the face (ace through king) of this card.
	public int getFace()
	{
		return this.face;
	}
	
	// This method retrieves the numerical value of this card
	// (usually same as card face, except 1 for ace and 10 for jack/queen/king)
	public int getValue()
	{
		if (this.face == 1){
			this.value = 1;
		}else if (this.face == 2){
			this.value = 2;
		}else if (this.face == 3){
			this.value = 3;
		}else if (this.face == 4){
			this.value = 4;
		}else if (this.face == 5){
			this.value = 5;
		}else if (this.face == 6){
			this.value = 6;
		}else if (this.face == 7){
			this.value = 7;
		}else if (this.face == 8){
			this.value = 8;
		}else if (this.face == 9){
			this.value = 9;
		}else if (this.face == 10 || this.face == 11 || this.face == 12 || this.face == 13){
			this.value = 10;
		}else{
			System.out.println("This card does not have a valid face, thus no value.");
		}
		return this.value;
	}
	
	public String toString(){
		String suit = "void";
		String face = "void";
		
		if (getFace() == 1){
			face = "Ace";
		}else if (getFace() == 2){
			face = "Two";
		}else if (getFace() == 3){
			face = "Three";
		}else if (getFace() == 4){
			face = "Four";
		}else if (getFace() == 5){
			face = "Five";
		}else if (getFace() == 6){
			face = "Six";
		}else if (getFace() == 7){
			face = "Seven";
		}else if (getFace() == 8){
			face = "Eight";
		}else if (getFace() == 9){
			face = "Nine";
		}else if (getFace() == 10){
			face = "Ten";
		}else if (getFace() == 11){
			face = "Jack";
		}else if (getFace() == 12){
			face = "Queen";
		}else if (getFace() == 13){
			face = "King";
		}
		
		if (getSuit() == 0){
			suit = "Spades";
		}else if (getSuit() == 1){
			suit = "Hearts";
		}else if (getSuit() == 2){
			suit = "Clubs";
		}else if (getSuit() == 3){
			suit = "Diamonds";
		}
		
		return face + " of " + suit;
	}
}