package dk.dtu.compute.se.pisd.roborally.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import dk.dtu.compute.se.pisd.roborally.model.Board;
@RestController
@RequestMapping("/api/game")
public class SaveAndLoadController {

    // Unique board name: current timestamp
    String name_of_the_board = "board_" + System.currentTimeMillis();

    @Autowired
    ApiService apiService;

    @PostMapping("/save")
    public ResponseEntity<String> saveGame(@RequestBody Board board) {
        //path to save games on server (local atm)
        String pathToSaveGames = "/Users/andersjefsen/Desktop/testingRoboRally/SAVE GAMES";

        //using unique name for board
        String boardName = name_of_the_board;

        // Call to saveGame method in ApiService to save the board
        apiService.saveGame(board, pathToSaveGames, boardName);

        return ResponseEntity.ok().build(); //return 200 OK message
    }

    @GetMapping("/load/{boardName}")
    public ResponseEntity<Board> loadGame(@PathVariable String boardName) {
        //path to load game
        String pathToLoadGames = "/Users/andersjefsen/Desktop/testingRoboRally/LOAD GAMES";

        // Call to loadGame method in ApiService to save the board
        Board board = apiService.loadGame(pathToLoadGames, boardName);

        //returning the loaded board in the response
        return ResponseEntity.ok(board);
    }
}
