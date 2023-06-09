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
package dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dk.dtu.compute.se.pisd.roborally.fileaccess.Adapter;
import dk.dtu.compute.se.pisd.roborally.model.*;

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
    public int step;
    public Phase phase;

    //public int antennaX, antennaY;

    public List<PlayerTemplate> getPlayers() {
        return players;
    }

    public List<PlayerTemplate> players = new ArrayList<>();
    public List<SpaceTemplate> spaces = new ArrayList<>();
    public ArrayList<String> playerOrder = new ArrayList<>();
    private PlayerTemplate current;
    private AntennaTemplate antenna;
    private RebootTokenTemplate rebootToken;

    public BoardTemplate fromBoard(Board board) {
        this.width = board.width;
        this.height = board.height;
        this.step = board.getStep();
        this.phase = board.getPhase();
        if (board.getPlayers().size()>0) {
            for (Player player : board.getPlayerOrder()) {
                this.playerOrder.add(player.getName());
            }
            this.current = new PlayerTemplate().fromPlayer(board.getCurrentPlayer());

            for (Player player : board.getPlayers()) {
                this.players.add(new PlayerTemplate().fromPlayer(player));
            }
        }
        if (board.getAntenna()!=null)
        {
            this.antenna = new AntennaTemplate().fromAntenna(board.getAntenna());
        }
        if (board.getRebootToken() != null) {
            this.rebootToken = new RebootTokenTemplate().fromRebootToken(board.getRebootToken());
        }



        for (int i = 0; i < board.width; i++) {
            for (int j = 0; j < board.height; j++) {
              {
                  // only convert the spaces that actually have some relevant data
                  SpaceTemplate space = new SpaceTemplate().fromSpace(board.getSpace(i,j));
                  if (space.actions.size()>0||space.walls.size()>0||space.player!=null|| space.startPlayerNo >0)
                  {
                     spaces.add(space);
                  }


                }
            }
        }

        return this;
    }

    public Board toBoard() {

        Board board = new Board(this.width, this.height);
        board.setStep(this.step);
        board.setPhase(this.phase);

        //first adding the players but where they are missing space because they are not istantiated yet
        if (players.size()>0) {
            for (PlayerTemplate playerTemplate : players) {
                board.addPlayer(playerTemplate.toPlayer(board));
            }
            ArrayList<Player> plOrder = new ArrayList<>();
            for (String playerName : playerOrder)
            {
               plOrder.add(getPlayerFromName(board.getPlayers(),playerName));

            }
            board.setPlayerOrder(plOrder);
            board.setCurrentPlayer(board.getPlayer(current.no));

        }
        //Adding all the spaces
        for (int i = 0; i < spaces.size(); i++) {

            SpaceTemplate sp = spaces.get(i);
            if (sp.player != null) {
                board.getSpace(sp.x, sp.y).setPlayer(getPlayerFromName(board.getPlayers(),sp.player));
            }
            if (sp.walls.size() > 0) {
                for (int f = 0; f < sp.walls.size(); f++) {
                    board.getSpace(sp.x, sp.y).addWall(sp.walls.get(f));
                }
            }
            if (sp.actions.size() > 0) {
                for (int f = 0; f < sp.actions.size(); f++) {
                    board.getSpace(sp.x, sp.y).addAction(sp.actions.get(f));

                }
            }
            if (sp.startPlayerNo>0)
            {
                board.getSpace(sp.x,sp.y).setStartPlayerNo(sp.startPlayerNo);
            }


        }
        if (this.antenna != null) {
            board.setAntenna(this.antenna.toAntenna(board));
        }
        if (this.rebootToken != null) {
            board.setRebootToken(this.rebootToken.toRebootToken(board));
        }




        return board;

    }
    //Returns the player that has the name of the input from the given list
    public Player getPlayerFromName(List<Player> players, String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        GsonBuilder builder = new GsonBuilder().registerTypeAdapter(FieldAction.class, new Adapter<FieldAction>()).
                setPrettyPrinting();
        Gson gson = builder.create();


        return gson.toJson(this, this.getClass());
    }
}
