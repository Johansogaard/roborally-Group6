package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class BeltRotating implements FieldAction {

    private Heading heading = Heading.EAST;

    public Heading getHeading() {
        return heading;
    }

    public FieldAction action;

    public void setHeading(Heading heading) {
        this.heading = heading;
    }


    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Player currentPlayer = space.getPlayer();

        currentPlayer.setHeading(this.heading);

        // Check if the player can move forward
        Space neighbourSpace = space.board.getNeighbour(space, heading);

        if (neighbourSpace.getPlayer() != null) {
            return false;
        } else {

            for (FieldAction action : neighbourSpace.actions) {
                if (action instanceof Belt && ((Belt) action).getHeading() != this.heading.opposite()) {
                    currentPlayer.setSpace(neighbourSpace);
                }
            }
            // if there's no adjecent conveyorbelts for the current player
            currentPlayer.setSpace(neighbourSpace);

            return true;
        }
    }
}
