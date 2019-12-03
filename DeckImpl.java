package style;

public class DeckImpl implements Deck {
	
	//Instance fields
	private Card[] card_array;			
	private int cards_left;
	
	/*
	 * Constructor initializes a new instance of Deck
	 * Fills the card array with 52 new card objects in random order
	 */
	public DeckImpl() {
		cards_left = 52;
		card_array = new Card[cards_left];

		int cidx = 0;
		for (Card.Suit suit : Card.Suit.values()) {
			for (int rank = 2; rank <= 14; rank++) {
				card_array[cidx] = new CardImpl(rank, suit);
				cidx += 1;
			}
		}
		
		for (int i=0; i<card_array.length; i++) {
			int swap_idx = i + ((int) (Math.random() * (card_array.length - i)));
			
			Card tmp = card_array[i];
			card_array[i] = card_array[swap_idx];
			card_array[swap_idx] = tmp;
		}		
	}

	/*
	 * Methods that determines if there are enough cards left to deal a hand
	 * Output: true if enough, false if not
	 */
	public boolean hasHand() {
		if (cards_left >= 5) {
			return false;
		}
		return true;
	}

	/*
	 * Method that returns a card object and subtracts one from cards_left
	 * Output: Card object dealtCard
	 * Throws exception if there are no cards left
	 */
	public Card dealNextCard() {
		if (cards_left== 0) {
			throw new RuntimeException("There are no cards left in the deck");
		}
		Card dealtCard = card_array[nextUndealtIndex()];
		cards_left -= 1;
		return dealtCard;
	}

	/*
	 * Method returns a new PokerHandImpl object containing five cards
	 * Output: PokerHand object hand_cards
	 * Throws exception if there are not enough cards to deal a complete hand
	 */
	public PokerHand dealHand() {
		if (!hasHand()) {
			throw new RuntimeException("Deck does not have enough cards to deal another hand");
		}
		
		Card[] hand_cards = new Card[5];
		for (int i=0; i<hand_cards.length; i++) {
			
			hand_cards[i] = dealNextCard();
		}
		
		return new PokerHandImpl(hand_cards);
	}	

	/*
	 * Method that deals the next available card from an input card
	 * Input: Card object Card
	 * Output: Returns card object that is next in deck
	 * Throws exception if there are no cards left in the deck
	 */
	public void findAndRemove(Card card) {
		if (cards_left == 0) {
			return;
		}
		
		for (int i=nextUndealtIndex(); i<52; i++) {
			if (card_array[i].equals(card)) {
				Card tmp = card_array[i];
				card_array[i] = card_array[nextUndealtIndex()];
				card_array[nextUndealtIndex()] = tmp;
				dealNextCard();
				return;
			}
		}
		return;
	}
	
	/*
	 * Helper method that returns the next card in the deck
	 */
	private int nextUndealtIndex() {
		int x = 52-cards_left;
		return x;
	}
}
