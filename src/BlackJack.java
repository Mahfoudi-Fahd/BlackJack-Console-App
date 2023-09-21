import java.util.ArrayList;
import java.util.Random;

public class BlackJack {

    private class Card{
        String value;
        String type;

        public Card(String value, String type) {
            this.value = value;
            this.type = type;
        }
        public String toString() {
            return value + "-" + type;
        }
    }
    ArrayList<Card> deck;
    Random random = new Random();

    BlackJack(){
        startGame();
    }
    public void startGame(){
        buildDeck();
        shuffleDeck();
    }
    public void buildDeck(){
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }

        System.out.println("BUILD DECK:");
        System.out.println(deck);
    }


    public void  shuffleDeck(){

        int deckSize = deck.size();

        for (int i = 0; i < deckSize; i++) {

            int j = random.nextInt(deckSize);

            Card temp = deck.get(i);
            deck.set(i, deck.get(j));
            deck.set(j, temp);



            }
        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }



}
