import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class BlackJack {

    private static class Card{
        String value;
        String type;

        public Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }

        public int getValue(int currentSum) {
            if (isAce() && currentSum + 11 > 21) {
                return 1;  // Ace should be 1 if it prevents going over 21
            } else if ("AJQK".contains(value)) {
                if (value.equals("A")) {
                    return 11;
                }
                return 10;
            }
            return Integer.parseInt(value);
        }

        public boolean isAce(){
            return value.equals("A");
        }
    }

    ArrayList<Card> deck;
    Random random = new Random();


//    Dealer
    Card hiddenCard;
    ArrayList<Card> dealerHand;
    int dealerSum;
    int dealerAceCount;

    //    Player
    ArrayList<Card> playerHand;
    int playerSum;
    int playerAceCount;

    //  Window
    int boardWidth =900;
    int boardHeight=525;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel();

    public static ImageIcon makeImageIcon(String filename) {
        BufferedImage myPicture;
        try {

            myPicture = ImageIO.read(new File("src/"+filename));
            return new ImageIcon(myPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    BlackJack(){
        startGame();

        //Create image
        JLabel imageHolder = new JLabel();
        imageHolder.setIcon(makeImageIcon("Background.png"));

        //Add image to panel, add panel to frame
        gamePanel.add(imageHolder);

        frame.add(gamePanel);
        frame.setResizable(false);
        frame.setSize(boardWidth , boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        gamePanel.setLayout(new BorderLayout());
//        gamePanel.setBackground(new Color(102,0,102));

    }
    public void startGame(){
    // Deck
        buildDeck("A","C");
        shuffleDeck();

    // Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount= 0;

        hiddenCard = deck.remove(0);
        dealerSum += hiddenCard.getValue(dealerSum);
        dealerAceCount += hiddenCard.isAce() ? 1 : 0;

        Card card = deck.remove(0);
        dealerSum += card.getValue(dealerSum);
        dealerAceCount += card.isAce() ? 1 : 0;
        dealerHand.add(card);

        System.out.println("\n-----------------------\nDealer\n-----------------------");
        System.out.println(hiddenCard);
        System.out.println(dealerHand);
        System.out.println(dealerSum);
        System.out.println(dealerAceCount);

    //  Player
        playerHand = new ArrayList<Card>();
        playerSum = 0;
        playerAceCount = 0;

        for (int i = 0; i<2 ; i++){
            card = deck.remove(deck.size()-1);
            playerSum += card.getValue(dealerSum);
            playerAceCount +=  card.isAce() ? 1: 0;
            playerHand.add(card);
        }
        System.out.println("PLAYER :");
        System.out.println(playerHand);
        System.out.println(playerSum);
        System.out.println(playerAceCount);

    }
//    public void buildDeck(){
//        deck = new ArrayList<Card>();
//        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
//        String[] types = {"C", "D", "H", "S"};
//
//        for (int i = 0; i < types.length; i++) {
//            for (int j = 0; j < values.length; j++) {
//                Card card = new Card(values[j], types[i]);
//                deck.add(card);
//            }
//        }
//
//        System.out.println("BUILD DECK:");
//        System.out.println(deck);
//    }


    public void buildDeck(String minValue, String minType) {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        boolean startAdding = false;

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                if (types[i].equals(minType) && values[j].equals(minValue)) {
                    startAdding = true;
                }

                if (startAdding) {
                    Card card = new Card(values[j], types[i]);
                    deck.add(card);
                }
            }
        }

        System.out.println("BUILD DECK AFTER " + minValue + "-" + minType + ":");
        System.out.println(deck);
    }







    public void  shuffleDeck(){

        int deckSize = deck.size();

        for (int i = 0; i <100 ; i++) {

            int randomIndex = random.nextInt(deckSize); //generate Random
            System.out.println(randomIndex);
            Card randomCard = deck.get(randomIndex); //get

            deck.remove(randomIndex); //remove

            deck.add(0, randomCard); //add to the top
        }
        System.out.println("AFTER SHUFFLE");
        System.out.println(deck);
    }



}
