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

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Space extends Subject {
    public int getStartPlayerNo() {
        return startPlayerNo;
    }

    private int startPlayerNo;
    public final Board board;
    public List<FieldAction> actions = new ArrayList<>();
    public final int x;
    public final int y;

    private Player player;
    private Lasers lasers;

    public Lasers getLasers() {
        return lasers;
    }


    public List<Heading> getWalls() {
        return walls;
    }
    private Antenna antenna;
    private List<Heading> walls = new ArrayList<>();

    public Space(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        player = null;
    }
    public void addWall(Heading heading) {
        if ( ! walls.contains(heading)) {
            walls.add(heading);
            notifyChange();
        }
        }
        public void addAntenna()
        {
            board.setAntenna(new Antenna(board,this.x,this.y));
            notifyChange();
        }
    public void addRebootToken() {
        board.setRebootToken(new RebootToken(board, x, y));
        notifyChange();
    }



    public void addGear(Direction direction) {
        boolean check = false;

        for (FieldAction action : actions) {
            if (action instanceof Gear) {
                check = true;
            }
        }



        if (!check) {
            this.actions.add(new Gear(direction));
            notifyChange();
        }
    }
    public void addPushPanel(Direction direction) {
        boolean check = false;

        for (FieldAction action : actions) {
            if (action instanceof Gear) {
                check = true;
            }
        }



        if (!check) {
            this.actions.add(new Gear(direction));
            notifyChange();
        }
    }
    public List<FieldAction> getActions() {
        return actions;
    }

    public void addPit() {
        boolean check = false;

        for (FieldAction action : actions) {
            if (action instanceof Pit) {
                check = true;
            }
        }
        if (!check) {
            this.actions.add(new Pit());
            notifyChange();

    }}
    public Player getPlayer() {
        return player;
    }

    public int checkNeighborBelt(ConveyorBelt belt) {


        ConveyorBelt rightBelt = board.getNeighbour(this, belt.getHeading().prev()).getAction(ConveyorBelt.class);
        ConveyorBelt leftBelt = board.getNeighbour(this, belt.getHeading().next()).getAction(ConveyorBelt.class);

        boolean leftbelttrue=false;
        boolean rightbelttrue=false;

if(leftBelt!=null){
    leftbelttrue = leftBelt.getHeading()==belt.getHeading().prev()||leftBelt.getHeading()==belt.getHeading().next();
    System.out.println(leftBelt.getHeading());
    System.out.println(belt.getHeading().prev());
    System.out.println(belt.getHeading().next());
}

        if(rightBelt!=null){
            rightbelttrue = rightBelt.getHeading()==belt.getHeading().next()||leftBelt.getHeading()==belt.getHeading().prev();
        }

        if (leftbelttrue && rightbelttrue) {
            return 3; // Both neighbors have conveyor belts
        } else if (leftbelttrue) {
            return 1; // Only the previous neighbor has a conveyor belt
        } else if (rightbelttrue) {
            return 2; // Only the next neighbor has a conveyor belt
        } else {
            return 0; // No neighbors have conveyor belts
        }
    }
    public <T extends FieldAction> T getAction(Class<T> type) {
        for (FieldAction action : this.actions) {
            if (type.isInstance(action)) {
                return type.cast(action);
            }
        }
        return null;
    }

    public void setStartPlayerNo(int startPlayerNo) {
        this.startPlayerNo = startPlayerNo;
        notifyChange();
    }
    public void setPlayer(Player player) {

        Player oldPlayer = this.player;
        if (player != oldPlayer &&
                (player == null || board == player.board)) {
            this.player = player;
            if (oldPlayer != null) {
                // this should actually not happen
                oldPlayer.setSpace(null);
            }
            if (player != null) {
                player.setSpace(this);
            }
            if(player!=null){
            for (FieldAction fieldaction : player.getSpace().getActions()) {
                if (fieldaction instanceof Pit) {
                    player.preboot();
                }}}
            notifyChange();
        }
    }
    public void addAction(FieldAction action) {
        this.actions.add(action);

        if (action instanceof Checkpoint) {
            this.board.setCheckpoint((Checkpoint) action);
        }

        notifyChange();
    }

    void playerChanged() {
        // This is a minor hack; since some views that are registered with the space
        // also need to update when some player attributes change, the player can
        // notify the space of these changes by calling this method.
        notifyChange();
    }

}
