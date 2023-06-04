package dk.dtu.compute.se.pisd.roborally.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {

    @Test
    public void testDeckSizeAfterInstantiation() {
        Deck deck = new Deck();
        assertEquals(18, deck.cards.size());
    }

    @Test
    public void testDeckSizeAfterDrawingCards() {
        Deck deck = new Deck();
        deck.drawCard();
        assertEquals(17, deck.cards.size());

        deck.drawCard();
        assertEquals(16, deck.cards.size());
    }

    @Test
    public void testDrawFromEmptyDeck() {
        Deck deck = new Deck();

        for(int i=0; i<18;i++){deck.drawCard();}

        assertEquals(0, deck.cards.size());

        deck.drawCard();
        assertEquals(17, deck.cards.size());
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