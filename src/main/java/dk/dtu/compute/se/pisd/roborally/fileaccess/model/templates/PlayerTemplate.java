package dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates;

import dk.dtu.compute.se.pisd.roborally.model.*;

import static dk.dtu.compute.se.pisd.roborally.model.Heading.SOUTH;

public class PlayerTemplate {
    public int no;
    private String name;
    private String color;
    private Heading heading = SOUTH;
    private int[] space;
    private CommandCardFieldTemplate[] program;
    private CommandCardFieldTemplate[] cards;

    public PlayerTemplate fromPlayer(Player player) {
        this.no = player.no;
        this.name = player.getName();
        this.color = player.getColor();
        this.heading = player.getHeading();
        this.space = new int[]{player.getSpace().x, player.getSpace().y};
        this.program = new CommandCardFieldTemplate[player.getProgram().length];

        CommandCardField[] commandCardField = player.getProgram();
        for (int i = 0; i < player.getProgram().length; i++) {
            program[i] = new CommandCardFieldTemplate().fromCommandCardField(commandCardField[i]);

        }
        this.cards = new CommandCardFieldTemplate[player.getCards().length];

        CommandCardField[] cardFields = player.getCards();
        for (int i = 0; i < player.getCards().length; i++) {
            cards[i] = new CommandCardFieldTemplate().fromCommandCardField(cardFields[i]);

        }

        return this;

    }

    public Player toPlayer(Board board) {
        Player player = new Player(board, this.color, this.name);
        player.no = this.no;
        player.setHeading(this.heading);


        CommandCardField[] programToPlayer = new CommandCardField[program.length];
        for (int i = 0; i < program.length; i++) {
            programToPlayer[i] = program[i].toCommandCardField(player);

        }
        player.setProgram(programToPlayer);
        CommandCardField[] cardToPlayer = new CommandCardField[cards.length];
        for (int i = 0; i < cards.length; i++) {
            cardToPlayer[i] = cards[i].toCommandCardField(player);

        }
        player.setCards(cardToPlayer);


        return player;
    }
}
