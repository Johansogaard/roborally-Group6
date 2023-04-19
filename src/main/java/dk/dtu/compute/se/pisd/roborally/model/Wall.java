package dk.dtu.compute.se.pisd.roborally.model;

public class Wall extends Obstacle{

    private Heading heading;

    public Wall(Heading heading) {
        super(true);
        this.heading=heading;
    }

    public Heading getHeading() {
        return heading;
    }

    public void setHeading(Heading heading) {
        this.heading = heading;
    }
    @Override
    public void interactWithRobot(Player player) {
        // If a robot tries to move onto a wall, stop it
      //  if (player.isOnSpace(this)) {
         //   player.stop();
        }
    }

