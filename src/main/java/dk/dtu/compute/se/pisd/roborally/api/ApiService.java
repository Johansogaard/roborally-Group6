package dk.dtu.compute.se.pisd.roborally.api;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadSaveGame;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    //Converts a Board object into a JSON
    public void saveGame(BoardTemplate boardTemplate, String path, String name) {
        Board board=boardTemplate.toBoard();
        LoadSaveGame.saveBoard(board, path, name);
    }

    //Converts a JSON file into a Board object.
    public BoardTemplate loadGame(String path, String gameID) {
        Board board = LoadSaveGame.loadBoard(path,gameID);
        return new BoardTemplate().fromBoard(board);
    }


}
