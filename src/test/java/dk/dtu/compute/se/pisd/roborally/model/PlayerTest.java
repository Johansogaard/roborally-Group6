package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static dk.dtu.compute.se.pisd.roborally.model.Command.SPAM;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    Board board;
    Player player;
    GameController controller;

    Method executeCommand;
    Method executeNextStep;
    @BeforeEach
    public void setUpAgain() throws NoSuchMethodException {
        board = new Board(10, 10, "testboard");
        player = new Player(board, "Blue", "John");
        controller = new GameController(board);
        player.setSpace(board.getSpace(4, 4)); // Set the player at the center of the board
        player.setHeading(SOUTH); // Assume player will move downwards on the board
        executeCommand = GameController.class.getDeclaredMethod("executeCommand", Player.class, Command.class);
        executeCommand.setAccessible(true);

        executeNextStep = GameController.class.getDeclaredMethod("executeNextStep");
        executeNextStep.setAccessible(true);

        board.testSetCurrentPlayer(player);
        board.setPhase(Phase.ACTIVATION);
        board.addPlayer(player);
    }

    @Test
    public void testMoveForward() {


        player.moveForward();

        assertEquals(board.getSpace(4, 5), player.getSpace());
    }

    @Test
    public void testMoveForwardWithWall() {


        board.getSpace(4, 5).addWall(Heading.SOUTH);

        player.moveForward();

        // Since there is a wall, the player should stay in the same space
        assertEquals(board.getSpace(4, 5), player.getSpace());
    }

    @Test
    public void testPushPlayer() {


        // Create another player
        Player player2 = new Player(board, "Red", "Jane");
        player2.setSpace(board.getSpace(4, 5)); // Set player2 in front of player


        // Invoke pushPlayer on player
        player.moveForward();

        assertEquals(board.getSpace(4, 6), player2.getSpace()); // Player should move forward
        assertEquals(board.getSpace(4, 5), player.getSpace()); // Player2 should move to player's previous space

    }

    @Test
    public void testPushPlayer2() {


        // Create another player
        Player player2 = new Player(board, "Red", "Jane");
        Player player3 = new Player(board, "green", "zohan");
        player2.setSpace(board.getSpace(4, 5)); // Set player2 in front of player
        player3.setSpace(board.getSpace(4, 6));

        // Invoke pushPlayer on player
        player.moveForward();
        assertEquals(board.getSpace(4, 7), player3.getSpace());
        assertEquals(board.getSpace(4, 6), player2.getSpace()); // Player should move forward
        assertEquals(board.getSpace(4, 5), player.getSpace()); // Player2 should move to player's previous space

    }

    @Test
    public void testPushPlayerWithWall() {


        // Create another player
        Player player2 = new Player(board, "Red", "Jane");
        player2.setSpace(board.getSpace(4, 5)); // Set player2 in front of player

       player2.getSpace().addWall(Heading.SOUTH);
       player.moveForward();

        // Since there is a wall, the player and player2 should stay in the same spaces
        assertEquals(board.getSpace(4, 4), player.getSpace());
        assertEquals(board.getSpace(4, 5), player2.getSpace());

    }
    @Test
    void goInPit() {
        board.getSpace(6,9).addRebootToken();
        board.getSpace(0,0).addPit();
        board.getSpace(0,0).setPlayer(player);
        assertEquals(player.getSpace(),board.getSpace(6,9));
    }
    @Test
    void stepOutOfBoard() {
        board.getSpace(6,9).addRebootToken();

        board.getSpace(9,9).setPlayer(player);
        player.setHeading(SOUTH);
        player.moveForward();

        assertEquals(player.getSpace(),board.getSpace(6,9));
    }
    @Test
    void Reboot() {
        board.getSpace(6,9).addRebootToken();
        player.preboot();

        assertEquals(player.getSpace(),board.getSpace(6,9));

        assertTrue(player.deck.discardPile.get(0).command== SPAM);
    }

    @Test
    void RebootWhenPlayerAlreadyThere() {
        Player player2 = new Player(board, "red", "J");
        board.getSpace(6,9).addRebootToken();

        player.preboot();
        player2.preboot();

        assertEquals(player.getSpace(),board.getSpace(5,9));
        assertEquals(player2.getSpace(),board.getSpace(6,9));

    }
    @Test
    void PushedOutOfBoard(){
        Player player2 = new Player(board, "red", "J");
        board.getSpace(6,9).addRebootToken();
        player.setSpace(board.getSpace(9, 9));
        player2.setSpace(board.getSpace(9, 8));
        player2.setHeading(SOUTH);
        player2.moveForward();

        assertEquals(player.getSpace(),board.getSpace(6,9));
    }

    @Test
    void PushedOutOfBoard2(){
        Player player2 = new Player(board, "red", "J");
        Player player3 = new Player(board, "green", "zohan");
        board.getSpace(6,9).addRebootToken();
        player.setSpace(board.getSpace(9, 9));
        player2.setSpace(board.getSpace(9, 8));
        player3.setSpace(board.getSpace(9, 7));
        player3.setHeading(SOUTH);
        player3.moveForward();
        player3.moveForward();
        assertEquals(player.getSpace(),board.getSpace(5,9));
        assertEquals(player2.getSpace(),board.getSpace(6,9));
    }

}