package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class ConveyorBelt2 implements FieldAction {

    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    private boolean hasMoved = false;

    public void setHeading(Heading heading) { this.heading = heading;}

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {


        Player currentPlayer = space.getPlayer();
        Space neighbourSpace = space.board.getNeighbour(space,heading);

        currentPlayer.setHeading(this.heading);

        if (neighbourSpace.getPlayer() != null) {
            return false;
        } else {

            Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

            for (FieldAction action : neighbourSpace.actions) {
                if (action instanceof ConveyorBelt2 || action instanceof ConveyorBelt3 && ((ConveyorBelt3) action).getHeading() != this.heading.opposite()) {
                    currentPlayer.setSpace(neighbourSpace);

                    for (FieldAction action2 : secondNeighbourSpace.actions) {

                        if(action2 instanceof ConveyorBelt2 || action2 instanceof ConveyorBelt3 && ((ConveyorBelt3) action2).getHeading() != this.heading.opposite()){

                            currentPlayer.setSpace(secondNeighbourSpace);
                        }
                        else{
                            break;
                        }
                    }

                }
                else{
                    break;
                }

            }



            //if(secondNeighbourSpace.actions instanceof ConveyorBelt2 || secondNeighbourSpace.actions instanceof  ConveyorBelt3) {
               // currentPlayer.setSpace(secondNeighbourSpace);
            //}
            return true;
        }

    }

}
