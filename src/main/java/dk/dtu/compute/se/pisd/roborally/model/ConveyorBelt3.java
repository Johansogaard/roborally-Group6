package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class ConveyorBelt3 implements FieldAction {

    private Heading heading = Heading.EAST;

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
            else if(action instanceof ConveyorBelt3){
                currentPlayer.setSpace(neighbourSpace);
                currentPlayer.setHeading(((ConveyorBelt3) action).getHeading());

                for(FieldAction action2 : secondNeighbourSpace.getActions()){
                    if(secondNeighbourSpace.getWalls().contains(this.heading.opposite())){
                        currentPlayer.setSpace(neighbourSpace);
                        return true;
                    }

                    if(action2 instanceof ConveyorBelt3){
                        currentPlayer.setSpace(neighbourSpace);
                        currentPlayer.setHeading(((ConveyorBelt3) action).getHeading());
                        break;
                    }
                }
            }

            else if (action instanceof ConveyorBelt2) {
                ConveyorBelt2 conveyorBelt = (ConveyorBelt2) action;
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
        }

        return true;
    }
}

