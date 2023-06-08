package dk.dtu.compute.se.pisd.roborally.view;



import com.sun.javafx.scene.traversal.Direction;
import dk.dtu.compute.se.pisd.designpatterns.observer.Subject;
import dk.dtu.compute.se.pisd.roborally.controller.GameController;
import dk.dtu.compute.se.pisd.roborally.controller.SaveBoardDesignController;
import dk.dtu.compute.se.pisd.roborally.model.*;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * made like BoardView just with a few changes
 */
public class BoardDesignView extends VBox {

    private Board board;
    private GridPane mainBoardPane;
    private SpaceView[][] spaces;
    private Label statusLabel;
    private SpaceEventHandler spaceEventHandler;

    private List fieldOptions = new ArrayList<String>();

    public BoardDesignView(Board board) {

        this.board = board;

        addOptions();

        mainBoardPane = new GridPane();

        this.getChildren().add(mainBoardPane);

        spaces = new SpaceView[board.width][board.height];

        spaceEventHandler = new SpaceEventHandler(this.board);

        for (int x = 0; x < this.board.width; x++) {
            for (int y = 0; y < this.board.height; y++) {
                Space space = this.board.getSpace(x, y);
                SpaceView spaceView = new SpaceView(space);
                spaces[x][y] = spaceView;
                mainBoardPane.add(spaceView, x, y);
                spaceView.setOnMouseClicked(spaceEventHandler);
            }
        }

        Button saveButton = new Button("Save the board!");
        saveButton.setOnAction(e -> {

            SaveBoardDesignController.saveBoardDesign(this.board);
        });

        VBox buttonsPane = new VBox(saveButton);
        buttonsPane.setSpacing(3.0);
        buttonsPane.setAlignment(Pos.CENTER);

        this.getChildren().add(buttonsPane);

    }

    private void addOptions() {
        this.fieldOptions.add("Antenna");
        this.fieldOptions.add("Conveyor Belt");
        this.fieldOptions.add("Player start field");
        this.fieldOptions.add("Walls");
        this.fieldOptions.add("Checkpoint");
        this.fieldOptions.add("Double Directional Conveyor belt");
        this.fieldOptions.add("Gear");
        this.fieldOptions.add("double Conveyor belt");
    }

    private class SpaceEventHandler implements EventHandler<MouseEvent> {

        private Board board;

        public SpaceEventHandler(Board board) {
            this.board = board;
        }

        @Override
        public void handle(MouseEvent event) {

            Object source = event.getSource();

            if (source instanceof SpaceView) {

                SpaceView spaceView = (SpaceView) source;
                Space space = spaceView.space;

                ChoiceDialog dialog = new ChoiceDialog();
                dialog.setContentText("Add a board element");
                dialog.getItems().addAll(fieldOptions);


                dialog.showAndWait();

                if (dialog.getSelectedItem() == null) {
                    return;
                }

                switch ((String) dialog.getSelectedItem()) {

                    case "Double Directional Conveyor belt":
                        addConveyorBelt3(space);
                        break;
                    case "Single Directional Conveyor belt":
                        addConveyorBelt4(space);
                        break;

                    case "Antenna":
                       addAntenna(space);
                        break;

                    case "Conveyor Belt":
                        addConveyorBelt(space);
                        break;

                    case "double Conveyor belt":
                        addConveyorBelt2(space);
                        break;
                    case "Push panel":
                        addPushPanel(space);
                        break;

                    case "Player start field":
                        addPlayerStart(space);
                        break;

                    case "Walls":
                        addWalls(space);
                        break;

                    case "Checkpoint":
                        addCheckpoint(space);
                        break;

                    case "Gear":
                        addGear(space);
                        break;
                    case "Reboot token":
                        addRebootToken(space);
                        break;
                    case "Pit":
                        space.addPit();
                        break;
                }

            }

            event.consume();
        }
        private void addAntenna(Space space)
        {

            if (this.board.getAntenna() == null) {
                space.addAntenna();
            }

        }

        private void addConveyorBelt(Space space) {

            for (FieldAction action : space.getActions()) {
                if (action instanceof ConveyorBelt) {
                    // TODO add some explanation to the user that there is already a belt at this space
                    return;
                }
            }

            ChoiceDialog dialog = new ChoiceDialog();
            dialog.setContentText("Which way should the belt move the player?");
            dialog.getItems().add(Heading.NORTH);
            dialog.getItems().add(Heading.EAST);
            dialog.getItems().add(Heading.SOUTH);
            dialog.getItems().add(Heading.WEST);

            dialog.showAndWait();

            if (dialog.getSelectedItem() != null) {

                ConveyorBelt belt = new ConveyorBelt();
                belt.setHeading((Heading) dialog.getSelectedItem());

                space.addAction(belt);
            }

        }

        private void addConveyorBelt2(Space space) {

            for (FieldAction action : space.getActions()) {
                if (action instanceof ConveyorBelt2) {
                    // TODO add some explanation to the user that there is already a belt at this space
                    return;
                }
            }

            ChoiceDialog dialog = new ChoiceDialog();
            dialog.setContentText("Which way should the belt move the player?");
            dialog.getItems().add(Heading.NORTH);
            dialog.getItems().add(Heading.EAST);
            dialog.getItems().add(Heading.SOUTH);
            dialog.getItems().add(Heading.WEST);

            dialog.showAndWait();

            if (dialog.getSelectedItem() != null) {

                ConveyorBelt2 belt = new ConveyorBelt2();
                belt.setHeading((Heading) dialog.getSelectedItem());

                space.addAction(belt);
            }

        }

        private void addConveyorBelt3(Space space) {

            for (FieldAction action : space.getActions()) {
                if (action instanceof ConveyorBelt3) {
                    // TODO add some explanation to the user that there is already a belt at this space
                    return;
                }
            }

            ChoiceDialog dialog = new ChoiceDialog();
            dialog.setContentText("Which way should the belt move the player?");
            dialog.getItems().add(Heading.NORTH);
            dialog.getItems().add(Heading.EAST);
            dialog.getItems().add(Heading.SOUTH);
            dialog.getItems().add(Heading.WEST);

            dialog.showAndWait();

            if (dialog.getSelectedItem() != null) {

                ConveyorBelt3 belt = new ConveyorBelt3();
                belt.setHeading((Heading) dialog.getSelectedItem());

                space.addAction(belt);
            }

        }

        private void addConveyorBelt4(Space space) {

            for (FieldAction action : space.getActions()) {
                if (action instanceof ConveyorBelt4) {
                    // TODO add some explanation to the user that there is already a belt at this space
                    return;
                }
            }

            ChoiceDialog dialog = new ChoiceDialog();
            dialog.setContentText("Which way should the belt move the player?");
            dialog.getItems().add(Heading.NORTH);
            dialog.getItems().add(Heading.EAST);
            dialog.getItems().add(Heading.SOUTH);
            dialog.getItems().add(Heading.WEST);

            dialog.showAndWait();

            if (dialog.getSelectedItem() != null) {

                ConveyorBelt4 belt = new ConveyorBelt4();
                belt.setHeading((Heading) dialog.getSelectedItem());

                space.addAction(belt);
            }

        }

        private void addPushPanel(Space space) {

            for (FieldAction action : space.getActions()) {
                if (action instanceof PushPanel) {
                    // TODO add some explanation to the user that there is already a belt at this space
                    return;
                }
            }

            ChoiceDialog dialog = new ChoiceDialog();
            dialog.setContentText("Which way should push panel face?");
            dialog.getItems().add(Heading.NORTH);
            dialog.getItems().add(Heading.EAST);
            dialog.getItems().add(Heading.SOUTH);
            dialog.getItems().add(Heading.WEST);

            dialog.showAndWait();

            if (dialog.getSelectedItem() != null) {

                PushPanel pushPanel = new PushPanel();
                pushPanel.setHeading((Heading) dialog.getSelectedItem());

                space.addAction(pushPanel);
            }
        }

        private void addPlayerStart(Space space) {

            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("Chose which player should spawn here (1 to 6)");
            dialog.showAndWait();

            if (dialog.getResult() != null) {
                space.setStartPlayerNo(Integer.parseInt(dialog.getResult()));
            }

        }


        private void addWalls(Space space) {

            List<Heading> currentWalls = space.getWalls();
            List<Heading> availableWalls = new ArrayList<>();
            List<Heading> headings = new ArrayList<>();

            headings.add(Heading.NORTH);
            headings.add(Heading.EAST);
            headings.add(Heading.SOUTH);
            headings.add(Heading.WEST);

            for (Heading heading : headings) {
                if ( ! currentWalls.contains(heading)) {
                    availableWalls.add(heading);
                }
            }

            if ( ! availableWalls.isEmpty()) {
                ChoiceDialog dialog = new ChoiceDialog();
                dialog.setContentText("Chose the direction of the wall");
                dialog.getItems().addAll(availableWalls);
                dialog.showAndWait();

                if (dialog.getSelectedItem() != null) {
                    space.addWall((Heading) dialog.getSelectedItem());
                }

            }


        }

        private void addCheckpoint(Space space) {
            for (FieldAction action : space.getActions()) {
                if (action instanceof Checkpoint) {
                    // TODO add some explanation to the user that there is already a checkpoint at this space
                    return;
                }
            }

            TextInputDialog dialog = new TextInputDialog();
            dialog.setContentText("Which number should the checkpoint be (1-4)?");
            dialog.showAndWait();

            if (dialog.getResult() != null) {
                int no = Integer.parseInt(dialog.getResult());

                if (no <= board.getCheckpoints().size()) {
                    String msg = "You have typed a number that already exist, the next checkpoint should be number. " + (board.getCheckpoints().size() + 1);
                    Alert alert = new Alert(Alert.AlertType.WARNING, msg);
                    alert.showAndWait();

                    addCheckpoint(space);
                }

                Checkpoint checkpoint = new Checkpoint(no);
                space.addAction(checkpoint);

            }

        }

        private void addGear(Space space) {
            for (FieldAction action : space.getActions()) {
                if (action instanceof Gear) {
                    // TODO add some explanation to the user that there is already a checkpoint at this space
                    return;
                }
            }

            List<Direction> directions = new ArrayList<>();

            directions.add(Direction.LEFT);
            directions.add(Direction.RIGHT);

            ChoiceDialog dialog = new ChoiceDialog();

            dialog.setContentText("What direction should the gear turn?");
            dialog.getItems().addAll(directions);
            dialog.showAndWait();

            if (dialog.getSelectedItem() != null) {
                space.addGear((Direction) dialog.getSelectedItem());
            }

        }

    }



}

