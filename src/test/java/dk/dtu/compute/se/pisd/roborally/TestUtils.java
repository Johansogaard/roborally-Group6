package dk.dtu.compute.se.pisd.roborally;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Command;
import dk.dtu.compute.se.pisd.roborally.model.Phase;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import java.lang.reflect.Method;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

public class TestUtils {
    protected Board board;
    public Player player;
    protected GameController controller;
    protected Method executeCommand;
    protected Method executeNextStep;

    public void setUp() throws NoSuchMethodException {
        board = new Board(10, 10, "testboard");
        player = new Player(board, "Blue", "John");
        controller = new GameController(board);
        player.setSpace(board.getSpace(4, 4)); // Set the player at the center of the board
        player.setHeading(SOUTH); // Assume player will move downwards on the board
        executeCommand = GameController.class.getDeclaredMethod("executeCommand", Player.class, Command.class);
        executeCommand.setAccessible(true);
    }

    protected void setUpAgain() throws NoSuchMethodException {
        setUp();

        executeNextStep = GameController.class.getDeclaredMethod("executeNextStep");
        executeNextStep.setAccessible(true);

        board.testSetCurrentPlayer(player);
        board.setPhase(Phase.ACTIVATION);
        board.addPlayer(player);
    }
}
