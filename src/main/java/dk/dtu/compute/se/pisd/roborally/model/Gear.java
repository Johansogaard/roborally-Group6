package dk.dtu.compute.se.pisd.roborally.model;


import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import javafx.scene.control.skin.TextInputControlSkin;


/**
 *
 * @author Benjamin Andresen
 * @author Gustav Utke Kauman, s195396@student.dtu.dk
 */
public class Gear implements FieldAction {

    public TextInputControlSkin.Direction direction;

    public void setDirection(TextInputControlSkin.Direction direction) { this.direction = direction;}

    public Gear(TextInputControlSkin.Direction direction) {
        this.direction = direction;
    }

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

