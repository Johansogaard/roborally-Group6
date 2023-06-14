package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {

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
        Antenna antenna = new Antenna(board,0,9);
        board.setAntenna(antenna);
    }



    @Test
    void NotWinning(){

        Checkpoint checkpoint = new Checkpoint(1);
        Checkpoint checkpoint2 = new Checkpoint(2);
        board.setCheckpoint(checkpoint);
        board.setCheckpoint(checkpoint2);

        checkpoint.doAction(controller, board.getSpace(4, 4));

        assertEquals(1, player.getLastCheckpoint());
        assertFalse(controller.won);

}
    @Test
    void WinningBig(){

        Checkpoint checkpoint = new Checkpoint(1);
        Checkpoint checkpoint2 = new Checkpoint(2);
        Checkpoint checkpoint3 = new Checkpoint(3);
        Checkpoint checkpoint4 = new Checkpoint(4);
        board.setCheckpoint(checkpoint);
        board.setCheckpoint(checkpoint2);
        board.setCheckpoint(checkpoint3);
        board.setCheckpoint(checkpoint4);
        checkpoint.doAction(controller, board.getSpace(4, 4));
        checkpoint2.doAction(controller, board.getSpace(4, 4));
        checkpoint3.doAction(controller, board.getSpace(4, 4));
        checkpoint4.doAction(controller, board.getSpace(4, 4));
        assertEquals(4, player.getLastCheckpoint());
        assertTrue(controller.won);

    }

    @Test
    void TryingToCheat(){

        Checkpoint checkpoint = new Checkpoint(1);
        Checkpoint checkpoint2 = new Checkpoint(2);
        Checkpoint checkpoint3 = new Checkpoint(3);
        Checkpoint checkpoint4 = new Checkpoint(4);
        board.setCheckpoint(checkpoint);
        board.setCheckpoint(checkpoint2);
        board.setCheckpoint(checkpoint3);
        board.setCheckpoint(checkpoint4);

        checkpoint4.doAction(controller, board.getSpace(4, 4));
        assertEquals(0, player.getLastCheckpoint());
        assertFalse(controller.won);

    }



    }
