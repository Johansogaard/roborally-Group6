package dk.dtu.compute.se.pisd.roborally.api;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadSaveGame;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

  //Converts a Board object into a JSON
    public void saveGame(Board board, String path, String name) {
        LoadSaveGame.saveBoard(board, path, name);
    }

    //Converts a JSON file into a Board object.
    public Board loadGame(String path, String gameID) {
        return LoadSaveGame.loadBoard(path, gameID);
    }


}
