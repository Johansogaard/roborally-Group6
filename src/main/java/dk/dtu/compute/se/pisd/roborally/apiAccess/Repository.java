package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.model.Board;

import java.util.concurrent.TimeUnit;

public class Repository implements IRepository {
    private static Repository single_instance = null;
    Client client = new Client();
    int id;
    int playerNumb;
    int maxPlayerNumb;

    // Static method
    // Static method to create instance of Singleton class
    public static synchronized Repository getInstance() {
        if (single_instance == null)
            single_instance = new Repository();

        return single_instance;
    }
    @Override
    public int getId() {
        return id;
    }
    @Override
    public int getPlayerNumb() {
        return playerNumb;
    }

    public void setMaxPlayerNumb(int maxPlayerNumb) {
        this.maxPlayerNumb = maxPlayerNumb;
    }
    @Override
    public void createGame(int maxNumbOfPlayers) {
        id = client.CreateGameInstance(maxNumbOfPlayers);
        playerNumb = 1;


    }
    @Override
    public void postGameInstanceActivationPhase(Board board) {
        String jsonData = client.getGameInstanceAsString(board);
        client.postGameInstanceActivationPhase(id, jsonData);

    }

    @Override
    public void postGameInstanceProgrammingPhase(Board board) {
        String jsonData = client.getGameInstanceAsString(board);
        client.postGameInstanceProgrammingPhase(id, jsonData, playerNumb);
        waitForPlayers();
    }
@Override
    public void postGameInstance(Board board) {
        String jsonData = client.getGameInstanceAsString(board);
        client.postGameInstance(id, jsonData);
    }
@Override
    public void joinGame(int id) {
        this.id = id;
        playerNumb = client.joinGame(id);

        getGameInstance();
    }

    @Override
    public Board getGameInstance() {
        String gameString = client.getGameInstance(id);
        return client.loadGameInstanceFromString(gameString);
    }
    @Override
    public void waitForPlayers() {
        while (true) {

            String status = client.getStatus(id);
            if (status.equals("Ready")) {
                break;
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
