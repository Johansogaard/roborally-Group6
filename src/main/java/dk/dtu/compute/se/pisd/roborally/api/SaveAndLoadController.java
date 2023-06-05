package dk.dtu.compute.se.pisd.roborally.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//RESTful API controller
@RestController

//used to map the web request given to the api
@RequestMapping("/api/game")
public class SaveAndLoadController {

    //defining the absolute path to the server:
    private static final String GAMES_FOLDER = "/Users/andersjefsen/Desktop/testingRoboRally/SAVED GAMES";

    //this maps the HTTP POST requests, also it expects JSON files.
    @PostMapping(value = "/{gameId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //saveGame method that takes gameID from the path varible and game objects from body.
    public void saveGame(@PathVariable String gameId, @RequestBody byte[] gameData) {
        try {
            //this writes the game data to a file named after the gameID
            Files.write(Paths.get(GAMES_FOLDER, gameId + ".json"), gameData);
        } catch (IOException e) {
            throw new RuntimeException("Could not save game", e);
        }
    }

    //This maps the HTTP GET requests, and it will only deliver JSON files.
    @GetMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    //loadGame from the path variable and returns a byte array
    public byte[] loadGame(@PathVariable String gameId) {
        try {
            //this reads all the bytes from the file into a byte array and retuyrns it
            return Files.readAllBytes(Paths.get(GAMES_FOLDER, gameId + ".json"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load game", e);
        }
    }
}
