package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConveyorbeltTestTest {

    private final int TEST_WIDTH = 8;

    private final int TEST_HEIGHT = 8;

    private GameController gameController;

    private List<FieldAction> actions = new ArrayList<>();

    private List<FieldAction> getActions() {
        return actions;
    }

    private ConveyorBelt2 conveyorBelt2;

    //private Space space;

    private SpaceView spaceView;

    private ConveyorBelt2 getConveyorBelt2() {

        ConveyorBelt2 belt = null;


        for (FieldAction action : this.actions) {
            if (action instanceof ConveyorBelt2 && belt == null) {
                belt = (ConveyorBelt2) action;
            }
        }

        return belt;
    }


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
    public void testDoAction() {
        Board board =  gameController.board;
        Player player1 = board.getPlayer(0);
        Player player2 = board.getPlayer(1);

        gameController.moveCurrentPlayerToSpace(board.getSpace(1,1));

        boolean result = conveyorBelt2.doAction(gameController, (board.getSpace(1,1)));

        assertTrue((result));
        assertEquals(player1, board.getSpace(3,1).getPlayer(),"Player"+player1.getName()+"should be here");

    }
}







