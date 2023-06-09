package dk.dtu.compute.se.pisd.roborally.view;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.model.APIObserver;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import org.jetbrains.annotations.NotNull;

public class APIView  implements  ViewObserver{
    private GameController gameController;
    public APIView (@NotNull GameController gameController)
    {
        this.gameController = gameController;
        gameController.apiObserver.attach(this);

    }
    @Override
    public void updateView(Subject subject) {
        gameController.repository.waitForPlayersAct();
        gameController.board = gameController.repository.getGameInstance(gameController.board);
        gameController.board.notifyBoardChange();
        gameController.waitForAction();
    }
}
