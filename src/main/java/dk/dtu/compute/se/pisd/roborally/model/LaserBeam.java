package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class LaserBeam implements FieldAction {
    private Heading heading;
    public int power; // The power of the laser

    public LaserBeam(Heading heading, int power) {
        this.heading = heading;
        this.power = power;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {return true;}}