package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 * @author Gustav Utke Kauman, s195396@student.dtu.dk
 */
public class ConveyorBelt implements FieldAction {

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
                if (action instanceof ConveyorBelt && ((ConveyorBelt) action).getHeading() != this.heading.opposite()) {
                    action.doAction(gameController, currentPlayer.getSpace());
                }
            }

            return true;
        }

    }

}
