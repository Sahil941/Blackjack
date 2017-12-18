// This class represents one Player
public class Player
{
	// Define fields
	public double money;
	public int totalScore;
	public String name;
	public Card[] playerHand;
	public boolean BlackJack;
	
	/* The Player constructor will store the cards in a player's hand
	   and the amount of money they will be able to use to wager */
	public Player(){
		this.name = "void";
		this.money = 0;
		this.playerHand = new Card[12];
		this.totalScore = 0;
		this.BlackJack = false;
	}
	
	// This method adds a card to a player's hand as well as add the score of the card
	public void addCard(Card card){
		for (int i = 0; i < playerHand.length; i++){
			if (playerHand[i] == null){
				playerHand[i] = card;
				totalScore += playerHand[i].getValue();
				break;
			}
		}
	}
	
	// This method will set the name of each Player
	public void setName(String name){
		this.name = name;
	}
	
	// This method will set the money each Player has to wager
	public void setMoney(double money){
		this.money = money;
	}
	
	// This method will give the total score of a player's hand
	public int getScore(){
		return this.totalScore;
	}
	
	// This method will give the name of the Player
	public String getName(){
		return this.name;
	}
	
	// This method will give the amount of money the Player has
	public double getMoney(){
		return this.money;
	}
}