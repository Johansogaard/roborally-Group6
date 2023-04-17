package dk.dtu.compute.se.pisd.roborally.model;
import java.util.*;

public class Checkpoints {
    private Board board;
    private Space[] checkpoints = new Space[3];

    Player player;

    private List<Space> visitedCheckpoints = new ArrayList<>();

    private boolean visited;

    public boolean isVisited(){
        return visited;
    }

    public void setVisited(boolean visited){
        this.visited = visited;
    }

    public Board Checkpoint(Board board){
        this.board = board;
        createCheckpoints();
        return board;
    }

    private void createCheckpoints() {
        // generate 3 unique random positions
        List<Space> positions = new ArrayList<>();
        for (int x = 0; x < board.width; x++) {
            for (int y = 0; y < board.height; y++) {
                positions.add(board.getSpace(x, y));
            }
        }
        Collections.shuffle(positions);
        for (int i = 0; i < 3; i++) {
            Space checkpoint = positions.get(i);
            // add the function to the checkpoint space
            addCheckpointFunction(player, new Space(board, checkpoint.x, checkpoint.y));
            checkpoints[i] = checkpoint;
        }
    }


    private void addCheckpointFunction(Player player, Space space){
        // check if the space is a checkpoint
        if (Arrays.asList(checkpoints).contains(space)) {
            // check if the checkpoint has already been visited by this player
            if (!isVisited()) {
                // mark the checkpoint as visited
                setVisited(true);
                // add the checkpoint to the player's list of visited checkpoints
                addVisitedCheckpoint(space);
                // check if the player has visited all three checkpoints in order
                if (hasVisitedAllCheckpoints()) {
                    // declare the player the winner of the game
                    //board.declareWinner(player);
                }
            }
        }
    }

    public void addVisitedCheckpoint(Space checkpoint){
        visitedCheckpoints.add(checkpoint);
    }
    public boolean hasVisitedAllCheckpoints() {
        return visitedCheckpoints.size() == 3 &&
                visitedCheckpoints.get(0) == checkpoints[0] &&
                visitedCheckpoints.get(1) == checkpoints[1] &&
                visitedCheckpoints.get(2) == checkpoints[2];
    }

}
