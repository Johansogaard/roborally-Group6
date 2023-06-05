package dk.dtu.compute.se.pisd.roborally.model;

import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;

public class RebootToken extends Subject {
    public final Board board;

    public final int x;
    public final int y;

    public RebootToken(Board board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
    }
}
