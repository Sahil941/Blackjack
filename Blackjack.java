// This is the actual game
public class Blackjack
{
	public static void main(String[] args)
	{
		boolean playAgain = false; // This is the boolean that will check if the players would like to play again or not
		int roundCounter = 0; // This is a counter for how many rounds the player have played
		int deckCounter = 0; // This is a counter for how many decks have been used in order to move onto the next deck
		
		
		System.out.println("Welcome to the game of BlackJack!");
		System.out.println("");
		
		
		// This will ask how many players will be playing BlackJack
		System.out.println("Enter how many players will be playing BlackJack.");
		int players = IO.readInt(); // Takes in the input the user enters
		
		
		/* Checks whether or not more than 6 or less than 1 player were going to play
		   because more than 6 and less than 1 player cannot play at the same time */
		while (players > 6 || players < 1){
			if (players > 6){
				System.out.println("More than 6 players cannot play BlackJack. Enter the number of players again.");
				players = IO.readInt(); // Takes in the input of the number of players playing
			}else if (players < 1){
				System.out.println("Less than 1 player cannot play BlackJack. Enter the number of players again.");
				players = IO.readInt(); // Takes in the input of the number of players playing
			}
		}
		System.out.println("");
		
		
		// This will actually create the new Players and the dealer's hand
		Player[] PlayerList = new Player[players];
		Card[] dealer = new Card[12];
		int dealerScore = 0;
		
		
		// This will initialize each Player object
		for (int i = 0; i < PlayerList.length; i++){
			PlayerList[i] = new Player();
		}
		
		
		// This is where all the decks are created, initialized, and shuffled
		Deck[] blackjackDeck = new Deck[6];
		for (int i = 0; i < blackjackDeck.length; i++){
			blackjackDeck[i] = new Deck();
			blackjackDeck[i].shuffle();
		}
		
		
		// This will ask each player for their names
		System.out.println("Each player must enter their name.");
		for (int i = 0; i < PlayerList.length; i++){
			System.out.println("Player " + (i + 1) + " enter your name.");
			PlayerList[i].setName(IO.readString()); // Sets the name in the Player object to the string input taken in
		}
		System.out.println("");
		
		
		// This will store the initial amount of $1000 each player will have to wager
		System.out.println("Each player will have $1000 to wager during the game.");
		for (int i = 0; i < PlayerList.length; i++){
			PlayerList[i].setMoney(1000); // Sets the money in the Player object to $1000
		}
		System.out.println("");
		
		
		/* This loop is for each round of BlackJack that will be played
		   depending on whether the players would like to play again or not */
		do{	
			/* This outputs what round of BlackJack the same players are playing after the
			   first round of BlackJack. This is also a check for when the players play more
			   than 1 round of BlackJack the cards in their hand are removed from the previous
			   round and their score is reset to 0. This is also the case for the dealer */
			if (roundCounter > 0){
				System.out.println("This is Round " + (roundCounter + 1) + " of BlackJack.");
				System.out.println("");
				
				for (int i = 0; i < PlayerList.length; i++){
					if (PlayerList[i] != null){ // This will skip any Player that has run out of money
						for (int j = 0; j < PlayerList[i].playerHand.length; j++){
							PlayerList[i].playerHand[j] = null; // This resets the card at the "j" point in the Player's hand to nothing
						}
						PlayerList[i].totalScore = 0; // This resets the Player's total score to 0
					}
				}
				
				for (int i = 0; i < dealer.length; i++){
					dealer[i] = null; // This resets the card at the "i' point in the Dealer's hand to nothing
				}
				dealerScore = 0; // This resets the Dealer's total score to 0
			}
			
			
			// This will ask each player how much they would like to wager
			double[] bet = new double[players]; // This will store the amount of money each Player will wager
			System.out.println("Each player must enter how much they would like to bet.");
			
			for (int i = 0; i < PlayerList.length; i++){
				if (PlayerList[i] != null){ // This will skip any Player that has run out of money
					System.out.println("Player " + PlayerList[i].getName() + " enter how much you would like to bet.");
					bet[i] = IO.readDouble(); // Takes in the input for the amount of money the Player would like to wager
					
					/* This is a check to see if the player has $0 after playing BlackJack and
					   notifying the Player that he/she has no more money to continue playing */
					if (PlayerList[i].getMoney() == 0){
						System.out.println("Player " + PlayerList[i].getName() + " you have no more money to play BlackJack.");
						System.out.println("Player " + PlayerList[i].getName() + " thank you for playing BlackJack!");
						System.out.println("");
						PlayerList[i] = null; // Sets the Player to nothing if he/she has no more money to play
					}else{
						/* This is a check to see whether the amount of money the player would
						   like to wager is greater than the amount he/she has or less than 0 */
						while (bet[i] > PlayerList[i].getMoney() || bet[i] <= 0){
							System.out.println("You must bet between 0 and " + PlayerList[i].getMoney());
							bet[i] = IO.readDouble(); // Takes in the input for the amount of money the Player would like to wager
						}
						
						PlayerList[i].money -= bet[i]; // This subtracts the amount of money the Player wages from the initial $1000
						System.out.println("");
					}
				}
			}
			
			
			/* This stores the amount of money the Player wagered as well
			   as be used for if the Player decides to split his/her hand */
			double[] splitHandBet = new double[players]; // This will store the amount of money a Player will bet on their "split" hand
			for (int i = 0; i < PlayerList.length; i++){
				splitHandBet[i] = bet[i]; // This sets the bet for the "split" hand to the amount of money of the initial bet
			}
			
			
			/* This is a check to see if all Players have $0 money or not.
			   If all do then the BlackJack Program will terminate itself. */
			if (roundCounter > 0){
				int terminator = 0;
				for (int i = 0; i < PlayerList.length; i++){
					if (PlayerList[i] == null){ // This increase the "terminator" by 1 for every Player that has ran out of money
						terminator++;
					}
				}
				if (terminator == players){ // This ends the game if all Players have no more money to play
					System.out.println("All Players have no more money to play BlackJack.");
					System.out.println("Thank you for playing BlackJack!");
					System.exit(0);
				}
			}
			
			
			/* This is where each player is dealt 2 random cards face-up
			   and then the dealer gets 1 card face-down and 1 card face-up */
			for (int i = 0; i < 2; i++){
				for (int j = 0; j < PlayerList.length; j++){
					if (PlayerList[j] != null){ // This will skip any Player that has run out of money
						if (blackjackDeck[deckCounter].isEmpty()){
							deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
							
							if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
								System.out.println("All cards in every deck have been used. There are no more");
								System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
								System.exit(0);
							}
						}
						PlayerList[j].addCard(blackjackDeck[deckCounter].deal()); // This adds a card dealt from the deck to the Player's hand
					}
				}
				dealer[i] = blackjackDeck[deckCounter].deal(); // This adds a card dealt from the deck to the Dealer's hand
			}
			
			
			// This will set whether each Player has gotten a true BlackJack or not
			for (int i = 0; i < PlayerList.length; i++){
				if (PlayerList[i] != null){ // This will skip any Player that has run out of money
					if (PlayerList[i].playerHand[0].getValue() == 10 && PlayerList[i].playerHand[1].getValue() == 1){
						PlayerList[i].totalScore = 21;
					}else if (PlayerList[i].playerHand[0].getValue() == 1 && PlayerList[i].playerHand[1].getValue() == 10){
						PlayerList[i].totalScore = 21;
					}
					if (PlayerList[i].getScore() == 21){
						PlayerList[i].BlackJack = true;
					}else{
						PlayerList[i].BlackJack = false;
					}
				}
			}
			
			
			// This is where each Advanced play boolean is created and initialized
			boolean[] didPlayerSplit = new boolean[players]; // This will store whether or not a Player did split
			boolean[] doubleDown = new boolean[players]; // This stores whether or not the Player will double down
			boolean[] splitHandDoubleDown = new boolean[players]; // This will store whether or not the Player double downed on their "split" hand
			
			for (int i = 0; i < players; i++){ // This loop initializes each boolean array to false
				didPlayerSplit[i] = false;
				doubleDown[i] = false;
				splitHandDoubleDown[i] = false;
			}
			
			
			/* This is where each player will be asked if they want to take
			   an insurance assuming the Dealer's face-up card is an Ace*/
			double[] insurance = new double[players]; // This is where the insurance money will be stored
			for (int i = 0; i < insurance.length; i++){ // This initializes the entire insurance array to $0
				insurance[i] = 0;
			}
			if (dealer[1].getValue() == 1){
				System.out.println("The Dealer's face-up card is an Ace. Each Player will have an opportunity to make an insurance wager.");
				for (int i = 0; i < PlayerList.length; i++){
					if (PlayerList[i] != null){ // This will skip any Player that has run out of money
						System.out.println("Player " + PlayerList[i].getName() + " would you like to make an insurance wager?");
						System.out.println("Enter \"Y\" for Yes, \"N\" for No, or \"hint\" for a hint.");
						String insuranceDecision = IO.readString(); // Takes in the Player's decision to make an insurance wager or not or take a hint
						
						// This checks whether or not the Player entered in "Y" or "N" or "hint" for Yes, No, and hint respectively
						while (!insuranceDecision.equals("Y") && !insuranceDecision.equals("N") && !insuranceDecision.equals("hint")){
							System.out.println("You did not enter \"Y\" or \"N\" or \"hint\". Please enter again.");
							insuranceDecision = IO.readString(); // Takes in the Player's decision to make an insurance wager or not or take a hint
						}
						System.out.println("");
						
						if (insuranceDecision.equals("hint")){
							insuranceDecision = insuranceHint();
						}
						System.out.println("");
						
						if (insuranceDecision.equals("Y")){ // This will set the amount of money the insurance should equal per Player
							System.out.println("You can insure up to 50 % of your original bet.");
							insurance[i] = IO.readDouble(); // Takes in the amount the Player will bet for insurance
							
							/* This is a check to see whether or not the Player entered an insurance
							   bet less than or equal to 0 or greater than his/her initial bet */
							while (insurance[i] < 0 || insurance[i] > (bet[i] * 0.5)){
								System.out.println("You must insurance bet between 0 and $" + (bet[i] * 0.5));
								insurance[i] = IO.readDouble(); // Takes in the amount the Player will bet for insurance
							}
							
							PlayerList[i].money -= insurance[i]; // This subtracts the insurance money from the Players total money
						}
						System.out.println("");
					}
				}
			}
			
			
			/* This is where each Player will be asked if they would like to
			   split their hand assuming they have the same two face cards */
			Card[][] splitHand = new Card[players][12]; // This will store the cards in "split" hand of a Player
			int[] splitHandScore = new int[players]; // This will store the score of the "split" hand of a Player
			
			for (int i = 0; i < PlayerList.length; i++){
				if (PlayerList[i] != null){ // This will skip any Player that has run out of money
					if (PlayerList[i].playerHand[0].getValue() == PlayerList[i].playerHand[1].getValue()){
						System.out.println("Player " + PlayerList[i].getName() + " you have two of the same cards in your hand.");
						System.out.println("Would you like to Split your hand? Enter \"Y\" for Yes, \"N\" for No, or \"hint\" for hint.");
						String splitDecision = IO.readString(); // Takes in the Player's decision to split his/her hand or to take a hint
						
						// This checks whether or not the Player entered in "Y" or "N" for Yes and No respectively
						while(!splitDecision.equals("Y") && !splitDecision.equals("N") && !splitDecision.equals("hint")){
							System.out.println("You did not enter \"Y\" or \"N\" or \"hint\". Please enter again.");
							splitDecision = IO.readString(); // Takes in the Player's decision to split his/her hand or to take a hint
						}
						System.out.println("");
						
						if (splitDecision.equals("hint")){
							splitDecision = splitHint(PlayerList[i].playerHand, dealer);
						}
						System.out.println("");
						
						if (splitDecision.equals("Y")){ // This splits the Player's original hand and deals face-up cards to both hands
							if (blackjackDeck[deckCounter].isEmpty()){
								deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
								
								if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
									System.out.println("All cards in every deck have been used. There are no more");
									System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
									System.exit(0);
								}
							}
							
							didPlayerSplit[i] = true; // This sets that the Player did split his/her hand
							splitHand[i][0] = PlayerList[i].playerHand[1];
							PlayerList[i].playerHand[1] = null;
							
							splitHand[i][1] = blackjackDeck[deckCounter].deal();
							PlayerList[i].addCard(blackjackDeck[deckCounter].deal());
						}
						else if (splitDecision.equals("N")){
							didPlayerSplit[i] = false; // This sets that the Player did not split his/her hand
						}
					}
				}
			}
			
			
			// This is where each player gets to decide if they want to hit, stand, double down, or hint
			System.out.println("Each player will now have the option to hit, stand, Double Down, or take a hint.");
			
			for (int i = 0; i < PlayerList.length; i++){
				if (PlayerList[i] != null){ // This will skip any Player that has run out of money
					System.out.println("Player " + PlayerList[i].getName() + " your hand is:");
					for (int j = 0; j < PlayerList[i].playerHand.length; j++){ // Prints out the current hand of the Player
						if (PlayerList[i].playerHand[j] != null){ // This skips any card that has no face and suit
							System.out.println(PlayerList[i].playerHand[j]); // Prints out the cards properties (i.e. Ace of Spades)
						}
					}
					
					System.out.println("");
					System.out.println("Your score is " + PlayerList[i].getScore() + ", would you like to Double Down?");
					System.out.println("Enter \"Y\" for Yes, \"N\" for No, or \"hint\" for hint.");
					String doubleDownDecision = IO.readString(); // Takes in the Player's decision to double down or not or take a hint
					
					// This will check whether or not they entered "Y" or "N" or "hint" for Yes, No, hint respectively
					while (!doubleDownDecision.equals("Y") && !doubleDownDecision.equals("N") && !doubleDownDecision.equals("hint")){
						System.out.println("You did not enter \"Y\" or \"N\" or \"hint\". Please enter again.");
						doubleDownDecision = IO.readString(); // Takes in the Player's decision to double down or not or take a hint
					}
					System.out.println("");
					
					if (doubleDownDecision.equals("hint")){
						doubleDownDecision = doubleDownHint(dealer, PlayerList[i].getScore());
					}
					System.out.println("");
					
					if (doubleDownDecision.equals("Y")){
						System.out.println("You can increase your initial bet by up to 100 %.");
						double doubleDownBet = IO.readDouble(); // Takes in the amount the player would like to Double Down
						
						/* This is a check to see whether or not the Player entered a Double Down
						   bet less than or equal to 0 or greater than his/her initial bet */
						while (doubleDownBet <= 0 || doubleDownBet > bet[i]){
							System.out.println("You must bet between 0 and $" + bet[i]);
							doubleDownBet = IO.readDouble(); // Takes in the amount the player would like to Double Down
						}
						
						PlayerList[i].money -= doubleDownBet; // This subtracts the double down bet from the Player's total money
						bet[i] += doubleDownBet; // This adds the double down bet to the initial bet
						doubleDown[i] = true; // This sets that the Player did double down
					}
					else if (doubleDownDecision.equals("N")){
						doubleDown[i] = false; // This sets that the Player did not double down
					}
					
					System.out.println("Would you like to hit, stand, or hint? Enter \"hit\" for hit, \"stand\" for stand, or \"hint\" for hint.");
					String decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
					
					// This checks to see whether or not the Player entered in either "hit" or "stand" or "hint"
					while (!decision.equals("hit") && !decision.equals("stand") && !decision.equals("hint")){
						System.out.println("You did not enter \"hit\" or \"stand\" or \"hint\". Please enter again.");
						decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
					}
					
					if (decision.equals("hint")){
						decision = hint(PlayerList[i].playerHand, dealer, PlayerList[i].getScore());
					}
					
					/* If the Player did double down and would like to take a hit
					   this will add only one card to his hand and no more */
					if (decision.equals("hit") && doubleDown[i] == true){
						if (blackjackDeck[deckCounter].isEmpty()){
							deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
							
							if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
								System.out.println("All cards in every deck have been used. There are no more");
								System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
								System.exit(0);
							}
						}
						PlayerList[i].addCard(blackjackDeck[deckCounter].deal());
					}
					else if (decision.equals("hit") && doubleDown[i] == false){
						do{
							if (blackjackDeck[deckCounter].isEmpty()){
								deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
								
								if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
									System.out.println("All cards in every deck have been used. There are no more");
									System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
									System.exit(0);
								}
							}
							
							PlayerList[i].addCard(blackjackDeck[deckCounter].deal());
							
							System.out.println("Player " + PlayerList[i].getName() + " now your hand is:");
							for (int j = 0; j < PlayerList[i].playerHand.length; j++){ // Prints out the current hand of the Player
								if (PlayerList[i].playerHand[j] != null){ // This skips any card that has no face and suit
									System.out.println(PlayerList[i].playerHand[j]); // Prints out the card's properties (i.e. Ace of Spades)
								}
							}
							
							System.out.println("");
							System.out.println("Your score is " + PlayerList[i].getScore() + ", would you like to hit, stand, or hint?");
							decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
							
							// This checks to see whether or not the Player entered in either "hit" or "stand" or "hint"
							while (!decision.equals("hit") && !decision.equals("stand") && !decision.equals("hint")){
								System.out.println("You did not enter \"hit\" or \"stand\" or \"hint\". Please enter again.");
								decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
							}
							System.out.println("");
							
							if (decision.equals("hint")){
								decision = hint(PlayerList[i].playerHand, dealer, PlayerList[i].getScore());
							}
						}while (decision.equals("hit"));
						System.out.println("");
					}
					else if (decision.equals("stand")){
						System.out.println("");
					}
					
					/* This checks to see whether or not a Player did split. If they
					   did split, this is where the 2nd hand will take it's turn */
					if (didPlayerSplit[i]){
						System.out.println("Player " + PlayerList[i].getName() + " your 2nd hand is:");
						for (int j = 0; j < splitHand[i].length; j++){ // Prints out all the cards in the Player's hand
							if (splitHand[i][j] != null){ // This skips any card that has no face and suit
								System.out.println(splitHand[i][j]); // Prints out the card's properties (i.e. Ace of Spades)
							}
						}
						
						for (int k = 0; k < splitHand[i].length; k++){ // This loop adds up the score of the cards in the "split" hand
							if (splitHand[i][k] != null){ // This skips any card that has no face and suit
								splitHandScore[i] += splitHand[i][k].getValue(); // This adds the value of the card at the "k" position
							}
						}
						
						System.out.println("");
						System.out.println("Your score is " + splitHandScore[i] + ", would you like to Double Down?");
						System.out.println("Enter \"Y\" for Yes, \"N\" for No, or \"hint\" for hint.");
						doubleDownDecision = IO.readString(); // Takes in the Player's decision to double down or not
						
						// This checks whether or not the Player entered "Y" or "N" or "hint" for Yes, No, or hint respectively
						while (!doubleDownDecision.equals("Y") && !doubleDownDecision.equals("N") && !doubleDownDecision.equals("hint")){
							System.out.println("You did not enter \"Y\" or \"N\" or \"hint\". Please enter again.");
							doubleDownDecision = IO.readString(); // Takes in the Player's decision to double down or not or take a hint
						}
						System.out.println("");
						
						if (doubleDownDecision.equals("hint")){
							doubleDownDecision = doubleDownHint(dealer, splitHandScore[i]);
						}
						System.out.println("");
						
						if (doubleDownDecision.equals("Y")){
							System.out.println("You can increase your initial bet by up to 100 %.");
							double doubleDownBet = IO.readDouble(); // Takes in the amount the player would like to double down
							
							/* This is a check to see whether or not the Player entered a
							   Double Down bet less than 0 or greater than his/her initial bet */
							while (doubleDownBet <= 0 || doubleDownBet > splitHandBet[i]){
								System.out.println("You must bet between 0 and $" + splitHandBet[i]);
								doubleDownBet = IO.readDouble(); // Takes in the amount the player would like to double down
							}
							
							PlayerList[i].money -= doubleDownBet;
							splitHandBet[i] += doubleDownBet;
							splitHandDoubleDown[i] = true; // This sets that the Player did double down on his/her "split" hand
						}
						else if (doubleDownDecision.equals("N")){
							splitHandDoubleDown[i] = false; // This sets that the Player did not double down on his/her "split" hand
						}
						
						System.out.println("Enter \"hit\" if you want to hit, \"stand\" if you want to stand, or \"hint\" if you want a hint.");
						decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
						
						// This checks to see whether or not the Player entered in either "hit" or "stand" or "hint"
						while (!decision.equals("hit") && !decision.equals("stand") && !decision.equals("hint")){
							System.out.println("You did not enter \"hit\" or \"stand\" or \"hint\". Please enter again.");
							decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
						}
						System.out.println("");
						
						if (decision.equals("hint")){
							decision = hint(splitHand[i], dealer, splitHandScore[i]);
						}
						System.out.println("");
						
						/* If the Player did double down and would like to take a hit
						   this will add only one card to his/her "split" hand and no more */
						if (decision.equals("hit") && splitHandDoubleDown[i] == true){
							for (int j = 0; j < splitHand[i].length; j++){ // This loop adds one card to the Player's "split" hand
								if (splitHand[i][j] == null){
									if (blackjackDeck[deckCounter].isEmpty()){
										deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
										
										if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
											System.out.println("All cards in every deck have been used. There are no more");
											System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
											System.exit(0);
										}
									}
									
									splitHand[i][j] = blackjackDeck[deckCounter].deal();
									splitHandScore[i] += splitHand[i][j].getValue();
									break;
								}
							}
						}else if (decision.equals("hit") && splitHandDoubleDown[i] == false){
							do{
								for (int j = 0; j < splitHand[i].length; j++){ // This loop adds one card to the Player's "split" hand
									if (splitHand[i][j] == null){
										if (blackjackDeck[deckCounter].isEmpty()){
											deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
											
											if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
												System.out.println("All cards in every deck have been used. There are no more");
												System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
												System.exit(0);
											}
										}
										
										splitHand[i][j] = blackjackDeck[deckCounter].deal();
										splitHandScore[i] += splitHand[i][j].getValue();
										break;
									}
								}
								
								System.out.println("Player " + PlayerList[i].getName() + " now your 2nd hand is:");
								for (int k = 0; k < splitHand[i].length; k++){ // Prints out the current hand of the Player
									if (splitHand[i][k] != null){ // This skips any card that has no face and suit
										System.out.println(splitHand[i][k]); // Prints out the card's properties (i.e. Ace of Spades)
									}
								}
								
								System.out.println("");
								System.out.println("Your score is " + splitHandScore[i] + ", would you like to hit or stand or hint?");
								decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
								
								// This checks to see whether or not the Player entered in either "hit" or "stand" or "hint"
								while (!decision.equals("hit") && !decision.equals("stand") && !decision.equals("hint")){
									System.out.println("You did not enter \"hit\" or \"stand\" or \"hint\". Please enter again.");
									decision = IO.readString(); // Takes in the Player's decision to "hit" or "stand" or "hint"
								}
								System.out.println("");
								
								if (decision.equals("hint")){
									decision = hint(splitHand[i], dealer, splitHandScore[i]);
								}
							}while (decision.equals("hit"));
							System.out.println("");
						}
						else if (decision.equals("stand")){
							System.out.println("");
						}
					}
				}
			}
			
			
			/* This will check if the Dealer has a true BlackJack or not
			   and deal out the insurance that each player made previously */
			if (dealer[1].getValue() == 1){ // This checks to see if the Dealer's face-up card is an Ace
				if (dealer[0].getValue() == 10){ // This checks to see if the Dealer's face-down card is a 10-point card
					dealerScore = 21; // This sets the Dealer's score to 21, indicating a natural BlackJack
					
					for (int i = 0; i < PlayerList.length; i++){
						if (PlayerList[i] != null){ // This will skip any Player that has run out of money
							if (insurance[i] > 0){
								System.out.println("Player " + PlayerList[i].getName() + " you won the insurance bet!");
								PlayerList[i].money += (insurance[i] * 2); // Adds double the insurance waged to the Player's total money
							}
						}
					}
				}
			}
			
			
			// This is where the Dealer will take his/her turn
			if (dealer[1].getValue() == 10 || dealer[1].getValue() == 1){ // Checks if the Dealer's face-up card is an Ace or 10-point card
				if (dealer[0].getValue() == 1 || dealer[0].getValue() == 10){ // Checks if the Dealer's face-down card is an Ace or 10-point card
					dealerScore = 21; // This sets the Dealer's score to 21, indicating a natural BlackJack
					
					System.out.println("The Dealer's hand is:");
					for (int i = 0; i < dealer.length; i++){ // Prints out the cards in the Dealer's hand
						if (dealer[i] != null){ // This skips any card that has no face and suit
							System.out.println(dealer[i]); // Prints out the card's properties (i.e. Ace of Spades)
						}
					}
					
					System.out.println("");
					System.out.println("The Dealer's score is " + dealerScore + ".");
					System.out.println("");
					
					for (int i = 0; i < PlayerList.length; i++){
						if (PlayerList[i] != null){ // This will skip any Player that has run out of money
							if (didPlayerSplit[i]){ // This checks to see if the Player did split or not
								// Because the Player did split, his first and "split" hand can never beat the Dealer's natural BlackJack
								System.out.println("The Dealer has gotten BlackJack against Player " + PlayerList[i].getName() + "'s 1st and \"split\" hand. Dealer wins!");
							}
							else{ // This is what happens if the Player did not split
								if (PlayerList[i].getScore() == 21 && PlayerList[i].BlackJack == true){ // Checks to see if the Player got a natural BlackJack
									System.out.println("Player " + PlayerList[i].getName() + " ties with the Dealer.");
									PlayerList[i].money += bet[i]; // Adds the amount bet to the Player's total money
								}
								else{ // Because the Player did not get a natural BlackJack, he/she loses against the Dealer
									System.out.println("The Dealer has gotten BlackJack against Player " + PlayerList[i].getName() + ". Dealer wins!");
								}
							}
							System.out.println("");
						}
					}
				}
			}
			else{ // This is what happens if the Dealer's face-up card is not an Ace or 10-point card
				dealerScore += dealer[1].getValue() + dealer[0].getValue(); // Adds the value of both the first and second card in the Dealer's hand
				
				for (int i = 2; dealerScore < 17; i++){ // This loop adds one card face-up to the Dealer's hand till the Dealer's score is greater than or equal to 17
					if (blackjackDeck[deckCounter].isEmpty()){
						deckCounter++; // This increases the counter by 1 causing the next deck to be used for dealing cards
						
						if (deckCounter > blackjackDeck.length){ // This checks to see if every deck has been used. Ends the game if true
							System.out.println("All cards in every deck have been used. There are no more");
							System.out.println("cards to play BlackJack with. Thank you for playing BlackJack!");
							System.exit(0);
						}
					}
					
					dealer[i] = blackjackDeck[deckCounter].deal(); // Adds a random card from the deck to the Dealer's hand
					dealerScore += dealer[i].getValue(); // Adds the score of the card drawn to the Dealer's total score
				}
				
				System.out.println("The Dealer's hand is:");
				for (int i = 0; i < dealer.length; i++){ // This loop prints out the cards in the Dealer's hand
					if (dealer[i] != null){ // This skips any card that has no face and suit
						System.out.println(dealer[i]); // Prints out the cards properties (i.e. Ace of Spades)
					}
				}
				
				System.out.println("");
				System.out.println("The Dealer's score is " + dealerScore + ".");
				System.out.println("");
				
				if (dealerScore == 21){ // Checks to see if the Dealer's score is 21
					for (int i = 0; i < PlayerList.length; i++){
						if (PlayerList[i] != null){ // This will skip any Player that has run out of money
							if (didPlayerSplit[i]){ // Checks to see if the Player did split or not
								// This will compare the Dealer's hand to the Player's first hand
								if (PlayerList[i].getScore() == 21){
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand has tied with the dealer.");
									PlayerList[i].money += bet[i]; // Adds the amount bet to the Player's total money
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand has lost against the Dealer.");
								}
								System.out.println("");
								
								// This will compare the Dealer's hand to the Player's "split" hand
								if (splitHandScore[i] == 21){
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand has tied with the dealer.");
									PlayerList[i].money += splitHandBet[i]; // Adds the amount bet on the "split" hand to the Player's total money
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand has lost against the Dealer.");
								}
								System.out.println("");
							}
							else{ // This is what happens if the Player did not split
								if (PlayerList[i].getScore() == 21 && PlayerList[i].BlackJack == true){
									System.out.println("Player " + PlayerList[i].getName() + " has gotten BlackJack and wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2.5); // Adds two and a half times the amount bet to the Player's total money
								}
								else if (PlayerList[i].getScore() == 21 && PlayerList[i].BlackJack == false){
									System.out.println("Player " + PlayerList[i].getName() + " has tied with the dealer.");
									PlayerList[i].money += bet[i]; // Adds the amount bet to the Player's total money
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + " has lost against the Dealer.");
								}
								System.out.println("");
							}
						}
					}
				}else if (dealerScore > 21){ // Checks to see if the Dealer has bust or not
					for (int i = 0; i < PlayerList.length; i++){
						if (PlayerList[i] != null){ // This will skip any Player that has run out of money
							if (didPlayerSplit[i]){ // Checks to see if the Player did split or not
								// This will compare the Dealer's hand to the Player's first hand
								if (PlayerList[i].getScore() > 21){
									System.out.println("Both the dealer and Player " + PlayerList[i].getName() + "'s 1st hand have busted.");
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2); // Adds twice the amount bet to the Player's total money
								}
								System.out.println("");
								
								// This will compare the Dealer's hand to the Player's "split" hand
								if (splitHandScore[i] > 21){
									System.out.println("Both the dealer and Player " + PlayerList[i].getName() + "'s \"split\" hand have busted.");
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand wins against the Dealer!");
									PlayerList[i].money += (splitHandBet[i] * 2); // Adds twice the amount bet on the "split" hand to the Player's total money
								}
								System.out.println("");
							}
							else{ // This is what happens if the Player did not split
								if (PlayerList[i].getScore() > 21){
									System.out.println("Both the dealer and Player " + PlayerList[i].getName() + " have busted.");
								}
								else if (PlayerList[i].getScore() == 21 && PlayerList[i].BlackJack == true){
									System.out.println("Player " + PlayerList[i].getName() + " has gotten a BlackJack and wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2.5); // Adds two and a half times the amount bet to the Player's total money
								}
								else if (PlayerList[i].getScore() <= 21 && PlayerList[i].BlackJack == false){
									System.out.println("Player " + PlayerList[i].getName() + " wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2); // Adds twice the amount bet to the Player's total money
								}
								System.out.println("");
							}
						}
					}
				}else{ // This is what happens if the Dealer's total score is less than 21 but greater than or equal to 17
					for (int i = 0; i < PlayerList.length; i++){
						if (PlayerList[i] != null){ // This will skip any Player that has run out of money
							if (didPlayerSplit[i]){ // Checks to see if the Player did split or not
								// This will compare the Dealer's hand to the Player's first hand
								if (PlayerList[i].getScore() == 21){
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2); // Adds twice the amount bet to the Player's total money
								}
								else if (PlayerList[i].getScore() > 21){
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand has bust. Dealer wins!");
								}
								else if (dealerScore > PlayerList[i].getScore()){
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand has lost against the Dealer.");
								}
								else if (dealerScore == PlayerList[i].getScore()){
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand has tied with the Dealer.");
									PlayerList[i].money += bet[i]; // Adds the amount bet to the Player's total money
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + "'s 1st hand wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2); // Adds twice the amount bet to the Player's total money
								}
								System.out.println("");
								
								// This will compare the Dealer's hand to the Player's "split" hand
								if (splitHandScore[i] == 21){
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand wins against the Dealer!");
									PlayerList[i].money += (splitHandBet[i] * 2); // Adds twice the amount bet on the "split" hand to the Player's total money
								}
								else if (splitHandScore[i] > 21){
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand has bust. Dealer wins!");
								}
								else if (dealerScore > splitHandScore[i]){
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand has lost against the Dealer.");
								}
								else if (dealerScore == splitHandScore[i]){
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand has tied with the Dealer.");
									PlayerList[i].money += splitHandBet[i]; // Adds the amount bet on the "split" hand to the Player's total money
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + "'s \"split\" hand wins against the Dealer!");
									PlayerList[i].money += (splitHandBet[i] * 2); // Adds twice the amount bet on the "split" hand to the Player's total money
								}
								System.out.println("");
							}
							else{ // This is what happens if the Player did not split
								if (PlayerList[i].getScore() == 21 && PlayerList[i].BlackJack == true){
									System.out.println("Player " + PlayerList[i].getName() + " has gotten BlackJack and wins against the Dealer!");
									PlayerList[i].money = (bet[i] * 2.5); // Adds two and a half times the amount bet to the Player's total money
								}
								else if (PlayerList[i].getScore() == 21 & PlayerList[i].BlackJack == false){
									System.out.println("Player " + PlayerList[i].getName() + " wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2); // Adds twice the amount bet to the Player's total money
								}
								else if (PlayerList[i].getScore() > 21){
									System.out.println("Player " + PlayerList[i].getName() + " has bust. Dealer wins!");
								}
								else if (dealerScore > PlayerList[i].getScore()){
									System.out.println("Player " + PlayerList[i].getName() + " has lost against the Dealer.");
								}
								else if (dealerScore == PlayerList[i].getScore()){
									System.out.println("Player " + PlayerList[i].getName() + " has tied with the Dealer.");
									PlayerList[i].money += bet[i]; // Adds the amount bet to the Player's total money
								}
								else{
									System.out.println("Player " + PlayerList[i].getName() + " wins against the Dealer!");
									PlayerList[i].money += (bet[i] * 2); // Adds twice the amount bet to the Player's total money
								}
								System.out.println("");
							}
						}
					}
				}
			}
			
			
			/* This will output the total amount of money each
			   player has after playing one game of BlackJack */
			for (int i = 0; i < PlayerList.length; i++){
				if (PlayerList[i] != null){ // This will skip any Player that has run out of money
					System.out.println("Player " + PlayerList[i].getName() + " has $" + PlayerList[i].getMoney() + " after playing BlackJack.");
				}
			}
			System.out.println("");
			
			
			// This will ask if the same players would like to play again or not
			System.out.println("Would you like to play BlackJack again?");
			System.out.println("Enter \"Y\" for Yes and \"N\" for No.");
			String playAgainDecision = IO.readString(); // Takes in the Players decision to play BlackJack again or not
			
			
			// This check whether they entered in "Y" or "N" for Yes and No respectively
			while (!playAgainDecision.equals("Y") && !playAgainDecision.equals("N")){
				System.out.println("You did not enter \"Y\" or \"N\". Please enter again.");
				playAgainDecision = IO.readString(); // Takes in the Players decision to play BlackJack again or not
			}
			
			if (playAgainDecision.equals("Y")){
				playAgain = true; // Sets the fact that the Players will play BlackJack again
				roundCounter++; // Increases the counter by 1 if another round of BlackJack will be played
				System.out.println("");
			}
			else if (playAgainDecision.equals("N")){
				playAgain = false; // Sets the fact that the Players will not play BlackJack again
			}
		}while (playAgain); // The main game loop will run once and then start over only if the Players choose to play again
	}
	
	
	/* This is the method that will deal out the hint based on the
	   situation of the Player asking for it and the other Players/Dealer */
	public static String hint(Card[] handOfPlayer, boolean split, boolean doubleDown, boolean splitDoubleDown){
		String decision = "void";
		return decision;
	}
	
	
	/* This is the method that will advice the Player what to do if he/she
	   asks for a hint when they are offered the chance of Insurance */
	public static String insuranceHint(){
		System.out.println("There is about a 30.7 % chance that the Dealer will have a natural BlackJack.");
		System.out.println("It is advisiable to not make an Insurance Bet.");
		System.out.println("Would you still like to make an Insurance Bet? Enter \"Y\" for Yes and \"N\" for No.");
		String insuranceDecision = IO.readString(); // Takes in the Player's decision to make an insurance bet or not
		
		// This checks whether or not the Player entered in "Y" or "N" for Yes and No respectively
		while (!insuranceDecision.equals("Y") && !insuranceDecision.equals("N")){
			System.out.println("You did not enter \"Y\" or \"N\". Please enter again.");
			insuranceDecision = IO.readString(); // Takes in the Player's decision to make an insurance bet or not
		}
		
		return insuranceDecision;
	}
	
	
	/* This is the method that will advice the Player what to do if he/she
	   asks for a hint when they are offered the chance to Split his/her hand */
	public static String splitHint(Card[] handOfPlayer, Card[] dealer){
		if (handOfPlayer[0].getValue() == 1){
			System.out.println("It is advisable to Split your pair of Aces.");
		}
		else if (handOfPlayer[0].getValue() == 10){
			System.out.println("It is advisable to stand and not Split your hand.");
		}
		else if (handOfPlayer[0].getValue() == 9){
			if (dealer[1].getValue() == 7 || dealer[1].getValue() == 10 || dealer[1].getValue() == 1){
				System.out.println("It is advisable to stand and not Split you pair of Nines.");
			}
			else{
				System.out.println("It is advisable to Split your pair of Nines");
			}
		}
		else if (handOfPlayer[0].getValue() == 8){
			System.out.println("It is advisable to Split your hand of Eights.");
		}
		else if (handOfPlayer[0].getValue() == 7){
			if (dealer[1].getValue() <= 7 && dealer[1].getValue() > 1){
				System.out.println("It is advisable to Split your hand of Sevens.");
			}
			else{
				System.out.println("It is advisable to hit and not Split your hand of Sevens.");
			}
		}
		else if (handOfPlayer[0].getValue() == 6){
			if (dealer[1].getValue() <= 6){
				System.out.println("It is advisable to Split your hand of Sixs.");
			}
			else{
				System.out.println("It is advisable to hit and not Split your hand of Sixs.");
			}
		}
		else if (handOfPlayer[0].getValue() == 5){
			if (dealer[1].getValue() <= 9){
				System.out.println("It is advisable to Double Down on your pair of Fives. If you do not want to Double Down, it is better to hit.");
			}
			else{
				System.out.println("It is advisable to hit and not Split your hand of Fives.");
			}
		}
		else if (handOfPlayer[0].getValue() == 4){
			if (dealer[1].getValue() <= 4 || dealer[1].getValue() >= 7){
				System.out.println("It is advisable to hit and not Split your hand of Fours.");
			}
			else{
				System.out.println("It is advisable to hit and not Split your hand of Sevens.");
			}
		}
		else if (handOfPlayer[0].getValue() == 3){
			if (dealer[1].getValue() <= 7){
				System.out.println("It is advisable to Split your hand of Threes.");
			}
			else{
				System.out.println("It is advisable to hit and not Split your hand of Threes.");
			}
		}
		else if (handOfPlayer[0].getValue() == 2){
			if (dealer[1].getValue() <= 7){
				System.out.println("It is advisable to Split your hand of Twos.");
			}
			else{
				System.out.println("It is advisable to hit and not Split your hand of Twos.");
			}
		}
		System.out.println("");
		
		System.out.println("Would you like to Split your hand? Enter \"Y\" for Yes and \"N\" for No.");
		String splitDecision = IO.readString(); // Takes in the Player's decision to split his/her hand
		
		// This checks whether or not the Player entered in "Y" or "N" for Yes and No respectively
		while (!splitDecision.equals("Y") && !splitDecision.equals("N")){
			System.out.println("You did not enter \"Y\" or \"N\". Please enter again.");
			splitDecision = IO.readString(); // Takes in the Player's decision to split his/her hand
		}
		
		return splitDecision;
	}
	
	
	/* This is the method that will advice the Player what to do if he/she asks
	   for a hint when they are offered the chance to Double Down on his/her hand */
	public static String doubleDownHint(Card[]dealer, int totalScore){
		if (totalScore == 11){
			System.out.println("It is advisable to Double Down on your hand. If you do not want to Double Down, it is better to hit.");
		}
		else if (totalScore == 10){
			if (dealer[1].getValue() == 10 || dealer[1].getValue() == 1){
				System.out.println("It is advisable to hit and not Double Down on your hand.");
			}
			else{
				System.out.println("It is advsiable to Double Down on your hand. If you do not want to Double Down, it is better to hit.");
			}
		}
		else if (totalScore == 9){
			if (dealer[1].getValue() > 2 && dealer[1].getValue() < 7){
				System.out.println("It is advisable to Double Down on your hand. If you do not want to Double Down, it is better to hit.");
			}
			else{
				System.out.println("It is advisable to hit and not Double Down on your hand.");
			}
		}
		else{
			System.out.println("It is advisable not to Double Down on your hand.");
		}
		
		System.out.println("Would you like to Double Down? Enter \"Y\" for Yes and \"N\" for No.");
		String doubleDownDecision = IO.readString(); // Takes in the Player's decision to double down or not
		
		// This checks whether or not the Player entered in "Y" or "N" for Yes and No respectively
		while (!doubleDownDecision.equals("Y") && !doubleDownDecision.equals("N")){
			System.out.println("You did not enter \"Y\" or \"N\". Please enter again.");
			doubleDownDecision = IO.readString(); // Takes in the Player's decision to double down or not
		}
		
		return doubleDownDecision;
	}
	
	
	/* This is the method that will advice the Player what to do if he/she
	   asks for a hint when they are offered the chance to "hit" or "stand" */
	public static String hint(Card[] handOfPlayer, Card[] dealer, int totalScore){
		if (totalScore > 3 && totalScore < 9){
			System.out.println("There is a 0 % chance that you will bust if you hit.");
			System.out.println("It is advisable to hit.");
		}
		else if (totalScore == 9){
			System.out.println("There is a 0 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() < 3 || dealer[1].getValue() > 6){
				System.out.println("It is advisable to hit.");
			}
			else{
				System.out.println("It is advisable to Double Down on your hand. Otherwise, you should hit.");
			}
		}
		else if (totalScore == 10){
			System.out.println("There is a 0 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() == 10 || dealer[1].getValue() == 1){
				System.out.println("It is advisable to stand.");
			}
			else{
				System.out.println("It is advisable to Double Down on your hand. Otherwise, you should hit.");
			}
		}
		else if (totalScore == 11){
			System.out.println("There is a 0 % chance that you will bust if you hit.");
			System.out.println("It is advisable to Double Down on your hand. Otherwise, you should hit.");
		}
		else if (totalScore == 12){
			System.out.println("There is about a 31 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() < 4 || dealer[1].getValue() > 6){
				System.out.println("It is advisable to hit.");
			}
			else{
				System.out.println("It is advisable to stand.");
			}
		}
		else if (totalScore == 13){
			System.out.println("There is about a 38 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() > 6){
				System.out.println("It is advisable to hit.");
			}
			else{
				System.out.println("It is advisable to stand.");
			}
		}
		else if (totalScore == 14){
			System.out.println("There is about a 46 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() > 6){
				System.out.println("It is advisable to hit.");
			}
			else{
				System.out.println("It is advisable to stand.");
			}
		}
		else if (totalScore == 15){
			System.out.println("There is about a 54 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() > 6 && dealer[1].getValue() < 10){
				System.out.println("It is advisable to hit.");
			}
			else if (dealer[1].getValue() > 1 && dealer[1].getValue() < 7){
				System.out.println("It is advisable to stand.");
			}
			else{
				System.out.println("It is advisable to hit.");
			}
		}
		else if (totalScore == 16){
			System.out.println("There is about a 62 % chance that you will bust if you hit.");
			
			if (dealer[1].getValue() > 6 && dealer[1].getValue() < 9){
				System.out.println("It is advisable to hit.");
			}
			else if (dealer[1].getValue() > 1 && dealer[1].getValue() < 7){
				System.out.println("It is advisable to stand.");
			}
			else{
				System.out.println("It is advisable to hit.");
			}
		}
		else if (totalScore == 17){
			System.out.println("There is about a 69 % chance that you will bust if you hit.");
			System.out.println("It is advisable to stand.");
		}
		else if (totalScore == 18){
			System.out.println("There is about a 77 % chance that you will bust if you hit.");
			System.out.println("It is advisable to stand.");
		}
		else if (totalScore == 19){
			System.out.println("There is about a 85 % chance that you will bust if you hit.");
			System.out.println("It is advisable to stand.");
		}
		else if (totalScore == 20){
			System.out.println("There is about a 92 % chance that you will bust if you hit.");
			System.out.println("It is advisable to stand.");
		}
		else if (totalScore == 21){
			System.out.println("It is advisable to stand.");
		}
		
		System.out.println("Would you like to hit or stand? Enter \"hit\" for hit and \"stand\" for stand.");
		String decision = IO.readString(); // Takes in the Player's decision to hit or stand
		
		// This checks whether or no the Player entered in "hit" or "stand"
		while (!decision.equals("hit") && !decision.equals("stand")){
			System.out.println("You did not enter \"hit\" or \"stand\". Please enter again.");
			decision = IO.readString(); // Takes in the Player's decision to hit or stand
		}
		
		return decision;
	}
}