package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.apiAccess.Client;

import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ClientController {
    public int getId() {
        return id;
    }

    int id;

    public int getPlayerNumb() {
        return playerNumb;
    }

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
    public void postGameInstanceProgrammingPhase(String jsonData)
    {
        client.postGameInstanceProgrammingPhase(id,jsonData,playerNumb);
    }
    public void postGameInstance(String jsonData)
    {
        client.postGameInstance(id,jsonData);
    }
    public void joinGame(int id)
    {
        this.id = id;
      playerNumb = client.joinGame(id);

      getGameInstance();
    }
    public String getGameInstance()
    {
        return client.getGameInstance(id);
    }
    public void waitForPlayers() {
        while (true) {

            String status = client.getStatus(id);
            if (status.equals("Ready"))
            {
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
