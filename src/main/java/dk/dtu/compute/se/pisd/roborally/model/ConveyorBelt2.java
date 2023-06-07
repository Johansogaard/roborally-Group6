package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class ConveyorBelt2 implements FieldAction {

    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    public void setHeading(Heading heading) { this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {

        Player currentPlayer = space.getPlayer();
        Space neighbourSpace = space.board.getNeighbour(space,heading);

        currentPlayer.setHeading(this.heading);

        if (neighbourSpace.getPlayer() != null) {
            return false;
        } else {

            currentPlayer.setSpace(neighbourSpace);

            for (FieldAction action : neighbourSpace.actions) {
                if (action instanceof ConveyorBelt2 || action instanceof ConveyorBelt3 && ((ConveyorBelt2) action).getHeading() != this.heading.opposite()) {

                        action.doAction(gameController, currentPlayer.getSpace());

                }
            }

            Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);
            if(secondNeighbourSpace.actions instanceof ConveyorBelt2 || secondNeighbourSpace.actions instanceof  ConveyorBelt3) {
                currentPlayer.setSpace(secondNeighbourSpace);
            }
            return true;
        }

    }

}
