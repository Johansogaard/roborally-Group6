package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.designpatterns.observer.Observer;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Player;
import dk.dtu.compute.se.pisd.roborally.view.BoardView;
import dk.dtu.compute.se.pisd.roborally.view.PlayerView;
import dk.dtu.compute.se.pisd.roborally.view.PlayersView;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;

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

        getGameInstance(null);
    }

    @Override
    public Board getGameInstance(Board oldBoard) {
        String gameString = client.getGameInstance(id);
        Board newBoard = client.loadGameInstanceFromString(gameString);
        //this will add the observers to the new board if there is any
        if (oldBoard != null)
        {
            newBoard.setObservers(oldBoard.getObservers());
            for (Observer observer: newBoard.getObservers()) {
             if (observer instanceof BoardView)
             {
                 ((BoardView) observer).setBoard(newBoard);
                 for (int i =0;i<newBoard.width;i++)
                 {
                     for (int f =0;f<newBoard.height;f++)
                     {
                         ((BoardView) observer).getSpaces()[i][f].setSpace(newBoard.getSpace(i,f));
                         newBoard.getSpace(i,f).attach( ((BoardView) observer).getSpaces()[i][f]);
                     }
                 }
             }
             else if(observer instanceof PlayersView)
             {
                 ((PlayersView) observer).setBoard(newBoard);

             }


            }


        for (int f=0;f<newBoard.getPlayers().size();f++)
        {
            newBoard.getPlayers().get(f).setObservers(newBoard.getObservers());
            for(int i =0;i<newBoard.getPlayers().get(f).getCards().length;i++)
            {
                newBoard.getPlayers().get(f).getCards()[i].setObservers(oldBoard.getPlayers().get(f).getProgram()[i].getObservers());
            }
            for(int i =0;i<newBoard.getPlayers().get(f).getProgram().length;i++)
            {
                newBoard.getPlayers().get(f).getProgram()[i].setObservers(oldBoard.getPlayers().get(f).getProgram()[i].getObservers());
            }
        }
        }
        return newBoard;
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
