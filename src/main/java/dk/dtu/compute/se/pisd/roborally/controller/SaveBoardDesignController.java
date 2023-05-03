package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadSaveGame;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class SaveBoardDesignController {
public static void saveBoardDesign(Board board)
{
    TextInputDialog td = new TextInputDialog("NewBoardSave");
    td.setHeaderText("Enter a name for the saved board");
    Optional<String> result = td.showAndWait();

    if (result.isPresent()) {
        String fileName = result.get();
        LoadSaveGame.saveBoard(board,"src/main/resources/boards", fileName);
    }



}
}

