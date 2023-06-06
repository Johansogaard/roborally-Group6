package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.TestUtils;
import org.junit.jupiter.api.Test;

import static dk.dtu.compute.se.pisd.roborally.model.Command.SPAM;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {
    private TestUtils testUtils;
    @Test
    public void testSpamAddCard() throws NoSuchMethodException {


        // Add a spam card to the discard pile
        Deck deck = new Deck();
        CommandCard spamCard = new CommandCard(SPAM);
       deck.addCard(spamCard);

        assertTrue(deck.discardPile.contains(spamCard));

        while (deck.cards.size() > 0) {
            deck.drawCard();
        }
        deck.drawCard();
        assertTrue(deck.cards.contains(spamCard));
        // Check if the spam card is in the discard pile

    }

    @Test
    public void testmakedeck() throws NoSuchMethodException {

        Deck deck = new Deck();


        // Check if a new deck is made and the drawn card is not null
        assertEquals(deck.cards.size(), 18);

    }

    @Test
    public void testDeckShuffling() {
        Deck deck = new Deck();
        Deck shuffled = new Deck();



       shuffled.shuffle();

        assertTrue(deck.cards!=shuffled.cards);

    }

    @Test
    public void testDrawCardFromEmptyDeck() {
        Deck deck = new Deck();

        // Draw all cards from the deck
        int deckSize = deck.cards.size();
        while(deck.cards.size() > 0) {
            deck.drawCard();
        }

        // Attempt to draw a card from an empty deck
        deck.drawCard();

        // Assert that the drawn card is null
        assertEquals(deck.cards.size(), 18);
    }
    @Test
    public void testAddCardToDeck() {
        Deck deck = new Deck();
        int initialDeckSize = deck.cards.size();

        CommandCard card = new CommandCard(Command.FORWARD);
        deck.addCard(card);

        assertEquals(initialDeckSize + 1, deck.cards.size());
        assertEquals(card, deck.cards.get(deck.cards.size()-1));
    }
}

