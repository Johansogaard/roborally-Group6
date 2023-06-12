package dk.dtu.compute.se.pisd.roborally.model;


import com.sun.javafx.scene.traversal.Direction;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import javafx.scene.control.skin.TextInputControlSkin;


/**
 *

 */
public class Gear implements FieldAction {
    public Direction direction;
    public Gear(Direction direction) {
        this.direction = direction;
    }


    public void setDirection(Direction direction) { this.direction = direction;}



    @Override
    public boolean doAction(GameController gameController, Space space){

        Player player = space.getPlayer();

        switch (direction) {
            case LEFT:
                gameController.turnLeft(player);
                break;

            case RIGHT:
                gameController.turnRight(player);
                break;
        }

        return true;

    }
}

