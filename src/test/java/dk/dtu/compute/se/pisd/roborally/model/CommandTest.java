package dk.dtu.compute.se.pisd.roborally.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {



//test if command is interactive
    @Test
    void isInteractive() {
        // Create a Command with no options
        Command nonInteractiveCommand = Command.FORWARD;
        assertFalse(nonInteractiveCommand.isInteractive());
    }

    @Test
    void getOptions() {
        // Create a Command with no options
        Command command = Command.FORWARD;

        // Retrieve the options
        List<Command> options = command.getOptions();

        // Check that the options list is empty
        assertTrue(options.isEmpty());
    }

}