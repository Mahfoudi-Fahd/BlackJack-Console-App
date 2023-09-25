import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
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

            if ("AJQK".contains(value)) {
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

        public String getImagePath() {
            return "/cards/" + toString() + ".png";
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

    int cardWidth= 110;
    int cardHeight=154;

    JFrame frame = new JFrame("Black Jack");
    JPanel gamePanel = new JPanel(){
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
try {
    Image hiddenCardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("/cards/BACK.png"))).getImage();
    g.drawImage(hiddenCardImg, 20, 20, cardWidth-5, cardHeight, null);

//    DrawdealerHand
    for (int i = 1; i <= dealerHand.size() ; i++) {
        Card card = dealerHand.get(i-1);
        Image cardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(card.getImagePath()))).getImage();
        g.drawImage(cardImg, 20 + ( cardWidth )*i, 20 ,cardWidth-5 , cardHeight,null );
    }
//    DrawPlayerHand
    for (int i = 0; i < playerHand.size(); i++) {
        Card card = playerHand.get(i);
        Image cardImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(card.getImagePath()))).getImage();
        g.drawImage(cardImg , 20 + ( cardWidth )*i , 280 , cardWidth-5 , cardHeight,null);

    }
}catch (Exception e){
    e.printStackTrace();
}
        }
    };
    JPanel buttonPanel = new JPanel();
    JButton hitButton = new JButton("Hit");


    JButton standButton = new JButton("Stand");


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
//        JLabel imageHolder = new JLabel();
//        imageHolder.setIcon(makeImageIcon("Background.png"));

        //Add image to panel, add panel to frame
//        gamePanel.add(imageHolder);

        frame.add(gamePanel);
        frame.setResizable(false);
        frame.setSize(boardWidth , boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
//        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(102,0,51));
        hitButton.setFocusable(false);
        buttonPanel.add(hitButton);
        standButton.setFocusable(false);
        hitButton.setBackground(Color.BLACK);
        hitButton.setForeground(Color.white);
        standButton.setBackground(Color.BLACK);
        standButton.setForeground(Color.white);
        buttonPanel.add(standButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        hitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Card card =deck.remove(0);
                playerSum += card.getValue(dealerSum);
                playerAceCount += card.isAce() ? 1 : 0;
                playerHand.add(card);
                if (reducePlayerAce()==21 ){
                    hitButton.setEnabled(false);
                    standButton.setEnabled(false);
                    while (dealerSum<17) {
                        card = deck.remove(0);
                        dealerSum += card.getValue(dealerSum);
                        dealerAceCount += card.isAce() ? 1 : 0;
                        dealerHand.add(card);
                    }

                } else if (reducePlayerAce()>21) {
                    hitButton.setEnabled(false);
                    standButton.setEnabled(false);
                }
                gamePanel.repaint();

            }
        });

        standButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hitButton.setEnabled(false);
                standButton.setEnabled(false);
                while (dealerSum<17){
                    Card card = deck.remove(0);
                    dealerSum += card.getValue(dealerSum);
                    dealerAceCount+= card.isAce()? 1 : 0;
                    dealerHand.add(card);

                }
                gamePanel.repaint();
            }
        });

        gamePanel.repaint();

    }
    public void startGame(){
    // Deck
        buildDeck("A","C");
        shuffleDeck();

    // Dealer
        dealerHand = new ArrayList<Card>();
        dealerSum = 0;
        dealerAceCount= 0;

        hiddenCard = deck.remove(0);//remove the Card selected
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
            card = deck.remove(0);
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

public int reducePlayerAce(){
        while (playerSum>21 && playerAceCount >0){
            playerSum -= 10;
            playerAceCount -=1;

        }
    System.out.println(playerSum);
        return playerSum;
}

    public int reduceDealerAce() {
        while (dealerSum>21 && dealerAceCount >0){
            dealerSum -= 10;
            dealerAceCount -=1;

        }
        return dealerSum;
    }
}
