package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConveyorbeltTest {

    private final int TEST_WIDTH = 8;

    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    private List<FieldAction> actions = new ArrayList<>();

    private List<FieldAction> getActions() {
        return actions;
    }

    private ConveyorBelt2 conveyorBelt2;
    private ConveyorBelt conveyorBelt;

    private Player player;

    private SpaceView spaceView;


    @BeforeEach
    void setUp() {
        Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
        gameController = new GameController(board);
        conveyorBelt2 = new ConveyorBelt2();

        for (int i = 0; i < 6; i++) {
            Player player = new Player(board, null,"Player " + i);
            board.addPlayer(player);
            player.setSpace(board.getSpace(i, i));
            player.setHeading(Heading.values()[i % Heading.values().length]);
        }
        board.setCurrentPlayer(board.getPlayer(0));
    }

    @AfterEach
    void tearDown() {
        gameController = null;
    }


    @Test
    public void testConveyorBelt2() {
        // Get the current player and space
        Board board = gameController.board;
        Player currentPlayer = board.getCurrentPlayer();
        Space currentSpace = currentPlayer.getSpace();

        // Set up the conveyor belt heading
        Heading conveyorBeltHeading = Heading.EAST; // Change this to the desired heading

        // Add the conveyor belt action to the current space
        currentSpace.addAction(conveyorBelt2);

        // Perform the action
        boolean result = conveyorBelt2.doAction(gameController, currentSpace);

        // Verify the results
        assertTrue(result);
        assertNull(currentSpace.getPlayer());  // Player should have moved from the current space

        // Check if the player's heading matches the conveyor belt's heading
        assertEquals(conveyorBeltHeading, currentPlayer.getHeading());

        // Get the new space after the first movement
        Space newSpace = currentPlayer.getSpace();

        // Perform the action again
        result = conveyorBelt2.doAction(gameController, newSpace);

        // Verify the results
        assertTrue(result);
        assertNull(newSpace.getPlayer());  // Player should have moved from the new space

        // Check if the player's heading is still the same as the conveyor belt's heading
        assertEquals(conveyorBeltHeading, currentPlayer.getHeading());

        // Get the final space after the second movement
        Space finalSpace = currentPlayer.getSpace();

        // Compare x value before and after conveyor belt as well as y values, to check whether the player moved 2 spaces in the right direction

        Space expectedFinalSpace = board.getNeighbour(board.getNeighbour(currentSpace, conveyorBeltHeading), conveyorBeltHeading);
        assertEquals(expectedFinalSpace.x, finalSpace.x);
        assertEquals(expectedFinalSpace.y, finalSpace.y);

    }

    @Test
    public void ConveyorBelt() {
        Board board = gameController.board;
        Player currentPlayer = board.getCurrentPlayer();
        Space currentSpace = currentPlayer.getSpace();

        // Set up the conveyor belt heading
        Heading conveyorBeltHeading = Heading.EAST; // Change this to the desired heading

        // Add the conveyor belt action to the current space
        currentSpace.addAction(conveyorBelt);

        // Perform the action
        boolean result = conveyorBelt.doAction(gameController, currentSpace);

        // Verify the results
        assertTrue(result);
        assertNull(currentSpace.getPlayer());  // Player should have moved from the current space


    }




}







