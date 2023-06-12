package dk.dtu.compute.se.pisd.roborally.fileaccess;
import dk.dtu.compute.se.pisd.roborally.model.Board;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;



import static org.junit.jupiter.api.Assertions.*;

class LoadSaveGameTest {

    private final String testBoardsPath = "src/test/boards";



    @Test
    void loadBoard_shouldLoadBoardFromFile() {
        // Arrange
        Board expectedBoard = createTestBoard();
        String boardName = "test_board";
        saveTestBoard(expectedBoard, testBoardsPath, boardName);

        // Act
        Board actualBoard = LoadSaveGame.loadBoard(testBoardsPath, boardName);

        // Assert
        assertNotNull(actualBoard);
        assertEquals(expectedBoard.height, actualBoard.width);
        assertEquals(expectedBoard.width, actualBoard.height);
    }

    @Test
    void saveBoard_shouldSaveBoardToFile() {
        // Arrange
        Board boardToSave = createTestBoard();
        String boardName = "test_board";

        // Act
        LoadSaveGame.saveBoard(boardToSave, testBoardsPath, boardName);

        // Assert
        File savedFile = new File(testBoardsPath + "/" + boardName+".json");
        assertTrue(savedFile.exists());
    }

    private Board createTestBoard() {
        // Create a test board with specific properties
        int width = 8;
        int height = 8;
        // Create an empty board with the specified width and height
        Board board = new Board(width, height);
        // Add any required fields, players, etc., to the board
        return board;
    }

    private void saveTestBoard(Board board, String path, String name) {
        // Save the board to a file using the specified path and name
        LoadSaveGame.saveBoard(board, path, name);
    }



    }



