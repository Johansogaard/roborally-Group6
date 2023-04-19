package dk.dtu.compute.se.pisd.roborally.model;

public abstract  class Board_Element {
    final public Board board;
    private Space space;

    protected Board_Element(Board board,Space space) {
        this.board = board;
        this.space = space;

    }
    public void setSpace(Space space) {
               if (space != this.space) {
            this.space = space;
        }


    }
}
