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

        currentPlayer.setHeading(this.heading);
        Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

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
                    if(neighbourSpace.getWalls().contains(this.heading) && neighbourSpace.getWalls().contains(this.heading.opposite())){
                        return false;
                    }
                    else{
                        BeltDoubleRotating conveyorBelt = (BeltDoubleRotating) action;
                        currentPlayer.setHeading(conveyorBelt.getHeading());
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

}
