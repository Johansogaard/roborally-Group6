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

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Command.SPAM;
import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Player extends Subject {
    private int lastCheckpoint;
    final public static int NO_REGISTERS = 5;
    final public static int NO_CARDS = 8;

    final public Board board;
    public int no;
    private Space spawnPoint;
    private String name;
    private String color;
    public Deck deck;
    public Deck discardpile;
    private Space space;
    private Heading heading = SOUTH;
    public boolean reboot=false;
    public CommandCardField[] getProgram() {
        return program;
    }

    public void setProgram(CommandCardField[] program) {
        this.program = program;
    }

    private CommandCardField[] program;

    public CommandCardField[] getCards() {
        return cards;
    }

    public void setCards(CommandCardField[] cards) {
        this.cards = cards;
    }

    private CommandCardField[] cards;

    public Player(@NotNull Board board, String color, @NotNull String name) {
        this.board = board;
        this.name = name;
        this.color = color;
        this.deck= new Deck();
        this.space = null;

        program = new CommandCardField[NO_REGISTERS];
        for (int i = 0; i < program.length; i++) {
            program[i] = new CommandCardField(this);
        }

        cards = new CommandCardField[NO_CARDS];
        for (int i = 0; i < cards.length; i++) {
            cards[i] = new CommandCardField(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null && !name.equals(this.name)) {
            this.name = name;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }
    public void notifyPlayer()
    {
        notifyChange();
        for (CommandCardField commandCardField: program)
        {
            commandCardField.notifyCommandCardField();
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        notifyChange();
        if (space != null) {
            space.playerChanged();
        }
    }


    public Space getSpace() {
        return space;
    }
    public void setSpawn() {
        if (spawnPoint == null) {
            for (int i = 0; i < board.height; i++) {
                for (int f = 0; f < board.width; f++) {
                    Space space = board.getSpace(i, f);
                    if (space != null && space.getStartPlayerNo() == this.no + 1) {
                        spawnPoint = space;
                        setSpace(spawnPoint);
                        break;
                    }
                }
            }
        } else {
            setSpace(spawnPoint);
        }
    }
    public void setSpace(Space space) {
        Space oldSpace = this.space;
        if (space != oldSpace &&
                (space == null || space.board == this.board)) {
            this.space = space;
            if (oldSpace != null) {
                oldSpace.setPlayer(null);
            }
            if (space != null) {
                space.setPlayer(this);
            }
            notifyChange();
        }
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(@NotNull Heading heading) {
        if (heading != this.heading) {
            this.heading = heading;
            notifyChange();
            if (space != null) {
                space.playerChanged();
            }
        }
    }

    public CommandCardField getProgramField(int i) {
        return program[i];
    }

    public CommandCardField getCardField(int i) {
        return cards[i];
    }

    /**
     * Sets the last chekpoint when called
     * @param lastCheckpoint
     */
    public void setLastCheckpoint(int lastCheckpoint) {
        // we only update this if the new checkpoint number is higher than the one the player already has
        if (lastCheckpoint == (this.lastCheckpoint + 1)) {
            this.lastCheckpoint = lastCheckpoint;
            notifyChange();
        }
    }
    public int getLastCheckpoint() {
        return lastCheckpoint;
    }


    public void shootLaser(Space space, GameController gameController){
        while (space != null && space.getPlayer() == null && !space.getWalls().contains(heading)
                && space!=gameController.board.getSpace(gameController.board.getAntenna().x,gameController.board.getAntenna().y)
                && !gameController.board.getNeighbour(space,heading).getWalls().contains(heading.opposite())) {
            space = gameController.board.getNeighbour(space, heading);}

        if (space.getPlayer()!=null){space.getPlayer().deck.addCard(new CommandCard(SPAM));}}
    public void preboot() {
        // Reset the program cards to null

        Space tokenLokation =board.getSpace((board.getRebootToken().x),(board.getRebootToken().y));
        reboot=true;
        deck.addCard( new CommandCard(Command.SPAM));

        setHeading(Heading.WEST);

        if((tokenLokation.getPlayer()!=null)){
            tokenLokation.getPlayer().moveForward();}

        this.setSpace(tokenLokation);


        // Notify observers of the change
        notifyChange();
    }

    /**
     *
     * @author Johan Søgaard Jørgensen(JJ)
     * This is a method to move the player Forward
     * It cheks if there is a wall in the heading direction
     * It cheks if there is a person infront and calls the move pushPlayer to push the player infront
     * It wait to move until the pushplayer method has returned if there is a wall infront of the players that the robot is going to push
     */
    // TODO: V2


        public void moveForward() {
            pushPlayer(this.heading);


                    // If there's no wall or player blocking, then move forward


                }



    /**
     * pushPlayer pushes the player infront and pushes x numbers of player who is infront of him
     * but always waits to see if the player infront has a wall that way we dont push and stand still if there is a wall infront of x robot
     *
     * @param heading        this is the direction of the push
     * @param
     */
    public boolean pushPlayer(Heading heading) {
        Space space = getSpace();
        Space target = board.getNeighbour(space, heading);
        boolean isWall = false;

        if (space.getWalls().contains(heading)) {return true;}
        if(target==null) {
            preboot();
            return false;
        }
        if (target.getWalls().contains(heading.opposite())|| target==board.getSpace(board.getAntenna().x,board.getAntenna().y)){return true;}
        // Check if there's a wall or a player in front of the next space
        if (target.getPlayer()!=null) {
            isWall = target.getPlayer().pushPlayer(heading);
        }
        // Only push the player if there's no wall
        if (!isWall) {
            setSpace(target);
        }

        return isWall;
    }
}
