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
    void conveyerbeltmove1() {
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(SOUTH);
        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(4,5).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,5), player.getSpace());
    }
    @Test
    void conveyerbeltTroughWall(){
        ConveyorBelt conveyorBelt = new ConveyorBelt();
        conveyorBelt.setHeading(SOUTH);
        board.getSpace(4,4).addWall(SOUTH);
        board.getSpace(4,4).addAction(conveyorBelt);
        board.getSpace(4,5).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,4), player.getSpace());
    }

    @Test
    void doubleConveyorBelt(){
        ConveyorBelt2 conveyorBelt = new ConveyorBelt2();
        conveyorBelt.setHeading(SOUTH);
        board.getSpace(4,5).addAction(conveyorBelt);
        board.getSpace(4,6).addAction(conveyorBelt);
        conveyorBelt.doAction(controller, board.getSpace(4,4));

        assertEquals(board.getSpace(4,6), player.getSpace());
    }

    @Test
    void doubleDirectionalConveyorBelt(){
        player.setHeading(EAST);
        ConveyorBelt3 conveyorBelt = new ConveyorBelt3();
        ConveyorBelt2 conveyorBelt2 = new ConveyorBelt2();
        conveyorBelt.setHeading(NORTH);
        board.getSpace(5,4).addAction(conveyorBelt);
        conveyorBelt2.setHeading(EAST);
        board.getSpace(4,4).addAction(conveyorBelt2);
        conveyorBelt2.setHeading(NORTH);
        board.getSpace(5,3).addAction(conveyorBelt2);

        conveyorBelt2.doAction(controller, board.getSpace(4,4));

        // Check if the players heading matches
        assertEquals(NORTH,player.getHeading());

        //Check if the players space matches the space after the conveyorbelt actions
        assertEquals(board.getSpace(5,4), player.getSpace());
    }






}







