package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class BeltDoubleRotating implements ConveyorBelt {

        private Heading heading = Heading.EAST;

        private boolean hasMovedOffConveyoerBelt = false;

        public Heading getHeading() { return heading; }

        public FieldAction action;

        public void setHeading(Heading heading) { this.heading = heading;
        }

        @Override
        public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
            Player currentPlayer = space.getPlayer();

            currentPlayer.setHeading(this.heading);

            // Check if the player can move forward
            Space neighbourSpace = space.board.getNeighbour(space, heading);
            Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

            if (neighbourSpace.getPlayer() != null || secondNeighbourSpace.getPlayer() != null
                    || neighbourSpace.getWalls().contains(this.heading.opposite())) {
                return false; // Player cannot move forward
            }

            // Move the player further if there are more directional conveyor belts
            for (FieldAction action : neighbourSpace.getActions()) {
                if(neighbourSpace.getWalls().contains(this.heading) || secondNeighbourSpace.getWalls().contains(this.heading.opposite())){
                    currentPlayer.setSpace(neighbourSpace);
                    return true;
                }
                else if(action instanceof BeltDoubleRotating){
                    hasMovedOffConveyoerBelt = true;
                    currentPlayer.setSpace(neighbourSpace);
                    currentPlayer.setHeading(((BeltDoubleRotating) action).getHeading());

                    for(FieldAction action2 : secondNeighbourSpace.getActions()){
                        if(currentPlayer.getSpace().getWalls().contains(this.heading) || secondNeighbourSpace.getWalls().contains(this.heading.opposite())){
                            return true;
                        }

                        if(action2 instanceof BeltDoubleRotating){
                            currentPlayer.setSpace(neighbourSpace);
                            currentPlayer.setHeading(((BeltDoubleRotating) action).getHeading());
                            break;
                        }
                    }
                }

                else if (action instanceof BeltDouble) {
                    BeltDouble conveyorBelt = (BeltDouble) action;
                    currentPlayer.setHeading(conveyorBelt.getHeading());
                    if (secondNeighbourSpace.getPlayer() != null) {
                        return false; // Player reached the final destination
                    }
                    currentPlayer.setSpace(secondNeighbourSpace);
                }
                else{
                    // If the square has no conveyor belt, then the player just moves one
                    currentPlayer.setSpace(neighbourSpace);
                }
                //if the conveyorbelt finishes and has no conveyorbelt actions neighbouring the player
                if(!hasMovedOffConveyoerBelt){
                    currentPlayer.setSpace(neighbourSpace);

                }
                else{
                    currentPlayer.setSpace(secondNeighbourSpace);

                }
            }

            return true;
        }
    }