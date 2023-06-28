import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlackjackGame {

    public static class Player {
        private String name;
        private int handValue;

        public Player(String name) {
            this.name = name;
            this.handValue = 0;
        }

        public void updateHandValue(int value) {
            this.handValue += value;
        }

        public String toString() {
            return "Player Name: " + name + "\nHand Value: " + handValue;
        }
    }

    public static void instructions() {
        System.out.println("Welcome to Blackjack!");
        System.out.println("The goal is to get a hand value as close to 21 as possible without going over.");
        System.out.println("The player with the highest hand value <= 21 wins.");
        System.out.println("If a player's hand value goes over 21, they bust and lose.");
        System.out.println("Let's get started!");
    }

    public static List<String> createDeck() {
        List<String> deck = new ArrayList<>();
        List<String> suits = Arrays.asList("Spades", "<3", "<>", "CLubs");
        List<String> ranks = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "Ace", "Jack", "Queen", "King");

        for (String suit : suits) {
            for (String rank : ranks) {
                deck.add(rank + " of " + suit);
            }
        }

        return deck;
    }

    public static void shuffleDeck(List<String> deck) {
        Collections.shuffle(deck);
    }

    public static String deal(List<String> deck) {
        return deck.remove(0);
    }

    public static void main(String[] args) {
        // Code for starting the game
        instructions();

        List<String> deck = createDeck();
        shuffleDeck(deck);

        System.out.println("Dealing cards...");
        String card1 = deal(deck);
        String card2 = deal(deck);

        Player player1 = new Player("Pocket");
        Player player2 = new Player("Atlas");

        player1.updateHandValue(getCardValue(card1));
        player2.updateHandValue(getCardValue(card2));

        System.out.println("Player 1: " + card1);
        System.out.println(player1);
        System.out.println();
        System.out.println("Player 2: " + card2);
        System.out.println(player2);
    }

    private static int getCardValue(String card) {
        String rank = card.split(" ")[0];
        if (rank.equals("Ace")) {
            return 11;
        } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
            return 10;
        } else {
            return Integer.parseInt(rank);
        }
    }
}

