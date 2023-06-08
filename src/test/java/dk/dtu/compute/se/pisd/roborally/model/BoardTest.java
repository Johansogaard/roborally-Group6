package dk.dtu.compute.se.pisd.roborally.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private final int width = 5;
    private final int height = 5;
    private final String boardName = "testBoard";

    @BeforeEach
    void setUp() {
// Initialize a board
        this.board = new Board(width, height, boardName);
    }

    @Test
    /*
    Test that checks that a board is initialized with a specific width, height, and name,
    and all the spaces in the board are created and properly assigned with the corresponding x and y coordinates
     */
    void BoardInitialization() {

        // Check the board's spaces, left to right, then next row and so on.
        Space[][] spaces = board.getSpaces();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // Assert that each space is not null ie. that the space exists
                assertNotNull(spaces[x][y]);

                // Assert that the space's x and y coordinates match its position in the array
                assertEquals(x, spaces[x][y].x);
                assertEquals(y, spaces[x][y].y);
            }
        }
    }

    @Test
    //checks that set and getGameId works - that it sets the right gameid and returns the correct id
    void getGameId() {
        int width = 5;
        int height = 5;
        String boardName = "testBoard";
        int gameId = 10;

        // Initialize a board
        Board board = new Board(width, height, boardName);
        // Set gameId
        board.setGameId(gameId);

        // Assert that getGameId returns the correct gameId
        assertEquals(gameId, board.getGameId());
    }

    @Test
    /*
    checking that for any valid (x, y) pair on the board, getSpace(x, y) returns a space where space.x == x and space.y == y

     */
    void getSpace() {
        // Test valid coordinates
        for(int x = 0; x < width; x++){
            for(int y = 0; y < height; y++){
                var space = board.getSpace(x, y);
                assertNotNull(space);
                assertEquals(x, space.x);
                assertEquals(y, space.y);
            }
        }

        // Test invalid coordinates. then x and y would be one more than the index, so it would be outside the board
        var invalidSpace = board.getSpace(width, height);
        assertNull(invalidSpace);
    }

    @Test
    void addPlayer() {
        // Create a new player
        Player player = new Player(board, "red", "TestPlayer");

        // Add the player to the board
        board.addPlayer(player);

        // Assert that the player is now in the board's player list
        assertTrue(board.getPlayers().contains(player));
    }

    @Test
    void getPhase() {
        // Assert that the initial phase is INITIALISATION
        assertEquals(Phase.INITIALISATION, board.getPhase());

        // Change the phase to something else
        board.setPhase(Phase.PLAYER_INTERACTION);

        // Assert that the phase is now the new phase
        assertEquals(Phase.PLAYER_INTERACTION, board.getPhase());
    }


    @Test
    void getStep() {
        // Assert that the initial step is 0
        assertEquals(0, board.getStep());

        // Change the step to something else
        board.setStep(3);

        // Assert that the step is now the new step
        assertEquals(3, board.getStep());

    }

    @Test
    void getPlayerNumber() {
        Player player1 = new Player(board,"red" ,"player1");
        Player player2 = new Player(board ,"blue","player2");

        // Add the players to the board
        board.addPlayer(player1);
        board.addPlayer(player2);

        // Test getPlayerNumber for player1
        int playerNumber1 = board.getPlayerNumber(player1);
        assertEquals(0, playerNumber1);

        // Test getPlayerNumber for player2
        int playerNumber2 = board.getPlayerNumber(player2);
        assertEquals(1, playerNumber2);
    }

    @Test
    void getNeighbour() {
        Space currentSpace = board.getSpace(2, 2);
        Space northNeighbour = board.getSpace(2, 1);
        Space southNeighbour = board.getSpace(2, 3);
        Space eastNeighbour = board.getSpace(3, 2);
        Space westNeighbour = board.getSpace(1, 2);

        // Test getNeighbour for each direction
        assertEquals(northNeighbour, board.getNeighbour(currentSpace, Heading.NORTH));
        assertEquals(southNeighbour, board.getNeighbour(currentSpace, Heading.SOUTH));
        assertEquals(eastNeighbour, board.getNeighbour(currentSpace, Heading.EAST));
        assertEquals(westNeighbour, board.getNeighbour(currentSpace, Heading.WEST));
    }

    @Test
    void getStatusMessage() {
        // Set up player and phase
        Player player = new Player(board, "red", "TestPlayer");
        board.addPlayer(player);
        board.setCurrentPlayer(player);
        board.setPhase(Phase.INITIALISATION);
        board.setStep(1);

        String statusMessage = board.getStatusMessage();
        String expectedStatusMessage = "Phase: " + Phase.INITIALISATION.name() + ", Player = " + player.getName() + ", Step: 1";

        assertEquals(expectedStatusMessage, statusMessage);
    }



    @Test
    void setPlayerOrder() {
        // Set up players
        Player player1 = new Player(board, "red", "TestPlayer1");
        Player player2 = new Player(board, "blue", "TestPlayer2");
        Player player3 = new Player(board, "green", "TestPlayer3");

        // Add players to board
        board.addPlayer(player1);
        board.addPlayer(player2);
        board.addPlayer(player3);

        // Call setPlayerOrder
        board.setPlayerOrder();

        // Check order of players
        assertEquals(player1, board.getPlayerOrder().get(0));
        assertEquals(player2, board.getPlayerOrder().get(1));
        assertEquals(player3, board.getPlayerOrder().get(2));
    }
}