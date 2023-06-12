package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class Belt implements ConveyorBelt {

    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    public void setHeading(Heading heading) { this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {

        Player currentPlayer = space.getPlayer();
        Space neighbourSpace = space.board.getNeighbour(space,heading);

        if (neighbourSpace.getPlayer() != null || neighbourSpace.getWalls().contains(this.heading.opposite())
        || currentPlayer.getSpace().getWalls().contains(this.heading)) {
            return false;
        } else {

           for (FieldAction action : neighbourSpace.actions) {
                if (action instanceof Belt || action instanceof BeltRotating) {
                    currentPlayer.setSpace(neighbourSpace);
                }
                else{
                    currentPlayer.setSpace(neighbourSpace);
                }
            }

            return true;
        }

    }

}
