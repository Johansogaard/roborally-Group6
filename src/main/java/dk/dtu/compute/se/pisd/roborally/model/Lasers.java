package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class Lasers implements FieldAction {
    private Heading heading;

    public Lasers(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // Implement the action of the laser here
        // ...
        return true;
    }
}