package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;


/**
 *
 */
public class Pit implements FieldAction {

    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {return true;}

}
