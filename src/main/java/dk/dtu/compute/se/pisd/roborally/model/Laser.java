package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import org.jetbrains.annotations.NotNull;

import static dk.dtu.compute.se.pisd.roborally.model.Command.SPAM;

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

    public void shootLaser(Space space, GameController gameController){
        while (space != null && space.getPlayer() == null && !space.getWalls().contains(heading)
                && space!=gameController.board.getSpace(gameController.board.getAntenna().x,gameController.board.getAntenna().y)
                && !gameController.board.getNeighbour(space,heading).getWalls().contains(heading.opposite())) {
            space = gameController.board.getNeighbour(space, heading);}

        if (space.getPlayer()!=null){for (int i = 0; i < power; i++) {
            // Assuming you have a method to damage the player. Replace with your own logic.
            space.getPlayer().deck.addCard(new CommandCard(SPAM));}
        }
    }
    @Override
    public boolean doAction(@NotNull GameController gameController, @NotNull Space space) {
        if(space.getPlayer()!=null){for (int i = 0; i < power; i++) {
            space.getPlayer().deck.addCard(new CommandCard(SPAM));}}
        else{shootLaser(space, gameController);}

        return true;
        }


    }