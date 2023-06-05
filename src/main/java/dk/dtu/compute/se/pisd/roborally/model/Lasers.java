package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class Lasers implements FieldAction {
    private Heading heading;
    private boolean on;

    public Lasers(Heading heading) {
        this.heading = heading;
        this.on = false;
    }

    public Heading getHeading() {
        return heading;
    }

    public boolean isOn() {
        return on;
    }

    public void turnOn() {
        this.on = true;
    }

    public void turnOff() {
        this.on = false;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        // Implement the action of the laser here
        // ...
        return true;
    }
}