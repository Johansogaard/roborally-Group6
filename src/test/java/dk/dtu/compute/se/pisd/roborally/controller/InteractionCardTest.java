package dk.dtu.compute.se.pisd.roborally.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dk.dtu.compute.se.pisd.roborally.model.*;


import java.lang.reflect.Method;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InteractionCardTest {
    private Board board;
    private Player player;
    private GameController controller;

    Method executeCommand;

    Method executeNextStep;
    @Test
    public void testCardAssignmentToPlayer() {
        // Create a board object
        Board board = new Board(8,8,"Testboard");

        // Create a player object on the board
        Player player = new Player(board, "Blue", "John");

        // Create a command card object with command FORWARD
        CommandCard card = new CommandCard(Command.FORWARD);

        // Set the first card of player's hand to be the created command card
        player.getCards()[0].setCard(card);

        // Assert that the first card in player's hand is correctly assigned as the created command card
        assertEquals(card, player.getCardField(0).getCard());

        // Set the first card of player's program to be the created command card
        player.getProgram()[0].setCard(card);

        // Assert that the first card in player's program is correctly assigned as the created command card
        assertEquals(card, player.getProgramField(0).getCard());
    }


    @BeforeEach
    public void setUp() throws NoSuchMethodException {
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
    public void testMoveForward() throws Exception {
        executeCommand.invoke(controller, player, Command.FORWARD);
        assertEquals(board.getSpace(4, 5), player.getSpace());
    }

    @Test
    public void testMove3Tiles() throws Exception {
        executeCommand.invoke(controller, player, Command.Move_3);
        assertEquals(board.getSpace(4, 7), player.getSpace());
    }

    @Test
    public void testFastForward() throws Exception {
        executeCommand.invoke(controller, player, Command.FASTFORWARD);
        assertEquals(board.getSpace(4, 6), player.getSpace());
    }

    @Test
    public void testMoveBackward() throws Exception {
        executeCommand.invoke(controller, player, Command.BACK);
        assertEquals(board.getSpace(4, 3), player.getSpace());
    }

    @Test
    public void testTurnLeft() throws Exception {
        executeCommand.invoke(controller, player, Command.LEFT);
        assertEquals(EAST, player.getHeading());
    }

    @Test
    public void testTurnRight() throws Exception {
        executeCommand.invoke(controller, player, Command.RIGHT);
        assertEquals(WEST, player.getHeading());
    }

    @Test
    public void testUTurn() throws Exception {
        executeCommand.invoke(controller, player, Command.uTurn);
        assertEquals(NORTH, player.getHeading());
    }
    @Test
    public void testAgain() throws Exception {

        // Add two AGAIN cards to the player's program
        player.getProgramField(0).setCard(new CommandCard(Command.FORWARD));
        player.getProgramField(1).setCard(new CommandCard(Command.AGAIN));

        // Call executeCommand with the second AGAIN card
        executeNextStep.invoke(controller);
        executeNextStep.invoke(controller);

        // The player should repeat the action of the previous card (FORWARD)
        assertEquals(board.getSpace(4, 6), player.getSpace()); // Player should move forward to space (4, 6)

    }
    @Test
    public void testAgainRepeat() throws Exception {

        // Add two AGAIN cards to the player's program
        player.getProgramField(0).setCard(new CommandCard(Command.FORWARD));
        for(int i=1; i<5;i++){player.getProgramField(i).setCard(new CommandCard(Command.AGAIN));}




        // Call executeCommand with the second AGAIN card
        for(int i=0; i<5;i++){
            executeNextStep.invoke(controller);}



        // The player should repeat the action of the previous card (FORWARD)
        assertEquals(board.getSpace(4, 9), player.getSpace()); // Player should move forward to space (4, 6)

    }
    @Test
    public void testAgainalternate() throws Exception {

        // Add two AGAIN cards to the player's program
        player.getProgramField(0).setCard(new CommandCard(Command.FORWARD));
        player.getProgramField(1).setCard(new CommandCard(Command.AGAIN));
        player.getProgramField(2).setCard(new CommandCard(Command.RIGHT));
        player.getProgramField(3).setCard(new CommandCard(Command.AGAIN));




        // Call executeCommand with the second AGAIN card
        for(int i=0; i<4;i++){
            executeNextStep.invoke(controller);}



        // The player should repeat the action of the previous card (FORWARD)
        assertEquals(board.getSpace(4, 6), player.getSpace());
        assertEquals(NORTH, player.getHeading());// Player should move forward to space (4, 6)

    }
}
