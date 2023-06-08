package dk.dtu.compute.se.pisd.roborally.model;

import static org.junit.jupiter.api.Assertions.*;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

    class PushPanelTest {
        private final int TEST_WIDTH = 8;
        private final int TEST_HEIGHT = 8;
        private GameController gameController;
        private List<FieldAction> actions = new ArrayList<>();

        @BeforeEach
        void setUp() {
            Board board = new Board(TEST_WIDTH, TEST_HEIGHT);
            gameController = new GameController(board);

            for (int i = 0; i < 6; i++) {
                Player player = new Player(board, null, "Player " + i);
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
        public void testPushPanel() {
            // Get the current player and space
            Board board = gameController.board;
            Player currentPlayer = board.getCurrentPlayer();
            Space currentSpace = currentPlayer.getSpace();

            // Set up the push panel heading
            Heading pushPanelHeading = Heading.EAST; // Change this to the desired heading

            // Create a push panel instance
            PushPanel pushPanel = new PushPanel();
            pushPanel.setHeading(pushPanelHeading);

            // Add the push panel action to the current space
            currentSpace.addAction(pushPanel);

            // Perform the action
            boolean result = pushPanel.doAction(gameController, currentSpace);

            // Verify the results
            assertTrue(result);
            assertNull(currentSpace.getPlayer());  // Player should have moved from the current space

            // Check if the player's heading matches the push panel's heading
            assertEquals(pushPanelHeading, currentPlayer.getHeading());

            // Get the new space after the movement
            Space newSpace = currentPlayer.getSpace();

            // Perform the action again
            result = pushPanel.doAction(gameController, newSpace);

            // Verify the results
            assertTrue(result);
            assertNull(newSpace.getPlayer());  // Player should have moved from the new space

            // Check if the player's heading is still the same as the push panel's heading
            assertEquals(pushPanelHeading, currentPlayer.getHeading());
        }
    }
