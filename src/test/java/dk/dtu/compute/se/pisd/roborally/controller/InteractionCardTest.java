package dk.dtu.compute.se.pisd.roborally.controller;
import org.junit.jupiter.api.Test;

import dk.dtu.compute.se.pisd.roborally.model.*;


import java.lang.reflect.Method;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InteractionCardTest {
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

    @Test
    public void testProgrammingCards() throws Exception{
        Board board = new Board(8,8,"testboard"); // replace with a real Board object
        Player player = new Player(board, "Blue", "John");
        GameController controller = new GameController(board);

        //testcards
        CommandCard card = new CommandCard(Command.FORWARD);
        CommandCard card2 = new CommandCard(Command.Move_3);
        CommandCard card3 = new CommandCard(Command.FASTFORWARD);
        CommandCard card4 = new CommandCard(Command.BACK);
        CommandCard card5 = new CommandCard(Command.LEFT);
        CommandCard card6 = new CommandCard(Command.RIGHT);
        CommandCard card7 = new CommandCard(Command.uTurn);


        player.setSpace(board.getSpace(1,1)); // assume this sets initial position
        player.setHeading(SOUTH); // assume player will move downwards on the board

        // Use reflection to access the private executeCommand method
        Method executeCommand = GameController.class.getDeclaredMethod("executeCommand", Player.class, Command.class);
        executeCommand.setAccessible(true);

        // Call the private method with the necessary arguments
        executeCommand.invoke(controller, player, card.command);
        // After moving forward, player should now be at space (1,2)
        assertEquals(board.getSpace(1,2), player.getSpace());

        //moving 3 tiles forward
        executeCommand.invoke(controller, player, card2.command);
        assertEquals(board.getSpace(1,5), player.getSpace());

        executeCommand.invoke(controller, player, card3.command);
        assertEquals(board.getSpace(1,7), player.getSpace());

        executeCommand.invoke(controller, player, card4.command);
        assertEquals(board.getSpace(1,6), player.getSpace());

        executeCommand.invoke(controller, player, card5.command);
        assertEquals(EAST, player.getHeading());

        executeCommand.invoke(controller, player, card6.command);
        assertEquals(SOUTH, player.getHeading());

        executeCommand.invoke(controller, player, card7.command);
        assertEquals(NORTH, player.getHeading());
    }
}
