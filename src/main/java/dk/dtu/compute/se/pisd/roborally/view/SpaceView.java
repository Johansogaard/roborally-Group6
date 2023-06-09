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

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URISyntaxException;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class SpaceView extends StackPane implements ViewObserver {

    final public static int SPACE_HEIGHT = 60; // 75;
    final public static int SPACE_WIDTH = 60; // 75;

    public final Space space;


    public SpaceView(@NotNull Space space) {
        this.space = space;

        // XXX the following styling should better be done with styles
        this.setPrefWidth(SPACE_WIDTH);
        this.setMinWidth(SPACE_WIDTH);
        this.setMaxWidth(SPACE_WIDTH);

        this.setPrefHeight(SPACE_HEIGHT);
        this.setMinHeight(SPACE_HEIGHT);
        this.setMaxHeight(SPACE_HEIGHT);
/*
        if ((space.x + space.y) % 2 == 0) {
            this.setStyle("-fx-background-color: white;");
        } else {
            this.setStyle("-fx-background-color: black;");
        }*/


        // updatePlayer();

        // This space view should listen to changes of the space
        space.attach(this);
        update(space);
    }

    private void updatePlayer() {


        Player player = space.getPlayer();
        if (player != null) {
            Polygon arrow = new Polygon(0.0, 0.0,
                    10.0, 20.0,
                    20.0, 0.0 );
            try {
                arrow.setFill(Color.valueOf(player.getColor()));
            } catch (Exception e) {
                arrow.setFill(Color.MEDIUMPURPLE);
            }

            arrow.setRotate((90*player.getHeading().ordinal())%360);
            this.getChildren().add(arrow);
        }



    }
    private void updateBeltImage(ConveyorBelt belt, String imagePath) {
        if (belt != null) {
            switch (belt.getHeading()) {
                case EAST:
                    addImage(imagePath).setRotate(90);
                    break;
                case SOUTH:
                    addImage(imagePath).setRotate(180);
                    break;
                case WEST:
                    addImage(imagePath).setRotate(-90);
                    break;
                case NORTH:
                    addImage(imagePath);
            }
        }
    }

    private void updateBelt() {
        updateBeltImage(space.getConveyorBelt(), "images/greencon.png");
        updateBeltImage(space.getConveyorBelt2(), "images/bluecon.png");
        updateBeltImage(space.getConveyorBelt3(), "images/DDCB.png");
        updateBeltImage(space.getConveyorBelt4(), "images/SDCB.png");
    }

    private void updatePushPanel() {
        PushPanel pushpanel = space.getPushPanel();

        if (pushpanel != null) {
            switch (pushpanel.getHeading()) {

                case WEST:
                    addImage("images/pushpanel.png").setRotate(90);
                    break;

                case NORTH:
                    addImage("images/pushpanel.png").setRotate(180);
                    break;

                case EAST:
                    addImage("images/pushpanel.png").setRotate(-90);
                    break;

                case SOUTH:
                    addImage("images/pushpanel.png");

            }}}
    private void updateWalls(){


        Space space = this.space;
        if (space != null && !space.getWalls().isEmpty()) {
            for (Heading wall : space.getWalls()) {

                Polygon fig = new Polygon(0.0,0.0,
                        60.0,0.0,
                        60.0,10.0,
                        0.0,10.0);

                switch (wall) {
                    case EAST:
                        fig.setTranslateX((this.SPACE_HEIGHT/2)-1);
                        fig.setRotate((90*wall.ordinal()) % 360);
                        break;

                    case SOUTH:
                        fig.setTranslateY((this.SPACE_HEIGHT/2)-1);
                        break;

                    case WEST:
                        fig.setTranslateX(-(this.SPACE_HEIGHT/2)+1);
                        fig.setRotate((90*wall.ordinal()) % 360);
                        break;

                    case NORTH:
                        fig.setTranslateY(-(this.SPACE_HEIGHT/2)-1);
                        break;
                }

                fig.setFill(Color.ORANGERED);
                this.getChildren().add(fig);

            }


        }
    }
    private void updateActions()
    {
        for (FieldAction action : space.actions) {
            if (action instanceof Checkpoint) {
                addImage("images/checkpoint" + ((Checkpoint) action).no + ".png", -90);
            }

            if (action instanceof Pit) {
                addImage("images/pit.png");
            }

            if (action instanceof Gear) {
                addImage("images/gear" + (((Gear) action).direction) + ".png");
            }


        }
    }
    private ImageView addImage(String name) {
        Image img = null;
        try {
            File file = new File("./src/main/resources/" + name);
            img = new Image(file.toURI().toString());
        } catch (Exception e) {
            e.printStackTrace();}
        ImageView imgView = new ImageView(img);
        imgView.setImage(img);
        imgView.setFitHeight(SPACE_HEIGHT);
        imgView.setFitWidth(SPACE_WIDTH);

        imgView.setVisible(true);

        this.getChildren().add(imgView);

        return imgView;
    }

    private ImageView addImage(String name, double rotation) {
        ImageView imageView = addImage(name);
        imageView.setRotate(rotation);

        return imageView;
    }



    @Override
    public void updateView(Subject subject) {
        if (subject == this.space) {
            this.getChildren().clear();
            addImage("images/floor.png");
        if (space.board.getAntenna() !=null && (space.board.getAntenna().x == space.x&&space.board.getAntenna().y == space.y))
        {
           addImage("images/antenna.png");
        }
            if (space.board.getRebootToken() !=null && (space.board.getRebootToken().x == space.x&&space.board.getRebootToken().y == space.y))
            {
                addImage("images/RebootToken.png");
            }
        if(space.getStartPlayerNo() !=0)
        {
            addImage("images/startpoint.png");
        }
            updatePushPanel();
            updateBelt();
            updateActions();
            updatePlayer();
            updateWalls();
        }
    }

}
