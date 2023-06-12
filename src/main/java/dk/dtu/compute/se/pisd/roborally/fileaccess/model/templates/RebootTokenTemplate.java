package dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.RebootToken;

public class RebootTokenTemplate {
    public int x;
    public int y;

    public RebootTokenTemplate fromRebootToken(RebootToken rebootToken) {
        this.x = rebootToken.x;
        this.y = rebootToken.y;

        return this;
    }

    public RebootToken toRebootToken(Board board) {
        RebootToken rebootToken = new RebootToken(board, x, y);

        return rebootToken;
    }
}
