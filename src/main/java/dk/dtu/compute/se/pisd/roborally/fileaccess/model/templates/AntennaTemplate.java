package dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates;

import dk.dtu.compute.se.pisd.roborally.model.Antenna;
import dk.dtu.compute.se.pisd.roborally.model.Board;

public class AntennaTemplate {
    public  int x;
    public  int y;

    public AntennaTemplate fromAntenna(Antenna antenna)
    {
        this.x = antenna.x;
        this.y = antenna.y;

        return this;
    }
    public Antenna toAntenna(Board board)
    {
        Antenna antenna = new Antenna(board,x,y);

        return antenna;
    }
}
