package dk.dtu.compute.se.pisd.roborally.model;


import dk.dtu.compute.se.pisd.roborally.controller.GameController;

/**
 * This class is used to hold information about the Checkpoints
 * placed on the game board.
 *
 * @author Gustav Kirkholt, s164765@student.dtu.dk
 * @author Gustav Utke Kauman, s195396@student.dtu.dk
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
