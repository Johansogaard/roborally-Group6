package dk.dtu.compute.se.pisd.roborally.model;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.NORTH;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;
import static org.junit.jupiter.api.Assertions.*;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class PushPanelTest {
        private final int TEST_WIDTH = 8;
        private final int TEST_HEIGHT = 8;
        private GameController gameController;
        private final List<FieldAction> actions = new ArrayList<>();
Board board;
Player player;
GameController controller;

Method executeNextStep;

            @BeforeEach
            public void setUpAgain() throws NoSuchMethodException {
                board = new Board(10, 10, "testboard");
                player = new Player(board, "Blue", "John");
                controller = new GameController(board);
                player.setSpace(board.getSpace(4, 4));
                player.setHeading(SOUTH);

                executeNextStep = GameController.class.getDeclaredMethod("executeNextStep");
                executeNextStep.setAccessible(true);

                board.testSetCurrentPlayer(player);
                board.setPhase(Phase.ACTIVATION);
                board.addPlayer(player);

                Antenna antenna = new Antenna(board,0,9);
                board.setAntenna(antenna);
            }

        @AfterEach
        void tearDown() {
            gameController = null;
        }

        @Test
        public void testPushPanel() {

            PushPanel pushPanel = new PushPanel();
            pushPanel.setHeading(NORTH);


            board.getSpace(4,4).addAction(pushPanel);
            board.setStep(1);
            pushPanel.doAction(controller, board.getSpace(4,4));

            assertEquals(player.getSpace(),board.getSpace(4,3));



        }

        @Test
        public void testPushPanelOffTurn() {

            PushPanel pushPanel = new PushPanel();
            pushPanel.setHeading(NORTH);


            board.getSpace(4,4).addAction(pushPanel);
            board.setStep(0);
            pushPanel.doAction(controller, board.getSpace(4,4));


            assertEquals(player.getSpace(),board.getSpace(4,4));

        }
    }
