/*
 *  This file is part of the initial project provided for the
 *  course "Project in Software Development (02362)" held at
 *  DTU Compute at the Technical University of Denmark.
 *
 *  Copyright (C) 2019, 2020: Ekkart Kindler, ekki@dtu.dk
 *
 *  This software is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 *
 *  This project is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this project; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */
package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.apiAccess.Client;
import dk.dtu.compute.se.pisd.roborally.apiAccess.ClientController;
import dk.dtu.compute.se.pisd.roborally.fileaccess.LoadSaveGame;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Player;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");
    private String fileToOpen;
    final private RoboRally roboRally;
    final private String boardsPath = "src/main/resources/boards";
    final private String gamesPath = "src/main/resources/savedGames";
    final private String currGamePath = "src/main/resources/currGameInstance";
    final private String currGameFile = "currGame.json";
    private GameController gameController;
    private ClientController client = null;

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }
    public void playOnline()
    {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("","Join Game","Create Game");
        dialog.setTitle("Join or create game");
        dialog.setHeaderText("Select a option");
        Optional<String> result = dialog.showAndWait();
        if (result.get().equals("Create Game"))
        {
        client = new ClientController(new Client());
        newGame();
        }
        else if(result.get().equals("Join Game"))
        {
            client = new ClientController(new Client());
            TextInputDialog inputDialog = new TextInputDialog();
            inputDialog.setContentText("Write the id of the game you want to join");
            inputDialog.showAndWait();




            client.joinGame(Integer.parseInt(inputDialog.getResult()));
            client.getGameInstance();
            Board loadedBoard = LoadSaveGame.loadBoard(currGamePath,currGameFile);
            gameController = new GameController(loadedBoard,client);
            roboRally.createBoardView(gameController);
        }
        else
        {

        }

    }

    public void newGame() {
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(PLAYER_NUMBER_OPTIONS.get(0), PLAYER_NUMBER_OPTIONS);
        dialog.setTitle("Player number");
        dialog.setHeaderText("Select number of players");
        Optional<Integer> result = dialog.showAndWait();

        if (result.isPresent()) {
            if (gameController != null) {
                // The UI should not allow this, but in case this happens anyway.
                // give the user the option to save the game or abort this operation!
                if (!stopGame()) {
                    return;
                }
            }

            // XXX the board should eventually be created programmatically or loaded from a file
            //     here we just create an empty board with the required number of players.
            Board board = loadBoard();

            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                player.no=i;
                board.addPlayer(player);
                player.setSpawn();
            }
            if (client != null) {
                gameController = new GameController(board,client);
                gameController.client.createGame(gameController.board.getPlayers().size());
            }
            else
            {
                gameController = new GameController(board,null);
            }




            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            board.setCurrentPlayer(board.getPlayer(0));

            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
            if (client != null)
            {
                LoadSaveGame.saveBoard(gameController.board,currGamePath ,currGameFile);
            }
        }
    }

    public void designBoard(
    ){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setContentText("Vælg bredden af spillepladen");
        dialog.showAndWait();

        int width = Integer.parseInt(dialog.getResult());

        dialog.setContentText("Vælg højden af spillepladen");
        dialog.showAndWait();

        int height = Integer.parseInt(dialog.getResult());

        BoardDesignController controller = new BoardDesignController(width, height);

        roboRally.createDesignView(controller);

    }
    public void saveGame() {
        TextInputDialog td = new TextInputDialog("NewGameSave");
        td.setHeaderText("Enter a name for the saved game");
        Optional<String> result = td.showAndWait();

        if (result.isPresent()) {
            String fileName = result.get();
            LoadSaveGame.saveBoard(gameController.board,gamesPath ,fileName);

        }

    }

    public void showFilesToChoseFrom(String pathName)
    {

        File folder = new File(pathName);
        File[] listOfFiles = folder.listFiles();
        ArrayList<String> lF = new ArrayList<>();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                lF.add(file.getName());
            }
        }
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(lF);

        // Create a popup window
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Select a file");
        popupWindow.setMinWidth(250);

        // Create a layout for the popup window
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(new Label("Select a file:"), comboBox);

        // Create a button to close the popup window and return the selected file
        Button selectButton = new Button("Select");
        selectButton.setOnAction(e -> {
            popupWindow.close();
            String selectedFile = comboBox.getSelectionModel().getSelectedItem();
            // Do something with the selected file
            if (selectedFile==null)
            {

            }
            else {
                fileToOpen = selectedFile;
            }
        });

        // Add the button to the layout
        layout.getChildren().add(selectButton);

        // Create a scene for the popup window
        Scene scene = new Scene(layout);

        // Set the scene and show the popup window
        popupWindow.setScene(scene);
        popupWindow.showAndWait();



    }
    public Board loadBoard()
    {

        showFilesToChoseFrom(boardsPath);
        return LoadSaveGame.loadBoard(boardsPath,fileToOpen);
    }
    public void loadGame() {

            showFilesToChoseFrom(gamesPath);
                Board loadedBoard = LoadSaveGame.loadBoard(gamesPath,fileToOpen);

                gameController = new GameController(loadedBoard,null);


                roboRally.createBoardView(gameController);


    }

    /**
     * Stop playing the current game, giving the user the option to save
     * the game or to cancel stopping the game. The method returns true
     * if the game was successfully stopped (with or without saving the
     * game); returns false, if the current game was not stopped. In case
     * there is no current game, false is returned.
     *
     * @return true if the current game was stopped, false otherwise
     */
    public boolean stopGame() {
        if (gameController != null) {

            // here we save the game (without asking the user). i am not sure we want this JJ
            //saveGame();

            gameController = null;
            roboRally.createBoardView(null);
            return true;
        }
        return false;
    }

    public void exit() {
        if (gameController != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Exit RoboRally?");
            alert.setContentText("Are you sure you want to exit RoboRally?");
            Optional<ButtonType> result = alert.showAndWait();

            if (!result.isPresent() || result.get() != ButtonType.OK) {
                return; // return without exiting the application
            }
        }

        // If the user did not cancel, the RoboRally application will exit
        // after the option to save the game
        if (gameController == null || stopGame()) {
            Platform.exit();
        }
    }

    public boolean isGameRunning() {
        return gameController != null;
    }


    @Override
    public void update(Subject subject) {
        // XXX do nothing for now
    }

}
