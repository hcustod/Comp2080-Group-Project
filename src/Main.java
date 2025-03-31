import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Console 9x9 Gomoku!");

        // Selecting game mode menu
        while (true) {
            System.out.println("Select Game Mode: ");
            System.out.println("1. 1 Player (Human vs AI)");
            System.out.println("2. 2 Player (Human vs Human)");
            System.out.println("3. Exit Game");

            System.out.println("Enter your choice: ");

            int userChoice = 0;

            try {
                userChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("You did not enter in a number (1-3).");
            }

            switch (userChoice) {
                case 1:
                    System.out.print("Enter player name: ");
                    String playerName = scanner.nextLine();

                    char playerSymbol = ' ';

                    // Setting symbols
                    // Choosing symbols menu
                    while (playerSymbol != 'B' && playerSymbol != 'W') {
                        System.out.print("Enter player symbol (B for Black and W for White): ");
                        playerSymbol = scanner.next().toUpperCase().charAt(0);
                    }

                    char aiSymbol;

                    if (playerSymbol == 'B') {
                        aiSymbol = 'W';
                    } else {
                        aiSymbol = 'B';
                    }

                    GameLogic gameBoard = new GameLogic(playerName, playerSymbol, aiSymbol);
                    gameBoard.startSinglePlayer();
                    break;

                case 2:
                    System.out.print("Enter player 1's name: ");
                    String player1Name= scanner.nextLine();

                    System.out.print("Enter player 2's name: ");
                    String player2Name= scanner.nextLine();

                    char player1Symbol = ' ';
                    char player2Symbol = ' ';

                    while (player1Symbol != 'B' && player1Symbol != 'W') {
                        System.out.print("Enter Player 1's symbol. Player 2 will be the other color (B for Black and W for White): ");
                        player1Symbol = scanner.next().toUpperCase().charAt(0);
                    }

                    if (player1Symbol == 'B') {
                        player2Symbol = 'W';
                    } else {
                        player2Symbol = 'B';
                    }

                    GameLogic gameBoard2 = new GameLogic(player1Name, player1Symbol, player2Name, player2Symbol);
                    gameBoard2.startTwoPlayer();
                    break;

                case 3:
                    System.out.println("Thank you for playing!");
                    break;

                default:
                    System.out.println("You did not enter a valid option.");
                    break;

            }

            scanner.close();
        }
    }

}
