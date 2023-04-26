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
package dk.dtu.compute.se.pisd.roborally.fileaccess.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.fileaccess.Adapter;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class BoardTemplate {

    public int width;
    public int height;

    public int antennaX, antennaY;

    public List<Player> getPlayers() {
        return players;
    }

    private final List<Player> players = new ArrayList<>();
    public List<SpaceTemplate> spaces = new ArrayList<SpaceTemplate>();

    public BoardTemplate fromBoard(Board board) {
        this.width = board.width;
        this.height = board.height;

       /* if (board.getAntenna() != null) {
            this.antennaX = board.getAntenna().x;
            this.antennaY = board.getAntenna().y;
        }*/

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                if (!board.getSpace(i,j).getWalls().isEmpty() || !board.getSpace(i,j).getActions().isEmpty() || board.getSpace(i,j).getStartPlayerNo() != 0) {
                    // only convert the spaces that actually have some relevant data
                    spaces.add((new SpaceTemplate()).fromSpace(board.getSpace(i,j)));
                }
            }
        }

        return this;
    }

    public Board toBoard() {

        Board board = new Board(this.width, this.height);
        //Antenna antenna = new Antenna(board, this.antennaX, this.antennaY);
      //  board.setAntenna(antenna);

        for (SpaceTemplate spaceTemplate : spaces) {
            Space space = spaceTemplate.toSpace(board);
            board.getSpaces()[space.x][space.y] = space;
        }

        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
                if (board.getSpace(i,j) == null) {
                    // fill out the "empty" spaces
                    board.getSpaces()[i][j] = new Space(board,i,j);
                }
            }
        }

        return board;

    }

    @Override
    public String toString() {

        GsonBuilder builder = new GsonBuilder().registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();
        Gson gson = builder.create();


        return gson.toJson(this, this.getClass());

    }
}
