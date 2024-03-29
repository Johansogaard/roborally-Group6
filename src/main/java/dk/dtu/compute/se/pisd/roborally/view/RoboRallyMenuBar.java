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
package dk.dtu.compute.se.pisd.roborally.view;

import dk.dtu.compute.se.pisd.roborally.controller.AppController;
import dk.dtu.compute.se.pisd.roborally.controller.BoardDesignController;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.logging.Logger;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class RoboRallyMenuBar extends MenuBar {


    private final MenuItem saveGameToServer;


    private final AppController appController;


    private final Menu controlMenu;

    private final MenuItem saveGame;

    private final MenuItem newGame;

    private final MenuItem loadGame;
    private final MenuItem playOnline;
    private final MenuItem stopGame;

    private final MenuItem createABoard;

    private final MenuItem exitApp;

    public RoboRallyMenuBar(AppController appController) {
        this.appController = appController;

        controlMenu = new Menu("Menu");
        this.getMenus().add(controlMenu);

        newGame = new MenuItem("New game");
        newGame.setOnAction(e -> this.appController.newGame());
        controlMenu.getItems().add(newGame);

        stopGame = new MenuItem("Stop Game");
        stopGame.setOnAction(e -> this.appController.stopGame());
        controlMenu.getItems().add(stopGame);
        playOnline = new MenuItem("Play Online");
        playOnline.setOnAction(e -> this.appController.playOnline());
        controlMenu.getItems().add(playOnline);

        saveGame = new MenuItem("Save game");
        saveGame.setOnAction(e -> this.appController.saveGame());
        controlMenu.getItems().add(saveGame);

        saveGameToServer = new MenuItem("Save game to server");
        saveGameToServer.setOnAction(e -> this.appController.saveGameOnServer());
        controlMenu.getItems().add(saveGameToServer);


        loadGame = new MenuItem("Load game");
        loadGame.setOnAction(e -> this.appController.loadGame());
        controlMenu.getItems().add(loadGame);

        createABoard = new MenuItem("Create board");
        createABoard.setOnAction(event -> this.appController.designBoard());
        controlMenu.getItems().add(createABoard);

        exitApp = new MenuItem("Exit");
        exitApp.setOnAction(e -> this.appController.exit());
        controlMenu.getItems().add(exitApp);

        controlMenu.setOnShowing(e -> update());
        controlMenu.setOnShown(e -> this.updateBounds());
        update();
    }

    public void update() {
        if (appController.isGameRunning()) {
            newGame.setVisible(false);
            stopGame.setVisible(true);
            saveGame.setVisible(true);
            loadGame.setVisible(false);
            playOnline.setVisible(false);
            saveGameToServer.setVisible(true);
            createABoard.setVisible(false);

        } else {
            newGame.setVisible(true);
            stopGame.setVisible(false);
            playOnline.setVisible(true);
            saveGame.setVisible(false);
            loadGame.setVisible(true);
            saveGameToServer.setVisible(false);
            createABoard.setVisible(true);


        }
    }

}
