package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class ConveyorBelt3 implements FieldAction {

    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    public void setHeading(Heading heading) { this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Player currentPlayer = space.getPlayer();
        currentPlayer.setHeading(this.heading);

        // Check if the player can move forward
        Space nextSpace = space.board.getNeighbour(space, heading);
        if (nextSpace == null || nextSpace.getPlayer() != null) {
            return false; // Player cannot move forward
        }

        currentPlayer.setSpace(nextSpace);

        // Move the player further if there are more directional conveyor belts
        for (FieldAction action : nextSpace.getActions()) {
            if (action instanceof ConveyorBelt3) {
                ConveyorBelt3 conveyorBelt = (ConveyorBelt3) action;
                currentPlayer.setHeading(conveyorBelt.getHeading());
                nextSpace = nextSpace.board.getNeighbour(nextSpace, conveyorBelt.getHeading());
                if (nextSpace == null || nextSpace.getPlayer() != null) {
                    return true; // Player reached the final destination
                }
                currentPlayer.setSpace(nextSpace);
            }
        }

        return true;
    }
}

