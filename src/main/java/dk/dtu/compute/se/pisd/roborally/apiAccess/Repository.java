package dk.dtu.compute.se.pisd.roborally.apiAccess;

import dk.dtu.compute.se.pisd.roborally.model.Board;

public class Repository implements IRepository{

    Client client = new Client();
    @Override
    public Board getGameInstance() {
        return null;
    }
}
