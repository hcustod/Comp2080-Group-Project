import java.util.Scanner;

public class GameLogic {

    private final int BOARD_WIDTH = 9;
    private final int BOARD_HEIGHT = 9;
    private char[][] gameBoard;

    private Player player1;
    private Player player2;
    private boolean isSinglePlayer;

    private GomokuAI ai;
    private GameBoard gameBoardRender;

    private Scanner scanner;

    public GameLogic(String name, char symbol, char aiSymbol) {
        this.player1 = new Player(name, symbol);
        this.player2 = new Player("Computer", aiSymbol);
        this.isSinglePlayer = true;

        this.ai = new GomokuAI(aiSymbol, symbol);

        this.scanner = new Scanner(System.in);
        this.gameBoardRender = new GameBoard();
        this.gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        gameBoardRender.initBoard(gameBoard);
    }

    public GameLogic(String name1, char symbol1, String name2, char symbol2) {
        this.player1 = new Player(name1, symbol1);
        this.player2 = new Player(name2, symbol2);
        this.isSinglePlayer = false;

        this.scanner = new Scanner(System.in);
        this.gameBoardRender = new GameBoard();
        this.gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        gameBoardRender.initBoard(gameBoard);
    }

    public void startTwoPlayer() {
        boolean player1Turn = (player1.getSymbol() == 'B');

        while (true) {
            gameBoardRender.drawBoard(gameBoard);
            if (player1Turn) {
                System.out.println(player1.getName() + "'s turn (" + player1.getSymbol() + ")");
                int[] move = makeValidMove(player1.getSymbol());

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], player1.getSymbol())) {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 1 Wins!");
                    break;
                }
            } else {
                System.out.println(player2.getName() + "'s turn (" + player2.getSymbol() + ")");
                int[] move = makeValidMove(player2.getSymbol());

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], player2.getSymbol())) {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 2 Wins!");
                    break;
                }
            }

            if (gameBoardRender.isBoardFull(gameBoard)) {
                gameBoardRender.drawBoard(gameBoard);
                System.out.println("Its a draw!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }

    public void startSinglePlayer() {
        boolean player1Turn = (player1.getSymbol() == 'B');

        while (true) {
            gameBoardRender.drawBoard(gameBoard);

            if (player1Turn) {
                System.out.println(player1.getName() + "'s turn (" + player1.getSymbol() + ")");
                int[] move = makeValidMove(player1.getSymbol());

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], player1.getSymbol())) {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 1 Wins!");
                    break;
                }
            } else {
                System.out.println("Computers turn (" + player2.getSymbol() + ")");
                System.out.println("Please wait for Computers Turn.");
                int[] move = ai.getBestNextMove(gameBoard);
                gameBoard[move[0]][move[1]] = player2.getSymbol();

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], player2.getSymbol())) {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 2 Wins!");
                    break;
                }
            }

            if (gameBoardRender.isBoardFull(gameBoard)) {
                gameBoardRender.drawBoard(gameBoard);
                System.out.println("Its a draw!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }

    private int[] makeValidMove(char symbol) {
        int row, col;

        while (true) {
            try {
                System.out.print("Enter row (0-8): ");
                row = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter col (0-8): ");
                col = Integer.parseInt(scanner.nextLine());


                if (row >= 0 && row < 9 && col >= 0 && col < 9 && gameBoard[row][col] == '.') {
                    gameBoard[row][col] = symbol;
                    return new int[]{row, col};
                } else {
                    System.out.println("Invalid Move. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid integers");
            }
        }
    }



    public char[][] getGameBoardCopy() {
        char[][] copy = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            System.arraycopy(gameBoard[i], 0, copy[i], 0, BOARD_WIDTH);
        }

        return copy;
    }

}
