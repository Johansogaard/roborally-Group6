package dk.dtu.compute.se.pisd.roborally.model;

public class Pit extends Obstacle {

    public Pit() {
        super(false);
    }


    @Override
    public void interactWithRobot(Player player) {
        //make robot reboot
        if (player.getSpace().isPit()) {
            player.reboot();
        }
    }
}
