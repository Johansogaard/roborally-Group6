package dk.dtu.compute.se.pisd.roborally.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import dk.dtu.compute.se.pisd.roborally.model.Board;

//class is a RESTful controller
@RestController
//sets the base URI path for all request mappings within the controller class
@RequestMapping("/api/game")
public class SaveAndLoadController {

    // Unique board name: current timestamp
    String name_of_the_board = "board_" + System.currentTimeMillis();


    //ensures that class has access to ApiService
    @Autowired
    ApiService apiService;

    //method handles HTTP POST requests to the "/save" endpoint
    @PostMapping("/save")
    public ResponseEntity<String> saveGame(@RequestBody Board board) {
        //path to save games on server (local atm)
        String gameFolder = "/Users/andersjefsen/Desktop/testingRoboRally/SAVED GAMES";

        //using unique name for board
        String boardName = name_of_the_board;

        // Call to saveGame method in ApiService to save the board
        apiService.saveGame(board, gameFolder, boardName);

        //return 200 OK message
        return ResponseEntity.ok().build();
    }

    //method handles HTTP GET requests
    @GetMapping("/load/{boardName}")
    public ResponseEntity<Board> loadGame(@PathVariable String boardName) {
        //path to load game
        String gameFolder = "/Users/andersjefsen/Desktop/testingRoboRally/SAVED GAMES";

        // Call to loadGame method in ApiService to save the board
        Board board = apiService.loadGame(gameFolder, boardName);

        //returning the loaded board in the response
        return ResponseEntity.ok(board);
    }
}
