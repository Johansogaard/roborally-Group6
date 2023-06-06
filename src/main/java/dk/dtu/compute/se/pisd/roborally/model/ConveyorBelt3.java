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
        Space neighbourSpace = space.board.getNeighbour(space, heading);

        currentPlayer.setHeading(this.heading);

        // Check if the neighbor space is occupied by another player
        if (neighbourSpace.getPlayer() != null) {
            return false;
        } else {
            // Move the current player to the neighbor space
            currentPlayer.setSpace(neighbourSpace);

            // Move the player in all four directions
            for (Heading direction : Heading.values()) {
                // Exclude the opposite direction of the current heading
                if (direction != this.heading.opposite()) {
                    Space nextSpace = neighbourSpace.board.getNeighbour(neighbourSpace, direction);
                    // Check if the next space has a conveyor belt action
                    for (FieldAction action : nextSpace.actions) {
                        if (action instanceof ConveyorBelt) {
                            // Move the player to the next space with conveyor belt action
                            action.doAction(gameController, currentPlayer.getSpace());
                            break;
                        }
                    }
                }
            }

            return true;
        }
    }

}