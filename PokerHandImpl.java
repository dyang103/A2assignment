package style;

public class PokerHandImpl implements PokerHand {

	//Instance fields
	private Card[] hand;

	/* 
	 * Constructor method takes parameter and initializes instance field hand
	 * and sorts array from least to greatest by rank
	 * Input: Card[] cards - Array of Card objects
	 * Throws RunTimeException if either the input array is null
	 * the array length is not equal to 5
	 * or any of the cards in the array are null
	 */
	public PokerHandImpl(Card[] cards) {
		if (cards == null) {
			throw new RuntimeException("card array is null");
		}
		if (cards.length != 5) {
			throw new RuntimeException("card array length is not 5");
		}
		for (int i=0; i<5; i++) {
			if (cards[i] == null) {
				throw new RuntimeException("one of the cards in the array is null");
			}
		}
		
		hand = cards.clone();
		
		for (int i=0; i<5; i++) {			
			for (int j=i; j<5; j++) {
				if (hand[j].getRank() < hand[i].getRank()) {
					Card tmp = hand[i];
					hand[i] = hand[j];
					hand[j] = tmp;
				}
			}
		}
	}

	// Output: returns a clone of poker array hand
	public Card[] getCards() {
		return hand.clone();
	}

	/*
	 * Method returns whether hand contains a specific card
	 * Input: Card object testCard
	 * Output: returns true if hand contains testCard, false otherwise
	 */
	public boolean contains(Card testCard) {
		for (int i=0; i<5; i++) {
			if (hand[i].equals(testCard)) {
				return true;
			}
		}
		return false;
	}

	
	/*
	 * Method compares the suites of the cards in hand
	 * Output: returns true if all of the cards in the hand have the same suit
	 * 		   returns false otherwise
	 */
	public boolean isFlush() {
		for (int i=1; i<5; i++) {
			if (hand[i].getSuit() != hand[0].getSuit()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Method compares ranks of cards in hand and tells if consecutive
	 * Output: returns true if cards in hand form 5 consecutive ranks
	 *         returns false if not
	 */
	public boolean isStraight() {
		boolean straight = true;
		
		for (int i=0; i<4; i++) {
			if (hand[i].getRank()+1 != hand[i+1].getRank()) {
				straight = false;
				break;
			}
		}
		return straight || isAceStraight();
	}
	
	/*
	 * Helper method for special case of isStraight()
	 * Output: returns true if hand contains 5 consecutive ranks including an ace
	 *         returns false if not
	 */
	private boolean isAceStraight() {
		if(hand[4].getRank() != 14)
			return false;
		
		for(int i = 0; i < 4; i++) {
			if(hand[i].getRank() != i+2)
				return false;
		}
		return true;
	}

	/*
	 * Method checks hand for exactly one pair using numPairsFrom()
	 * Output: returns true if hand contains exactly one pair
	 *         returns false if no pairs or more than one
	 */
	public boolean isOnePair() {
		int numPairs = numPairsFrom(0);

		boolean otherPairs = numPairsFrom(numPairs+1) == -1;
		
		return ((numPairs != -1) && otherPairs);
		
	}

	/*
	 * Method checks hand for exactly two pairs using numPairsFrom()
	 * Output: returns true if hand contains exactly two pairs
	 *         returns false if not exactly two pairs
	 */
	public boolean isTwoPair() {
		
		int firstPairIdx = numPairsFrom(0);
		int secondPairIdx = numPairsFrom(firstPairIdx+2);

		return ((firstPairIdx != -1) && (secondPairIdx != -1) && 
				!isFourOfAKind() && !isFullHouse());
	}

	/*
	 * Methods checks hand for exactly three of a kind using numPairsFrom()
	 * Output: returns true if hand contains exactly three of a kind
	 *         returns false if hand does not contain exactly three of a kind
	 */
	public boolean isThreeOfAKind() {
		int firstPairIdx = numPairsFrom(0);
		
		if ((firstPairIdx == -1) || (firstPairIdx == 3)) {
			return false;
		}
		
		return ((hand[firstPairIdx].getRank() == hand[firstPairIdx+2].getRank()) &&
				!isFourOfAKind() && !isFullHouse());
	}

	/*
	 * Method checks for exactly three identical card ranks and a separate pair
	 * Output: returns true if either the first two cards in the hand are a pair and the rest are identical
	 *         or if the first three card in the hand are identical and the last two are a pair
	 *         returns false otherwise
	 */
	public boolean isFullHouse() {
		return (((hand[0].getRank() == hand[1].getRank()) &&
				 (hand[2].getRank() == hand[3].getRank()) &&
				 (hand[3].getRank() == hand[4].getRank())) ||
				((hand[0].getRank() == hand[1].getRank()) &&
				 (hand[1].getRank() == hand[2].getRank()) &&
				 (hand[3].getRank() == hand[4].getRank())));		
	}

	
	/*
	 * Method checks hand for exactly four of a kind
	 * Output: returns true if either the first four or last four cards in hand have identical ranks
	 *         returns false otherwise
	 */
	public boolean isFourOfAKind() {
		return (((hand[0].getRank() == hand[1].getRank()) &&
				 (hand[1].getRank() == hand[2].getRank()) &&
				 (hand[2].getRank() == hand[3].getRank()))||
				((hand[1].getRank() == hand[2].getRank()) &&
				 (hand[2].getRank() == hand[3].getRank()) &&
				 (hand[3].getRank() == hand[4].getRank())));				
	}	
	
	/*
	 * Method checks if hand is both a straight and a flush
	 * Output: returns true if both isStraight() and isFlush() are true
	 *         returns false otherwise
	 */
	public boolean isStraightFlush() {
		if (isStraight() == true) {
			if (isFlush() == true) {
				return true;
			}
		}
		return false;
	}

	/*
	 * Output: returns value of existing hand type or highest ranking card
	 */
	public int getHandRank() {
		if (isOnePair() == true) {
			return hand[numPairsFrom(0)].getRank();
		} else if (isTwoPair() == true) {
			return hand[3].getRank();			
		} else if (isThreeOfAKind() || isFourOfAKind() || isFullHouse()) {	
			return hand[2].getRank();		
		} else if(isAceStraight()){	
			return 5;	
		}else {		
			return hand[4].getRank();
		}
	}

	/*
	 * Method compares hand value between two hands using getHandRank() and getHandValueType()
	 * Input: PokerHand object otherHand
	 * Output: returns -1, 0, or 1 depending on which hand has a higher value
	 */
	public int compareTo(PokerHand otherHand) {
		if (getHandTypeValue() < otherHand.getHandTypeValue()) {
			return -1;
		} else if (getHandTypeValue() > otherHand.getHandTypeValue()) {
			return 1;
		} else {
			return Integer.compare(getHandRank(), otherHand.getHandRank());
		}
	}
		
	
	/*
	 * Helper method that tells the values of existing hand types
	 * Output: integer between 1 and 9
	 */
	public int getHandTypeValue() {
		
		if (isStraightFlush()) 
			return 9;
		
		if (isOnePair()) 
			return 2;
		
		if (isTwoPair()) 
			return 3;
		
		if (isThreeOfAKind())
			return 4;
		
		if (isStraight()) 
			return 5;
		
		if (isFlush())
			return 6;
		
		if (isFullHouse())
			return 7;
		
		if (isFourOfAKind()) 
			return 8;
		
	return 1;
	}
	
	/*
	 * Helper method used to find existing pairs in a hand from a given starting index
	 * by comparing each card to the next as they are ordered
	 * Input: int index - tells which index in hand Array to start searching from
	 * Output: returns index i if pairs are found or -1 if no pairs are found
	 */
	private int numPairsFrom(int index) {		
		if (index < 0) {
			index = 0;
		}
		if (index >= 4) {
			return -1;
		}
		
		for (int i=index; i<4; i++) {
			if (hand[i].getRank() == hand[i+1].getRank()) { 
					return i;
			}
		}
		
	return -1;
	}

}
