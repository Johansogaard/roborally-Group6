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
package dk.dtu.compute.se.pisd.roborally.model;

import com.sun.javafx.scene.traversal.Direction;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import javafx.scene.control.skin.TextInputControlSkin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static dk.dtu.compute.se.pisd.roborally.model.Phase.INITIALISATION;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Board extends Subject {
    public Antenna getAntenna() {
        return antenna;
    }

    public void setAntenna(Antenna antenna) {
        this.antenna = antenna;

    }

    private Antenna antenna;

    public final int width;

    public final int height;

    public final String boardName;

    private Integer gameId;

    private final Space[][] spaces;

    private final List<Player> players = new ArrayList<>();

    public List<Player> getPlayerOrder() {
        return playerOrder;
    }

    public void setPlayerOrder(List<Player> playerOrder) {
        this.playerOrder = playerOrder;
    }

    private List<Player> playerOrder = new ArrayList<>();

    private List<Checkpoint> checkpoints = new ArrayList<Checkpoint>();
    private Player current;

    private Phase phase = INITIALISATION;

    private int step = 0;

    private boolean stepMode;
    public Space[][] getSpaces() {
        return spaces;
    }
    public Board(int width, int height, @NotNull String boardName) {
        this.boardName = boardName;
        this.width = width;
        this.height = height;

        spaces = new Space[width][height];
        for (int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                Space space = new Space(this, x, y);
                spaces[x][y] = space;
            }
        }
        this.stepMode = false;
    }

    public Board(int width, int height) {
        this(width, height, "defaultboard");
    }
    public List<Player> getPlayers() {
        return players;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        if (this.gameId == null) {
            this.gameId = gameId;
        } else {
            if (!this.gameId.equals(gameId)) {
                throw new IllegalStateException("A game with a set id may not be assigned a new id!");
            }
        }
    }
    public void setCheckpoint(Checkpoint checkpoint) {
        this.checkpoints.add(checkpoint);
    }
    public List<Checkpoint> getCheckpoints() {
        return this.checkpoints;
    }


    public Space getSpace(int x, int y) {
        if (x >= 0 && x < width &&
                y >= 0 && y < height) {
            return spaces[x][y];
        } else {
            return null;
        }
    }

    public int getPlayersNumber() {
        return players.size();
    }

    public void addPlayer(@NotNull Player player) {
        if (player.board == this && !players.contains(player)) {
            players.add(player);
            notifyChange();
        }
    }
    public void notifyBoardChange()
    {
        notifyChange();
        for (int i =0;i<width;i++)
        {
            for (int f=0;f<height;f++)
            {
                spaces[i][f].notifySpace();
            }
        }
        for (Player player: players)
        {
            player.notifyPlayer();
        }

    }



    public Player getPlayer(int i) {
        if (i >= 0 && i < players.size()) {
            for (Player player: players) {
                if (player.no == i)
                    return player;
            }
        }

        return null;

    }
    public Player getCurrentPlayer() {
        if (current == null)
        {
            return playerOrder.get(0);
        }
        return current;
    }

    public void setCurrentPlayer(Player player) {
        if (player != this.current && players.contains(player)) {
            this.current = player;
            notifyChange();
        }
    }

    public void testSetCurrentPlayer(Player player){
        this.current = player;
    }

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        if (phase != this.phase) {
            this.phase = phase;
            notifyChange();
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        if (step != this.step) {
            this.step = step;
            notifyChange();
        }
    }

    public boolean isStepMode() {
        return stepMode;
    }

    public void setStepMode(boolean stepMode) {
        if (stepMode != this.stepMode) {
            this.stepMode = stepMode;
            notifyChange();
        }
    }
    public int getOrderNumber(@NotNull Player player)
    {
        if (player.board ==this){
            if (playerOrder.size() ==0)
            {
                setPlayerOrder();
            }
            for (Player pl : playerOrder)
            {
                if (pl.getName().equals(player.getName()))
                {
                    return playerOrder.indexOf(pl);
                }

            }

        }
       return -1;
    }
    public int getPlayerNumber(@NotNull Player player) {
        if (player.board == this) {
            return players.indexOf(player);
        } else {
            return -1;
        }
    }

    /**
     * Returns the neighbour of the given space of the board in the given heading.
     * The neighbour is returned only, if it can be reached from the given space
     * (no walls or obstacles in either of the involved spaces); otherwise,
     * null will be returned.
     *
     * @param space the space for which the neighbour should be computed
     * @param heading the heading of the neighbour
     * @return the space in the given direction; null if there is no (reachable) neighbour
     */
    public Space getNeighbour(@NotNull Space space, @NotNull Heading heading) {
        int x = space.x;
        int y = space.y;
        switch (heading) {
            case SOUTH:
                y = (y + 1) % height;
                break;
            case WEST:
                x = (x + width - 1) % width;
                break;
            case NORTH:
                y = (y + height - 1) % height;
                break;
            case EAST:
                x = (x + 1) % width;
                break;
        }

        return getSpace(x, y);
    }

    public String getStatusMessage() {
        // this is actually a view aspect, but for making assignment V1 easy for
        // the students, this method gives a string representation of the current
        // status of the game

        // XXX: V2 changed the status so that it shows the phase, the player and the step
        return "Phase: " + getPhase().name() +
                ", Player = " + getCurrentPlayer().getName() +
                ", Step: " + getStep();
    }
    public void setPlayerOrder()
    {


        if (antenna != null) {
            HashMap<Player, Integer> map = new HashMap<>();

            for (int x = 0; x < width; x++) {
                for (int y = 0; y <height; y++) {
                    Player player = getSpace(x, y).getPlayer();
                    if (player != null) {

                        int length = Math.abs(antenna.x - x) + Math.abs(antenna.y - y);

                        map.put(player, length);

                    }
                }
            }
            // convert the map into a list of map entries
            List<Map.Entry<Player, Integer>> list = new LinkedList<>(map.entrySet());
            Collections.sort(list, new Comparator<Map.Entry<Player, Integer>>() {
                @Override
                public int compare(Map.Entry<Player, Integer> o1, Map.Entry<Player, Integer> o2) {
                    if (o1.getValue() == o2.getValue()) {
                        // The robots have the same distance to the antenna
                        if (o2.getKey().getSpace().y > antenna.y && o1.getKey().getSpace().y > antenna.y) {
                            // Both robots are above the antenna
                            return o1.getKey().getSpace().x - o2.getKey().getSpace().x;
                        }

                        if (o2.getKey().getSpace().y < antenna.y && o1.getKey().getSpace().y < antenna.y) {
                            // Both robots are below the antenna
                            return o2.getKey().getSpace().x - o1.getKey().getSpace().x;
                        }

                        if (o2.getKey().getSpace().y > antenna.y || o1.getKey().getSpace().y > antenna.y) {
                            // One of the robots are above the antenna
                            return o1.getKey().getSpace().x - o2.getKey().getSpace().x;
                        }


                    } else {
                        return o1.getValue() - o2.getValue();
                    }

                    return 0;
                }
            });

            for (int i = 0; i < list.size(); i++) {
                playerOrder.add(list.get(i).getKey());
            }
            setCurrentPlayer(playerOrder.get(0));



        }
        else {
            for (int i = 0; i < players.size(); i++) {
                playerOrder.add(players.get(i));
            }
            //if there is no antenna it will take player one as first player
            setCurrentPlayer(getPlayer(0));
        }
    }


}
