package dk.dtu.compute.se.pisd.roborally.model;
import java.util.ArrayList;




    public class Deck {
        private ArrayList<CommandCard> cards;
        private ArrayList<CommandCard> discardPile;
        public Deck() {
            cards = new ArrayList<>();
            discardPile = new ArrayList<>();
            makeDeck();
        }

        private void makeDeck() {
            this.cards=discardPile;
            while(discardPile.size()>0){discardPile.remove(0);}
            //  Add FORWARD cards
            for (int i = 0; i < 5; i++) {
                cards.add(new CommandCard(Command.FORWARD));
            }

            //  Add LEFT cards
            for (int i = 0; i < 3; i++) {
                cards.add(new CommandCard(Command.LEFT));
            }

            //  Add RIGHT cards
            for (int i = 0; i < 3; i++) {
                cards.add(new CommandCard(Command.RIGHT));
            }

            for (int i = 0; i < 1; i++) {
                cards.add(new CommandCard(Command.uTurn));
            }

            for (int i = 0; i < 2; i++) {
                cards.add(new CommandCard(Command.AGAIN));
            }

            for (int i = 0; i < 1; i++) {
                cards.add(new CommandCard(Command.BACK));
            }
            for (int i = 0; i < 1; i++) {
                cards.add(new CommandCard(Command.Move_3));}

            for (int i = 0; i < 2; i++) {
                cards.add(new CommandCard(Command.FASTFORWARD));}


            shuffle();
        }

        public void shuffle() {
            ArrayList<CommandCard> shuffleddeck = new ArrayList<>();
            while(cards.size()>0)
            {
                int index = (int) (Math.random() * cards.size());
                shuffleddeck.add(cards.remove(index));
            }
            cards=shuffleddeck;
        }

        public CommandCard drawCard() {
            if (cards.isEmpty()) {
                makeDeck();
            }
            return cards.remove(0);
        }

        public void addCard(CommandCard card) {
            cards.add(card);
        }


    }