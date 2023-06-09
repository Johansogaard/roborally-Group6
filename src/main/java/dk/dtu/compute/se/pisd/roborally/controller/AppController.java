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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

import dk.dtu.compute.se.pisd.roborally.RoboRally;

import dk.dtu.compute.se.pisd.roborally.fileaccess.Adapter;
import dk.dtu.compute.se.pisd.roborally.fileaccess.*;
import dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates.BoardTemplate;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;
/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class AppController implements Observer {
    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    private static final String BASE_URL = "http://localhost:8084/api/game";

    final private List<Integer> PLAYER_NUMBER_OPTIONS = Arrays.asList(2, 3, 4, 5, 6);
    final private List<String> PLAYER_COLORS = Arrays.asList("red", "green", "blue", "orange", "grey", "magenta");
    private String fileToOpen;
    final private RoboRally roboRally;
    final private String boardsPath = "src/main/resources/boards";
    final private String gamesPath = "src/main/resources/savedGames";
    private GameController gameController;

    private static final String GAMES_FOLDER = "/Users/victor/Desktop/testingRoboRally/SAVED GAMES";
    private static final String JSON_EXT = "json";
    private final RestTemplate restTemplate = new RestTemplate();

    public AppController(@NotNull RoboRally roboRally) {
        this.roboRally = roboRally;
    }

    public void saveGameToServer() {
        // Creating a dialog box for user input
        TextInputDialog td = new TextInputDialog("NewGameSave");
        td.setHeaderText("Enter a name for the saved game");
        Optional<String> result = td.showAndWait();

        // If user has entered a name
        if (result.isPresent()) {
            // Get the entered name and create a file path with it
            String fileName = result.get();
            String filePath = GAMES_FOLDER + "/" + fileName + "." + JSON_EXT;
            // Convert the current game board to a template
            BoardTemplate template = new BoardTemplate().fromBoard(gameController.board);

            // Set up Gson with custom adapter and pretty printing
            GsonBuilder gsonBuilder = new GsonBuilder()
                    .registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>())
                    .setPrettyPrinting();
            Gson gson = gsonBuilder.create();

            // Convert the board template to a JSON string
            String jsonData = gson.toJson(template, template.getClass());

            // Try to write the JSON data to a local file
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(jsonData);
                logger.info("Successfully saved the board locally: {}", fileName);
            } catch (IOException e) {
                logger.error("Error saving board locally: {}", fileName, e);
                throw new RuntimeException("Could not save game locally", e);
            }
            //Sends the game to the server
            sendToServer(fileName);
        }
    }

    private void sendToServer(String fileName) {
        // Remove the extension from the filename to get the game ID
        String gameId = fileName.substring(0, fileName.lastIndexOf('.'));

        // Create the URL to POST to
        String apiUrl = BASE_URL + gameId;

        // Attempt to read the local game file and send it to the server
        try {
            // Read the entire file into a byte array
            byte[] gameData = Files.readAllBytes(Paths.get(GAMES_FOLDER, fileName));

            // Set up HTTP headers to indicate we're sending JSON data
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Set up an HTTP request entity with our game data and headers
            HttpEntity<byte[]> requestEntity = new HttpEntity<>(gameData, headers);

            // Send the request to the server
            ResponseEntity<Void> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, requestEntity, Void.class);

            // If the server responded with HTTP 200 OK, it was successful

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                logger.info("Game data sent successfully to the server for gameId: {}", gameId);
            } else {
                logger.error("Error occurred while sending game data to the server for gameId: {}", gameId);
                throw new RuntimeException("Could not send game data to the server");
            }
        } catch (IOException e) {
            logger.error("Error occurred while reading game data locally: {}", fileName, e);
            throw new RuntimeException("Could not read game data locally", e);
        }
    }
    public void loadGameFromServer() {
        try {
            System.out.println("Showing files to choose from...");
            showFilesToChoseFrom(GAMES_FOLDER);

            System.out.println("Loading board...");
            Board loadedBoard = LoadSaveGame.loadBoard(GAMES_FOLDER, fileToOpen);

            System.out.println("Creating GameController...");
            gameController = new GameController(loadedBoard);

            System.out.println("Creating BoardView...");
            roboRally.createBoardView(gameController);

            System.out.println("Game loaded successfully from server");
        } catch (Exception e) {
            System.out.println("An error occurred:");
            e.printStackTrace();
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

            gameController = new GameController(board);

            int no = result.get();
            for (int i = 0; i < no; i++) {
                Player player = new Player(board, PLAYER_COLORS.get(i), "Player " + (i + 1));
                player.no=i;
                board.addPlayer(player);
                player.setSpawn();
            }


            // XXX: V2
            // board.setCurrentPlayer(board.getPlayer(0));
            board.setCurrentPlayer(board.getPlayer(0));
            gameController.startProgrammingPhase();

            roboRally.createBoardView(gameController);
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

                gameController = new GameController(loadedBoard);


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
