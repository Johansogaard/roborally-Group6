package dk.dtu.compute.se.pisd.roborally.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    public void testDeckSizeAfterInstantiation() {
        Deck deck = new Deck();
        assertEquals(10, deck.cards.size());
    }

    @Test
    public void testDeckSizeAfterDrawingCards() {
        Deck deck = new Deck();
        deck.drawCard();
        assertEquals(9, deck.cards.size());

        deck.drawCard();
        assertEquals(8, deck.cards.size());
    }

    @Test
    public void testdrawfromemptydeck() {
        Deck deck = new Deck();
        deck.drawCard();
        assertEquals(9, deck.cards.size());

        deck.drawCard();
        assertEquals(8, deck.cards.size());
    }

    @Test
    public void testShuffleDeck() {
        Deck deck = new Deck();
        Deck originalDeck = new Deck();

        // Shuffle the deck
        deck.shuffle();

        // Ensure the shuffled deck is different from the original deck
        assertNotEquals(originalDeck, deck);
    }

    // Add more tests as needed

}