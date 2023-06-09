package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import org.jetbrains.annotations.NotNull;

public class Lasers implements FieldAction {
    private Heading heading;

    public FieldAction action;


    public Lasers(Heading heading) {
        this.heading = heading;
    }

    public Heading getHeading() {
        return heading;
    }

    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        Player currentPlayer = space.getPlayer();
        space = currentPlayer.getSpace();

        for (FieldAction action : space.actions) {
            if(action instanceof Lasers){
                currentPlayer.deck.addCard(new CommandCard(Command.SPAM));
            }
        }
        return true;
    }
}
