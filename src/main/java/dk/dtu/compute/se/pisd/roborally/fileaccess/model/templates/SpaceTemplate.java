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

import dk.dtu.compute.se.pisd.roborally.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 */
public class SpaceTemplate {


    public String player;
    public int startPlayerNo;
    public int x;
    public int y;
    public List<Heading> walls = new ArrayList<>();
    public List<FieldAction> actions = new ArrayList<>();

    public SpaceTemplate fromSpace(Space space) {
        this.x = space.x;
        this.y = space.y;
        if (space.getPlayer() != null) {
            this.player = space.getPlayer().getName();
        }
        this.startPlayerNo = space.getStartPlayerNo();
        this.walls = space.getWalls();
        this.actions = space.getActions();


        return this;
    }

    public Space toSpace(Board board) {

        Space space = new Space(board, this.x, this.y);
        //  space.setPlayer(player.toPlayer(board));

        for (FieldAction action : actions) {
            space.addAction(action);
        }

        for (Heading wall : walls) {
            space.addWall(wall);
        }
        space.setStartPlayerNo(this.startPlayerNo);
        return space;

    }

}
