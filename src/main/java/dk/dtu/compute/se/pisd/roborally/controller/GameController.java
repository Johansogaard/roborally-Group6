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

import dk.dtu.compute.se.pisd.roborally.apiAccess.ClientController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * ...
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class GameController {

    final public Board board;
    final public ClientController client;
    public boolean won = false;
    /**
     * GameController is a controller for
     * @param board is the gameboard in use
     */
    public GameController(@NotNull Board board,ClientController client) {
        this.board = board;
        this.client= client;
    }

    /**
     * This is just some dummy controller operation to make a simple move to see something
     * happening on the board. This method should eventually be deleted!
     *
     * @param space the space to which the current player should move
     */
    public void moveCurrentPlayerToSpace(@NotNull Space space)  {
        // TODO Assignment V1: method should be implemented by the students:
        //   - the current player should be moved to the given space
        //     (if it is free()
        //   - and the current player should be set to the player
        //     following the current player
        //   - the counter of moves in the game should be increased by one
        //     if the player is moved

        if (space != null && space.board == board) {
            Player currentPlayer = board.getCurrentPlayer();
            if (currentPlayer != null && space.getPlayer() == null) {
                currentPlayer.setSpace(space);
                int playerNumber = (board.getPlayerNumber(currentPlayer) + 1) % board.getPlayersNumber();
                board.setCurrentPlayer(board.getPlayer(playerNumber));
            }
        }

    }

    // XXX: V2
    public void startProgrammingPhase() {

        board.setPhase(Phase.PROGRAMMING);
        board.setStep(0);

        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            if (player != null) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    CommandCardField field = player.getProgramField(j);
                    field.setCard(null);
                    field.setVisible(true);
                }
                for (int j = 0; j < Player.NO_CARDS; j++) {
                    CommandCardField field = player.getCardField(j);
                    field.setCard(generateRandomCommandCard());
                    field.setVisible(true);
                }
            }
        }
    }
    public void fillEmptyRegister() {
        List<Player> players=board.getPlayers();
        for (int i = 0; i < players.size(); i++) {
                for (int j = 0; j < Player.NO_REGISTERS; j++) {
                    if (players.get(i).getProgramField(j).getCard() == null) {
                        players.get(i).getProgramField(j).setCard(generateRandomCommandCard());
                    }
                }
            }
        }



    // XXX: V2
    private CommandCard generateRandomCommandCard() {
        Command[] commands = Command.values();
        int random = (int) (Math.random() * commands.length);
        return new CommandCard(commands[random]);
    }

    // XXX: V2
    public void finishProgrammingPhase() {
        makeProgramFieldsInvisible();
        makeProgramFieldsVisible(0);
        board.setPhase(Phase.ACTIVATION);
        board.setPlayerOrder();
        board.setStep(0);
        fillEmptyRegister();
    }

    // XXX: V2
    private void makeProgramFieldsVisible(int register) {
        if (client == null) {
            if (register >= 0 && register < Player.NO_REGISTERS) {
                for (int i = 0; i < board.getPlayersNumber(); i++) {
                    Player player = board.getPlayer(i);
                    CommandCardField field = player.getProgramField(register);
                    field.setVisible(true);
                }
            }
        }
        else
        {

        }
    }

    // XXX: V2
    private void makeProgramFieldsInvisible() {
        for (int i = 0; i < board.getPlayersNumber(); i++) {
            Player player = board.getPlayer(i);
            for (int j = 0; j < Player.NO_REGISTERS; j++) {
                CommandCardField field = player.getProgramField(j);
                field.setVisible(false);
            }
        }
    }

    // XXX: V2
    public void executePrograms() {
        board.setStepMode(false);
        continuePrograms();
    }

    // XXX: V2
    public void executeStep() {
        board.setStepMode(true);
        continuePrograms();
    }

    // XXX: V2
    private void continuePrograms() {
        do {
            executeNextStep();
        } while (board.getPhase() == Phase.ACTIVATION && !board.isStepMode());
    }

    // XXX: V2
    private void executeNextStep() {
        //gets the curr player

        Player currentPlayer = board.getCurrentPlayer();

        //cheks if the phase is activation and the curr player is not null
        if (board.getPhase() == Phase.ACTIVATION && currentPlayer != null) {
            //the curr register
            int step = board.getStep();
            //checks if the step in correct and not a unusable value
            if (step >= 0 && step < Player.NO_REGISTERS) {
                //gets the curr card
                CommandCard card = currentPlayer.getProgramField(step).getCard();

                //checks if card is something
                if (card != null) {
                    //gets the command
                    Command command = card.command;

                    //if the command is Interactive then the phase must be changed
                    if (card.command.isInteractive()) {
                        board.setPhase(Phase.PLAYER_INTERACTION);
                    } else {
                        //else it will executecommand
                        executeCommand(currentPlayer, command);

                        //setting the next player;
                        //sout i used for debugging remove later
                        System.out.println(board.getOrderNumber(currentPlayer) +" "+ board.getPlayersNumber());
                        if (board.getOrderNumber(currentPlayer)+1 < board.getPlayersNumber()) {
                            board.setCurrentPlayer(board.getPlayerOrder().get((board.getOrderNumber(currentPlayer)+1)%(board.getPlayers().size())));
                        } else {
                            //adds a step because we now have been through all the players
                            step++;

                            //we run doaction on all fields because now all players have done the current register
                            for(Player player : this.board.getPlayers()){
                                for (FieldAction action : player.getSpace().getActions()) {
                                    if (won) {
                                        break;
                                    }
                                    action.doAction(this, player.getSpace());
                                }
                            }

                            //checks if we have more steps than registers if thats the case we will start the programming phase
                            if (step < Player.NO_REGISTERS) {
                                makeProgramFieldsVisible(step);
                                board.setStep(step);
                                board.setCurrentPlayer(board.getPlayerOrder().get((board.getOrderNumber(currentPlayer)+1)%(board.getPlayers().size())));
                            } else {

                                startProgrammingPhase();
                            }
                        }
                    }
                }
            }else {
                // this should not happen
                assert false;
            }
        } else {
            // this should not happen
            assert false;
        }
    }

    // XXX: V2
    public void executeCommand(@NotNull Player player, Command command) {
        if (player != null && player.board == board && command != null) {
            // XXX This is a very simplistic way of dealing with some basic cards and
            //     their execution. This should eventually be done in a more elegant way
            //     (this concerns the way cards are modelled as well as the way they are executed).

            switch (command) {
                case FORWARD:
                    this.moveForward(player);
                    break;
                case RIGHT:
                    this.turnRight(player);
                    break;
                case LEFT:
                    this.turnLeft(player);
                    break;
                case FASTFORWARD:
                    this.fastForward(player);
                    break;
                case Move_3:
                    this.Move3(player);
                    break;
                case uTurn:
                    this.uTurn(player);
                    break;
                case BACK:
                    this.Back(player);
                    break;
                case AGAIN:
                    this.again(player, board.getStep());
                    break;
                default:
                    // DO NOTHING (for now)
            }
        }
    }

    private void again(Player player, int currentStep) {

        int previousStep = (currentStep - 1 + Player.NO_REGISTERS) % Player.NO_REGISTERS;
        CommandCard previousCard = player.getProgramField(previousStep).getCard();

        if (previousCard != null && previousCard.command == Command.AGAIN) {
            again(player, currentStep-1);

        } else if (previousCard != null) {
            Command previousCommand = previousCard.command;
            executeCommand(player, previousCommand);
        }
    }

    private void Back(Player player) {
        uTurn(player);
        moveForward(player);
        uTurn(player);
    }

    private void uTurn(Player player) {
        turnLeft(player);
        turnLeft(player);
    }

    private void Move3(Player player) {
        for (int i = 0 ; i<3;i++) {
            moveForward(player);
        }
    }

    /**
     * @author Johan Søgaard Jørgensen(JJ)
     * This is a method to move the player Forward
     * It cheks if there is a wall in the heading direction
     * It cheks if there is a person infront and calls the move pushPlayer to push the player infront
     * It wait to move until the pushplayer method has returned if there is a wall infront of the players that the robot is going to push
     * @param player is the current player that is going to move foward
     */
    // TODO: V2
    public void moveForward(@NotNull Player player) {
        Space space = player.getSpace();
        if (player != null && player.board == board && space != null) {
            Heading heading = player.getHeading();
            Space target = board.getNeighbour(space, heading);
            if (target != null ) {
                boolean isWall =false;

                // XXX note that this removes an other player from the space, when there
                //     is another player on the target. Eventually, this needs to be
                //     implemented in a way so that other players are pushed away!

                //JJ added a loop that cheks if there is a wall infront of the robot in the traveling direction
                if (space.getWalls().contains(heading))
                {
                    isWall=true;
                }
                if (target.getPlayer()!=null&& isWall!=true)
                {
                   isWall = pushPlayer(player,heading);
                }
                if (isWall!=true) {
                    target.setPlayer(player);
                }

            }

        }
    }

    /**
     * pushPlayer pushes the player infront and pushes x numbers of player who is infront of him
     * but always waits to see if the player infront has a wall that way we dont push and stand still if there is a wall infront of x robot
     *
     * @param player this is the player that is pushing
     * @param heading this is the direction of the push
     */
    public boolean pushPlayer(@NotNull Player player,Heading heading)
    {
        Space space = player.getSpace();
        Space target = board.getNeighbour(space, heading);
        Player playerToPush = target.getPlayer();
        Space nextTarget = board.getNeighbour(target,heading);
        boolean isWall =false;

        // XXX note that this removes an other player from the space, when there
        //     is another player on the target. Eventually, this needs to be
        //     implemented in a way so that other players are pushed away!

        //added a rekursive loop that will always check the player infront before pushing
        if (target.getWalls().contains(heading))
        {
            isWall=true;
        }
        if(nextTarget.getPlayer()!=null&&isWall !=true)
        {
           isWall= pushPlayer(playerToPush,heading);
        }
        if (isWall!=true) {
            nextTarget.setPlayer(playerToPush);
        }
        return isWall;
    }

    // TODO: V2
    public void fastForward(@NotNull Player player) {
        moveForward(player);
        moveForward(player);
    }

    // TODO: V2
    public void turnRight(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().next());
        }
    }

    // TODO: V2
    public void turnLeft(@NotNull Player player) {
        if (player != null && player.board == board) {
            player.setHeading(player.getHeading().prev());
        }
    }

    public boolean moveCards(@NotNull CommandCardField source, @NotNull CommandCardField target) {
        CommandCard sourceCard = source.getCard();
        CommandCard targetCard = target.getCard();
        if (sourceCard != null && targetCard == null) {
            target.setCard(sourceCard);
            source.setCard(null);
            return true;
        } else {
            return false;
        }
    }

    /**
     * A method called when no corresponding controller operation is implemented yet. This
     * should eventually be removed.
     */
    public void notImplemented() {
        // XXX just for now to indicate that the actual method is not yet implemented
        assert false;
    }
    public void executeCommandOptionAndContinue(Command command,Player player){
        int step =board.getStep();
        Player currentPlayer = board.getCurrentPlayer();
        board.setPhase(Phase.ACTIVATION);
        executeCommand(player,command);
        if (board.getOrderNumber(currentPlayer)+1 < board.getPlayersNumber()) {
            board.setCurrentPlayer(board.getPlayerOrder().get((board.getOrderNumber(currentPlayer)+1)%(board.getPlayers().size())));

        if (board.isStepMode() == false) {
                executePrograms();
            }
        } else {
               step++;
                if (step < Player.NO_REGISTERS) {
                    makeProgramFieldsVisible(step);
                    board.setStep(step);
                    board.setCurrentPlayer(board.getPlayerOrder().get((board.getOrderNumber(currentPlayer)+1)%(board.getPlayers().size())));
                } else {
                    startProgrammingPhase();
                }

        }
    }    public void initiateWin(Player player) {
        Alert winMsg = new Alert(Alert.AlertType.INFORMATION, "Spiller \"" + player.getName() + "\" har vundet spillet.");
        this.won = true;
        winMsg.showAndWait();
    }


}
