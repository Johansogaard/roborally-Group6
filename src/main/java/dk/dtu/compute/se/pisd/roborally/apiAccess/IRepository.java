package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public interface IRepository {



    void saveGameToServer(Board board, String saveName);

    public int getId();

    int getPlayerNumb();

    void createGame(int maxNumbOfPlayers);

    void postGameInstanceActivationPhase(Board board);

    void postGameInstanceProgrammingPhase(Board board);

    void postGameInstance(Board board);

    void joinGame(int id);

    public Board getGameInstance(Board oldBoard);





    void waitForPlayersProg();

    void waitForPlayersAct();
}
