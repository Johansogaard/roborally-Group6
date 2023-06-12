package dk.dtu.compute.se.pisd.roborally.model;


import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * This class is used to hold information about the Checkpoints
 * placed on the game board.
 *

 */
public class Checkpoint implements FieldAction {

    public final int no;

    public Checkpoint(int no) {
        this.no = no;
    }

    @Override
    public boolean doAction(GameController gameController, Space space) {
        Player player = space.getPlayer();
        if (player != null) {
            // there is actually a player on this space
            player.setLastCheckpoint(this.no);
            if (player.getLastCheckpoint() >= gameController.board.getCheckpoints().size()) {
                gameController.initiateWin(player);
            }

        }

        return true;
    }
}
