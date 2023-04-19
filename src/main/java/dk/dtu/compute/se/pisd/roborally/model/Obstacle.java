package dk.dtu.compute.se.pisd.roborally.model;

public abstract class Obstacle {
    protected boolean isSolid;

    public Obstacle(boolean isSolid) {
        this.isSolid = isSolid;
    }
    public boolean isSolid() {
        return isSolid;
    }
    public abstract void interactWithRobot(Player player);
}
