package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.model.Board;

import java.util.ArrayList;

public interface IRepository {



    void saveGameToServer(Board board, String saveName);

    public int getId();

    int getPlayerNumb();

    void createGame(int maxNumbOfPlayers);

    ArrayList<String> getFiles();

    void postGameInstanceActivationPhase(Board board);

    void postGameInstanceProgrammingPhase(Board board);

    void postGameInstance(Board board);

    void joinGame(int id);

    Board getGameFromServer(String fileName);

    public Board getGameInstance(Board oldBoard);





    void waitForPlayersProg();

    void waitForPlayersAct();
}
