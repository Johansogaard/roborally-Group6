package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class BeltDouble implements ConveyorBelt {

    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    private boolean hasMovedOffConveyoerBelt = false;

    public void setHeading(Heading heading) { this.heading = heading;}


    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {



        Player currentPlayer = space.getPlayer();
        Space neighbourSpace = space.board.getNeighbour(space,heading);
        Space edgeOfMapNeighbourspace = space.board.getNeighbour(space,this.getHeading());
        Space rebootTokenLokation = gameController.board.getSpace((gameController.board.getRebootToken().x),(gameController.board.getRebootToken().y));

        // if the player is on the edge of the map and is on a conveyorbelt
        if(edgeOfMapNeighbourspace==null){
            currentPlayer.setSpace(rebootTokenLokation);
            return true;
        }


        //currentPlayer.setHeading(this.heading);

        Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

        if(neighbourSpace != null && secondNeighbourSpace != null){


        if (neighbourSpace.getPlayer() != null || secondNeighbourSpace.getPlayer() != null || currentPlayer.getSpace().getWalls().contains(heading)
                || neighbourSpace.getWalls().contains(this.heading.opposite())) {
            return false;
        } else {

            //Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

            for (FieldAction action : neighbourSpace.actions) {
                if(neighbourSpace.getWalls().contains(this.heading)){
                    currentPlayer.setSpace(neighbourSpace);
                    return true;
                }

                else if (action instanceof BeltDouble && ((BeltDouble) action).getHeading() != this.heading.opposite()) {

                    hasMovedOffConveyoerBelt = true;


                    for (FieldAction action2 : secondNeighbourSpace.actions) {

                        if(secondNeighbourSpace.getWalls().contains(this.heading.opposite())){
                            neighbourSpace.setPlayer(currentPlayer);
                            return true;
                        }

                        if(action2 instanceof BeltDouble || action2 instanceof BeltDoubleRotating && ((BeltDoubleRotating) action2).getHeading() != this.heading.opposite()){

                            currentPlayer.setSpace(secondNeighbourSpace);

                        }
                        else{
                            break;
                        }
                    }

                }
                else if (action instanceof BeltDoubleRotating && ((BeltDoubleRotating) action).getHeading() != this.heading.opposite()) {
                    BeltDoubleRotating conveyorBelt = (BeltDoubleRotating) action;
                    currentPlayer.setHeading(conveyorBelt.getHeading());
                    if(neighbourSpace.getWalls().contains(conveyorBelt.getHeading()) || secondNeighbourSpace.getWalls().contains(conveyorBelt.getHeading().opposite())){
                        currentPlayer.setSpace(neighbourSpace);
                        return false;
                    }
                    else{
                        Space neighbourSpace1 = neighbourSpace.board.getNeighbour(neighbourSpace, conveyorBelt.getHeading());
                        if (secondNeighbourSpace.getPlayer() != null) {
                            return false; // Player cannot move forward
                        }
                        currentPlayer.setSpace(neighbourSpace1);
                        return true;
                    }

                }

            }
            //if there is 1 or no adjecent conveyorbelts in the players heading
            if(!hasMovedOffConveyoerBelt){
                currentPlayer.setSpace(neighbourSpace);

            }
            else{
                currentPlayer.setSpace(secondNeighbourSpace);

            }

            return true;
        }

    }
else{

    currentPlayer.setSpace(rebootTokenLokation);
            return true;
        }
    }
}
