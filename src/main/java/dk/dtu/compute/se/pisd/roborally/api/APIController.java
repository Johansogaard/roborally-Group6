package dk.dtu.compute.se.pisd.roborally.api;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static dk.dtu.compute.se.pisd.roborally.fileaccess.LoadSaveGame.loadBoard;

@RestController
@RequestMapping("/api/game")
public class APIController {


    @PostMapping("/{gameId}")
    public ResponseEntity<Void> saveGame(@PathVariable String gameId, @RequestBody String gameData) {
        // Save the game data to the "savedGames" folder on the server
        String folderPath = "/Users/andersjefsen/Desktop/testingRoboRally/SAVED GAMES"; // Specify the folder path where the game data should be stored
        String fileName = gameId + ".json"; // Construct the file name using the gameId

        try {
            // Create the folder if it doesn't exist
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Write the game data to a file
            String filePath = folderPath + "/" + fileName;
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(gameData);
            fileWriter.close();

            return ResponseEntity.status(HttpStatus.CREATED).build(); // Return HTTP 201 Created for successful save
        } catch (IOException e) {
            // Handle any error that occurs during file writing
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return HTTP 500 Internal Server Error for error during save
        }
    }


    @GetMapping("/{gameId}")
    public ResponseEntity<Board> loadGame(@PathVariable String gameId) {
        String folderPath = "/Users/andersjefsen/Desktop/testingRoboRally/SAVED GAMES";
        String fileName = gameId + ".json";

        try {
            File gameFile = new File(folderPath + "/" + fileName);

            if (gameFile.exists()) {
                // Read the game data from the file
                String gameData = new String(Files.readAllBytes(gameFile.toPath()), StandardCharsets.UTF_8);

                // Convert JSON to Board
                Board board = loadBoard(folderPath, gameData);

                // Return HTTP 200 OK and the game data
                return ResponseEntity.ok(board);
            } else {
                // If the file does not exist, return HTTP 404 Not Found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (IOException e) {
            // Handle any error that occurs during file reading
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return HTTP 500 Internal Server Error for error during read
        }
    }

}


