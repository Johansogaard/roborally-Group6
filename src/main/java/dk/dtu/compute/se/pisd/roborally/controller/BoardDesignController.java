package dk.dtu.compute.se.pisd.roborally.controller;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.view.BoardDesignView;

public class BoardDesignController {
    private int width, height;
    private Board board;
    private BoardDesignView boardDesignerView;

    public BoardDesignController(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public BoardDesignController(Board board) {
        this.board = board;
    }

    public BoardDesignView createView() {

        if (this.board == null) {
            this.createBoard();
        }

        this.boardDesignerView = new BoardDesignView(this.board);

        return this.boardDesignerView;
    }

    public void createBoard() {
        this.board = new Board(this.width, this.height);
        this.board.setAntenna(null);
    }
}
