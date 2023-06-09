package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LaserTest {
    private Board board;
    private Player player1, player2;
    private GameController controller;

    @BeforeEach
    public void setUp() {
        board = new Board(10, 10, "testboard");
        player1 = new Player(board, "Blue", "John");
        player2 = new Player(board, "Red", "Mike");
        controller = new GameController(board);
        player1.setSpace(board.getSpace(4, 4)); // Set the player at the center of the board

         // Set player2 one space east of player1

        player1.setHeading(EAST); // Player1 heading east
        player2.setHeading(WEST); // Player2 heading west

        board.addPlayer(player1);
        board.addPlayer(player2);
    }

    @Test
    public void testLaserAction() {
        // Assume that player1 fires a laser of power 2
        Laser laser = new Laser(EAST, 1);

        laser.doAction(controller, board.getSpace(4,4));

        // Check the life token of player2, assuming it was 3 at start and applyDamage reduces life by 1
        assertEquals(1, player1.deck.discardPile.size());
    }

    @Test
    public void testLaserAction3() {
        // Player2 is moved out of the laser's path

        Laser laser = new Laser(EAST, 3);

        laser.doAction(controller, board.getSpace(4,4));

        // Check the life token of player2, assuming it was 3 at start
        assertEquals(3, player1.deck.discardPile.size());
    }

    @Test
    public void testLaserPlayerBetween() {
        // Player2 is moved out of the laser's path
        player2.setSpace(board.getSpace(4, 5));
        Laser laser = new Laser(SOUTH, 1);

        laser.doAction(controller, board.getSpace(4,0));

        // Check the life token of player2, assuming it was 3 at start
        assertEquals(1, player1.deck.discardPile.size());
        assertEquals(0, player2.deck.discardPile.size());
    }

    @Test
    public void testLaserLongShot() {
        // Player2 is moved out of the laser's path
        player2.setSpace(board.getSpace(4, 9));
        Laser laser = new Laser(SOUTH, 1);

        laser.doAction(controller, board.getSpace(4,0));

        // Check the life token of player2, assuming it was 3 at start
        assertEquals(1, player1.deck.discardPile.size());

    }
    @Test
    public void testLaserWall() {
        // Player2 is moved out of the laser's path
        player2.setSpace(board.getSpace(4, 9));
        Laser laser = new Laser(SOUTH, 1);
        board.getSpace(4,2).addWall(SOUTH);
        laser.doAction(controller, board.getSpace(4,0));

        // Check the life token of player2, assuming it was 3 at start
        assertEquals(0, player1.deck.discardPile.size());
}

    @Test
    public void testLaserAntenna() {
        // Player2 is moved out of the laser's path
        player2.setSpace(board.getSpace(4, 9));
        Laser laser = new Laser(SOUTH, 1);
        board.getSpace(4,2).addAntenna();
        laser.doAction(controller, board.getSpace(4,0));

        // Check the life token of player2, assuming it was 3 at start
        assertEquals(0, player1.deck.discardPile.size());
    }

}