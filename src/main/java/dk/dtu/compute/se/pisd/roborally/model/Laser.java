package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class Laser implements FieldAction {
    private Heading heading;
    public int power; // The power of the laser

    public Laser(Heading heading, int power) {
        this.heading = heading;
        this.power = power;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Player currentPlayer = space.getPlayer();
        space = currentPlayer.getSpace();

        for (FieldAction action : space.actions) {
            if(action instanceof Laser){
                for (int i = 0; i < power; i++) {
                    currentPlayer.deck.addCard(new CommandCard(Command.SPAM));
                }
            }
        }
        return true;
    }
}