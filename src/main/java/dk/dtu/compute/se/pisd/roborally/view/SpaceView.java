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
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.model.Wall;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineCap;
import org.jetbrains.annotations.NotNull;

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


        // add wall based on heading
        if (space.getWall() != null) {
            this.getChildren().clear();

            Wall wall = space.getWall();

            if (wall.getHeading() == Heading.NORTH) {
                Polygon northWall = new Polygon(
                        0, 0,
                        SPACE_WIDTH, 0,
                        SPACE_WIDTH, SPACE_HEIGHT / 4,
                        SPACE_WIDTH / 2, SPACE_HEIGHT / 2,
                        0, SPACE_HEIGHT / 4);
                northWall.setFill(Color.RED);
                this.getChildren().add(northWall);
            } else if (wall.getHeading() == Heading.SOUTH) {
                Polygon southWall = new Polygon(
                        0, SPACE_HEIGHT,
                        SPACE_WIDTH, SPACE_HEIGHT,
                        SPACE_WIDTH, SPACE_HEIGHT * 3 / 4,
                        SPACE_WIDTH / 2, SPACE_HEIGHT / 2,
                        0, SPACE_HEIGHT * 3 / 4);
                southWall.setFill(Color.RED);
                this.getChildren().add(southWall);
            } else if (wall.getHeading() == Heading.WEST) {
                Polygon westWall = new Polygon(
                        0, 0,
                        SPACE_WIDTH / 4, SPACE_HEIGHT / 2,
                        0, SPACE_HEIGHT,
                        SPACE_WIDTH / 4, SPACE_HEIGHT,
                        SPACE_WIDTH / 2, SPACE_HEIGHT / 2);
                westWall.setFill(Color.RED);
                this.getChildren().add(westWall);
            } else if (wall.getHeading() == Heading.EAST) {
                Polygon eastWall = new Polygon(
                        SPACE_WIDTH, 0,
                        SPACE_WIDTH * 3 / 4, SPACE_HEIGHT / 2,
                        SPACE_WIDTH, SPACE_HEIGHT,
                        SPACE_WIDTH * 3 / 4, SPACE_HEIGHT,
                        SPACE_WIDTH / 2, SPACE_HEIGHT / 2);
                eastWall.setFill(Color.RED);
                this.getChildren().add(eastWall);
            }
        }

            else if (space.isPit()) {
                this.setStyle("-fx-background-color: #654321; -fx-shape: \"M 30 20 m -20 0 a 20 10 0 1 0 40 0 a 20 10 0 1 0 -40 0 z\";");
            } else if ((space.x + space.y) % 2 == 0) {
                this.setStyle("-fx-background-color: white;");
            } else {
                this.setStyle("-fx-background-color: black;");
            }

            space.attach(this);
            update(space);

            //updatePlayer();


        }

        private void updatePlayer () {
            this.getChildren().clear();

            Player player = space.getPlayer();
            if (player != null) {
                Polygon arrow = new Polygon(0.0, 0.0,
                        10.0, 20.0,
                        20.0, 0.0);
                try {
                    arrow.setFill(Color.valueOf(player.getColor()));
                } catch (Exception e) {
                    arrow.setFill(Color.MEDIUMPURPLE);
                }

                arrow.setRotate((90 * player.getHeading().ordinal()) % 360);
                this.getChildren().add(arrow);
            }
        }

        @Override
        public void updateView (Subject subject){
            if (subject == this.space) {
                updatePlayer();
            }
        }

    }
