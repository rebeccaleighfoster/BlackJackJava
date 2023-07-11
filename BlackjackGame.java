import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BlackjackGame {

    // Card class representing a playing card
    public static class Card {
        private String rank;
        private String suit;
        private int value;

        public Card(String rank, String suit, int value) {
            this.rank = rank;
            this.suit = suit;
            this.value = value;
        }

        public String getRank() {
            return rank;
        }

        public String getSuit() {
            return suit;
        }

        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return rank + " of " + suit;
        }
    }

    // Player class representing a player in the game
    public static class Player {
        private String name;
        private List<Card> hand; // Player's hand containing Card objects
        private boolean stay; // Flag indicating if the player has chosen to stay

        public Player(String name) {
            this.name = name;
            this.hand = new ArrayList<>();
            this.stay = false;
        }

        // Displays the player's hand and its total value
        public void displayHand() {
            System.out.println(name + "'s hand:");
            for (Card card : hand) {
                System.out.println(card.toString());
            }
            System.out.println("Hand Value: " + calculateHandValue(hand));
            System.out.println();
        }

        public List<Card> getHand() {
            return hand;
        }

        // Asks the player if they want another card (hit) or want to stay
        public boolean wantsToHit() {
            int handValue = calculateHandValue(hand);
            if (handValue > 21) {
                System.out.println("Hand value exceeds 21. " + name + " loses.");
                stay = true;
                return false;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print(name + ", do you want another card? (hit/stay): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("hit")) {
                return true;
            } else if (choice.equals("stay")) {
                stay = true;
                return false;
            } else {
                System.out.println("Invalid choice. Please enter 'hit' or 'stay'.");
                return wantsToHit();
            }
        }

        // Checks if the player has chosen to stay
        public boolean hasStayed() {
            return stay;
        }
    }

    //  instructions() Introduce the game and rules briefly using a function
    public static void instructions() {
        System.out.println("Welcome to Blackjack!");
        System.out.println("The goal is to get a hand value as close to 21 as possible without going over.");
        System.out.println("The player with the highest hand value <= 21 wins.");
        System.out.println("If a player's hand value goes over 21, they bust and lose.");
        System.out.println("Let's get started!");
    }

    // Creates a deck of cards and returns it as a List
    public static List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        List<String> suits = Arrays.asList("clubs", "<3", "<>", "spades");
        List<String> ranks = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "Ace", "Jack", "Queen", "King");

        for (String suit : suits) {
            for (String rank : ranks) {
                int value;
                if (rank.equals("Ace")) {
                    value = 11;
                } else if (rank.equals("King") || rank.equals("Queen") || rank.equals("Jack")) {
                    value = 10;
                } else {
                    value = Integer.parseInt(rank);
                }
                Card card = new Card(rank, suit, value);
                deck.add(card);
            }
        }

        return deck;
    }

    // Shuffles the deck of cards
    public static void shuffleDeck(List<Card> deck) {
        Collections.shuffle(deck);
    }

    // Deals a card from the deck and removes it
    public static Card deal(List<Card> deck) {
        return deck.remove(0);
    }

    // Displays the player's hand and its total value
    public static void displayHand(String playerName, List<Card> hand) {
        System.out.println(playerName + "'s hand:");
        for (Card card : hand) {
            System.out.println(card.toString());
        }
        System.out.println("Hand Value: " + calculateHandValue(hand));
        System.out.println();
    }

    // Calculates the total value of a hand, considering Ace values
    public static int calculateHandValue(List<Card> hand) {
        int handValue = 0;
        int aceCount = 0;

        for (Card card : hand) {
            handValue += card.getValue();
            if (card.getRank().equals("Ace")) {
                aceCount++;
            }
        }

        while (handValue > 21 && aceCount > 0) {
            handValue -= 10;
            aceCount--;
        }

        return handValue;
    }

    // Determines if the dealer should hit or stay based on the hand value
    public static boolean dealerHitOrStay(List<Card> hand) {
        int handValue = calculateHandValue(hand);
        if (handValue <= 16) {
            return true; // HIT
        } else {
            return false; // STAY
        }
    }

    public static void main(String[] args) {
        // Code for starting the game
        instructions();

        List<Card> deck = createDeck();
        shuffleDeck(deck);

        System.out.println("Dealing cards...");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();
        Player player1 = new Player(playerName);
        Player player2 = new Player("Dealer");

        // Initial dealing of two cards to each player
        player1.getHand().add(deal(deck));
        player2.getHand().add(deal(deck));
        player1.getHand().add(deal(deck));
        player2.getHand().add(deal(deck));

        player1.displayHand();
        player2.displayHand();

        // Player 1's turn
        while (!player1.hasStayed() && player1.wantsToHit()) {
            player1.getHand().add(deal(deck));
            player1.displayHand();
        }

        // Player 2's turn (dealer)
        while (!player2.hasStayed() && dealerHitOrStay(player2.getHand())) {
            player2.getHand().add(deal(deck));
            player2.displayHand();
        }

        // Determine the winners
        int player1HandValue = calculateHandValue(player1.getHand());
        int player2HandValue = calculateHandValue(player2.getHand());

        System.out.println("Final Results:");
        player1.displayHand();
        player2.displayHand();

        // Check for bust or compare hand values
        if (player1HandValue > 21 && player2HandValue > 21) {
            System.out.println("Both players bust. It's a draw.");
        } else if (player1HandValue > 21) {
            System.out.println(player1.name + " busts. " + player2.name + " wins!");
        } else if (player2HandValue > 21) {
            System.out.println(player2.name + " busts. " + player1.name + " wins!");
        } else {
            if (player1HandValue > player2HandValue) {
                System.out.println(player1.name + " wins!");
            } else if (player2HandValue > player1HandValue) {
                System.out.println(player2.name + " wins!");
            } else {
                System.out.println("It's a draw!");
            }
        }
    }
}

