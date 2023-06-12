package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;
import static org.junit.jupiter.api.Assertions.*;

class ConveyorbeltTest {

        private GameController controller;
        private Board board;
        private Player player;

        private Player player2;
        @BeforeEach
        void setUp() {
            board = new Board(10, 10, "testboard");
            player = new Player(board, "Blue", "John");
            player2 = new Player(board, "Red", "Anders");
            controller = new GameController(board);
            player.setSpace(board.getSpace(4, 4)); // Set the player at the center of the board
            player.setHeading(SOUTH);
        }

    @Test
    void conveyerBeltmove1() {
        Belt conveyorBelt = new Belt();
        conveyorBelt.setHeading(SOUTH);
        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(4,5).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,5), player.getSpace());
    }
    @Test
   void conveyerBeltTroughWall(){
        Belt conveyorBelt = new Belt();
        conveyorBelt.setHeading(SOUTH);
        board.getSpace(4,4).addWall(SOUTH);
        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(5,4).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,4), player.getSpace());
    }

    @Test
    void doubleConveyerBeltTroughWall(){
        BeltDouble conveyorBelt = new BeltDouble();
        conveyorBelt.setHeading(EAST);
        board.getSpace(4,4).addWall(EAST);
        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(5,4).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,4), player.getSpace());
    }

    @Test
    void doubleRotatingConveyerBeltTroughWall(){
        BeltDouble conveyorBelt = new BeltDouble();
        BeltDoubleRotating converBelt2 = new BeltDoubleRotating();

        conveyorBelt.setHeading(EAST);
        converBelt2.setHeading(NORTH);
        board.getSpace(4,5).addWall(NORTH);
        board.getSpace(5,4).addAction(converBelt2);
        board.getSpace(4,4).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(5,4), player.getSpace());
    }

    @Test
    void conveyerBeltKeepOrientation() {
        Belt conveyorBelt = new Belt();
        conveyorBelt.setHeading(SOUTH);

        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(4,5).addAction(conveyorBelt);
        player.setHeading(WEST);
        conveyorBelt.doAction(controller, board.getSpace(4,4));
        assertEquals(player.getHeading(),WEST);

    }

    @Test
    void DoubleBeltKeepOrientation() {
            BeltDouble conveyorBelt = new BeltDouble();
        conveyorBelt.setHeading(SOUTH);

        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(4,5).addAction(conveyorBelt);
        player.setHeading(WEST);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(player.getHeading(),WEST);

    }


    /*@Test
    void conveyerbeltIntersect(){
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(3,4).addAction(conveyorBelt);
        board.getSpace(4,5).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,4), player.getSpace());
    }*/



    /*@Test
    public void testConveyorBelt2() {
        // Get the current player and space

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


    }*/




}







