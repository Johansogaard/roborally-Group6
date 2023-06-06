package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.apiAccess.Client;

import java.io.FileWriter;
import java.io.IOException;

public class ClientController {
    public int getId() {
        return id;
    }

    int id;
    int playerNumb;

    public void setMaxPlayerNumb(int maxPlayerNumb) {
        this.maxPlayerNumb = maxPlayerNumb;
    }

    int maxPlayerNumb;

    public final Client client;
    public ClientController(Client client)
    {
        this.client = client;
    }
    public void createGame(int maxNumbOfPlayers) {
        id = client.CreateGameInstance(maxNumbOfPlayers);
        playerNumb =1;


    }
    public void postGameInstance()
    {
        client.postGameInstance(id);
    }
    public void joinGame(int id)
    {
        this.id = id;
      playerNumb = client.joinGame(id);

      getGameInstance();
    }
    public void getGameInstance()
    {
        client.getGameInstance(id);
    }




}
