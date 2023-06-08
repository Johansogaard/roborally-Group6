package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;

public interface ConveyorBeltAction {
    boolean doAction(GameController gameController, Space space);
}
