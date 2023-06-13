package dk.dtu.compute.se.pisd.roborally.fileaccess.model.templates;


import dk.dtu.compute.se.pisd.roborally.model.CommandCard;
import dk.dtu.compute.se.pisd.roborally.model.CommandCardField;
import dk.dtu.compute.se.pisd.roborally.model.Player;

public class CommandCardFieldTemplate {


    private CommandCard card;

    private boolean visible;

    public CommandCardFieldTemplate fromCommandCardField(CommandCardField commandCardField) {
        this.visible = commandCardField.isVisible();
        this.card = commandCardField.getCard();

        return this;
    }

    public CommandCardField toCommandCardField(Player player) {
        CommandCardField commandCardField = new CommandCardField(player);
        commandCardField.setVisible(visible);
        commandCardField.setCard(card);
        return commandCardField;
    }
}
