package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sun.javafx.scene.traversal.Direction.LEFT;
import static com.sun.javafx.scene.traversal.Direction.RIGHT;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static org.junit.jupiter.api.Assertions.*;

public class GearTest {
        private GameController controller;
        private Board board;
        private Player player;

        @BeforeEach
        void setUp() {
            board = new Board(10, 10, "testboard");
            player = new Player(board, "Blue", "John");
            controller = new GameController(board);
            player.setSpace(board.getSpace(4, 4));
            player.setHeading(SOUTH);
        }

        @Test
        void doActionTurnsPlayerLeft() {
            Gear gear = new Gear(LEFT);

            gear.doAction(controller, board.getSpace(4,4));
            assertEquals(Heading.EAST, player.getHeading(), "The player's heading should be WEST after turning LEFT from NORTH");
        }

        @Test
        void doActionTurnsPlayerRight() {
            Gear gear = new Gear(RIGHT);
            gear.doAction(controller, board.getSpace(4,4));

            assertEquals(Heading.WEST, player.getHeading(), "The player's heading should be EAST after turning RIGHT from NORTH");
        }
    }