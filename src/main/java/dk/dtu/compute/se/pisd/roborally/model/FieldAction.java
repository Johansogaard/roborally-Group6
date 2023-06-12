package dk.dtu.compute.se.pisd.roborally.model;


import dk.dtu.compute.se.pisd.roborally.controller.GameController;

/**

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
