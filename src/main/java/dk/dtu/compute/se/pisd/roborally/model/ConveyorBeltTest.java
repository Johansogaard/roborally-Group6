package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

/*public class ConveyorBeltTest implements ConveyorBeltAction {

    private Heading heading = Heading.EAST;

    public Heading getHeading() { return heading; }

    private boolean hasMovedOffConveyoerBelt = false;

    public void setHeading(Heading heading) { this.heading = heading;}
    private int conveyorBeltNumber; // Represents the different conveyor belt logic

    public ConveyorBeltTest(int conveyorBeltNumber) {
        this.conveyorBeltNumber = conveyorBeltNumber;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player currentPlayer = space.getPlayer();
        Space neighbourSpace = space.board.getNeighbour(space,heading);
        Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

        switch (conveyorBeltNumber) {
            case 1:

                if (neighbourSpace.getPlayer() != null) {
                    return false;
                } else {

                    for (FieldAction action : neighbourSpace.actions) {
                        if (action instanceof Belt || action instanceof BeltRotating) {
                            currentPlayer.setSpace(neighbourSpace);
                        }
                    }

                    return true;
                }

            case 2:

                currentPlayer.setHeading(this.heading);

                if (neighbourSpace.getPlayer() != null || secondNeighbourSpace.getPlayer() != null) {
                    return false;
                } else {

                    //Space secondNeighbourSpace = space.board.getNeighbour(neighbourSpace, heading);

                    for (FieldAction action : neighbourSpace.actions) {

                        if (action instanceof BeltDouble && ((BeltDouble) action).getHeading() != this.heading.opposite()) {

                            hasMovedOffConveyoerBelt = true;

                            for (FieldAction action2 : secondNeighbourSpace.actions) {

                                if(action2 instanceof BeltDouble || action2 instanceof BeltDoubleRotating && ((BeltDoubleRotating) action2).getHeading() != this.heading.opposite()){

                                    currentPlayer.setSpace(secondNeighbourSpace);
                                }
                                else{
                                    break;
                                }
                            }

                        }
                        else if (action instanceof BeltDoubleRotating && ((BeltDoubleRotating) action).getHeading() != this.heading.opposite()) {
                            BeltDoubleRotating conveyorBelt = (BeltDoubleRotating) action;
                            currentPlayer.setHeading(conveyorBelt.getHeading());
                            Space neighbourSpace1 = neighbourSpace.board.getNeighbour(neighbourSpace, conveyorBelt.getHeading());
                            if (secondNeighbourSpace.getPlayer() != null) {
                                return false; // Player cannot move forward
                            }
                            currentPlayer.setSpace(neighbourSpace1);
                        }

                    }
                    //if there is 1 or no adjecent conveyorbelts in the players heading
                    if(!hasMovedOffConveyoerBelt){
                        currentPlayer.setSpace(neighbourSpace);
                    }
                    else{
                        currentPlayer.setSpace(secondNeighbourSpace);
                    }

                    return true;
                }
            case 3:
                currentPlayer.setHeading(this.heading);

                // Check if the player can move forward

                if (neighbourSpace.getPlayer() != null || secondNeighbourSpace.getPlayer() != null) {
                    return false; // Player cannot move forward
                }

                // Move the player further if there are more directional conveyor belts
                for (FieldAction action : neighbourSpace.getActions()) {
                    if(action instanceof BeltDoubleRotating){
                        currentPlayer.setSpace(neighbourSpace);
                        currentPlayer.setHeading(((BeltDoubleRotating) action).getHeading());

                        for(FieldAction action2 : secondNeighbourSpace.getActions()){

                            if(action2 instanceof BeltDoubleRotating){
                                currentPlayer.setSpace(neighbourSpace);
                                currentPlayer.setHeading(((BeltDoubleRotating) action).getHeading());
                                break;
                            }
                        }
                    }

                    if (action instanceof BeltDouble) {
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
                }

                return true;

            case 4:
                currentPlayer.setHeading(this.heading);

                // Check if the player can move forward

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

            default:
                // Invalid conveyorBeltNumber
                break;
        }
        return true;
    }
}*/

