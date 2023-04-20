package dk.dtu.compute.se.pisd.roborally.model;


import dk.dtu.compute.se.pisd.roborally.controller.GameController;

/**
 * Simple interface that can support an action on a field (Space).
 * This can eg. be the Conveyor Belts that move the players.
 *
 * @author Ekkart Kindler, ekki@dtu.dk
 * @author Gustav Utke Kauman, s195396@student.dtu.dk
 */
public interface FieldAction {

    /**
     * Executes the action for a given field.
     *
     * @param gameController
     * @param space
     */
    boolean doAction(GameController gameController, Space space);

}
