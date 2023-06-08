package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class PushPanel implements FieldAction {
int a,b;
    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    public void setHeading(Heading heading) { this.heading = heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
       if((gameController.board.getStep()==a)||(gameController.board.getStep()==b)){
        Player currentPlayer = space.getPlayer();
        Space neighbourSpace = space.board.getNeighbour(space,heading);

            currentPlayer.setSpace(neighbourSpace);

           for (FieldAction action : neighbourSpace.actions) {
                if (action instanceof PushPanel && ((PushPanel) action).getHeading() != this.heading.opposite()) {
                    action.doAction(gameController, currentPlayer.getSpace());
                }
            }


        }
        return true;
    }

}
