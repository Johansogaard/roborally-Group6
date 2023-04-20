package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.roborally.controller.GameController;


/**
 *
 */
public class Pit implements FieldAction {

    @Override
    public boolean doAction(GameController gameController, Space space) {

        Board board = space.board;
        Player player = space.getPlayer();
        player.reboot();

        if (player == null) {
            return false;
        }




        for (int i = 0; i < board.width; i++) {
           /* for (int j = 0; j < board.height; j++) {
                Space newSpace = board.getSpace(i,j);
                if (newSpace.getStartPlayerNo() == (player.getDbNo() + 1) && newSpace.getPlayer() == null) {
                    player.setSpace(newSpace);
                    return true;
                }
            }*/
        }

        return false;

    }
}
