package style;

public class CardImpl implements Card {
	
	// String array that contains rank values
	private static String[] rankArray = {"Two", "Three", "Four", "Five", "Six", "Seven",
		 "Eight","Nine", "Ten", "Jack", "Queen", "King", "Ace"};
	
	// Instance fields
	private int rank;
	private Card.Suit suit;

	/*
	 * Constructor method used to initialize instance fields using
	 * given parameters
	 * input: int rank - integer representing rank between 2-14
	 * 		  Card.Suit suit - enum type of card object
	 * Throws exception if rank is not between 2-14
	 */
	public CardImpl(int rank, Card.Suit suit) {
		if(rank < 2 || rank > 14) {
			throw new IllegalArgumentException("rank must be between 2 and 14");
		}
		
		this.rank = rank;
		this.suit = suit;
	}
	
	// Returns rank of card
	public int getRank() {
		return rank;
	}

	// Returns suit of card
	public Suit getSuit() {
		return suit;
	}
	
	/*
	 * Method Comparing two cards to each other and seeing if they are the same
	 * Input: non null Card object otherCard
	 * Output: true if equal, false if not
	 */
	public boolean equals(Card otherCard) {
		return (rank == otherCard.getRank()) && (suit == otherCard.getSuit());
	}

	// Method creates a string that has rank and suit of instance card
	// Output Ex. Five of Spades
	public String toString() {
		return rankArray[getRank() - 2] + " of " + suitToString(getSuit());
	}

	
	/*
	 * Method uses switch cases to return string equivalent of card suit
	 * Input: Card.Suit type - element of suit enum
	 * Output: String value of suit type
	 */
	public String suitToString(Card.Suit type) {
		switch (type) {
			case SPADES :
				return "Spades";
			case HEARTS:
				return "Hearts";
			case DIAMONDS:
				return "Diamonds";
			case CLUBS:
				return "Clubs";}
		
		return null;
		}
	
	

}
