package style;

/*
 * Card
 * Represents information about a standard card within a deck.
 * 
 * getRank()
 *     Returns the rank that is associated with a card.
 *     
 * getSuit()
 *     Returns the suit that is associated with a card.
 *   
 * toString()
 *     Returns a string with the rank and suit of a card.
 *     Ex. Five of Spades
 * 
 * equals()
 *     Returns true if both the rank and the suit of the input card are 
 *     the same as the instance field values
 *     
 * suitToString(Card.Suit s)
 *     Returns the cards suit as a String using the switch method
 *     using the possible suits from the enumeration.
 */

public interface Card {
	public enum Suit {
		SPADES, HEARTS, DIAMONDS, CLUBS
		};
	
	int getRank();
	
	Card.Suit getSuit();

	boolean equals(Card other);
	
	String toString();
	
	
	String suitToString(Card.Suit type);
}