/*
Group Names & Student Numbers:

1.  Full Name: Henrique Custodio
    Student ID: 101497015
2.  Full Name: Jinah Ahn
    Student ID: 100902591
3.  Full Name: Fitsum Asgedom
    Student ID: 101510623

 */

import java.util.Scanner;

public class GameLogic
{

    private final int BOARD_WIDTH = 9;
    private final int BOARD_HEIGHT = 9;
    private char[][] gameBoard;

    private boolean isSinglePlayer;
    private String[] playerNames = new String[2];
    private char[] playerSymbols = new char[2];
    private int[] playerScores = new int[2];

    private GomokuAI ai;
    private GameBoard gameBoardRender;

    private Scanner scanner;

    public GameLogic(String name, char symbol, char aiSymbol)
    {
        this.isSinglePlayer = true;

        playerNames[0] = name;
        playerSymbols[0] = symbol;
        playerScores[0] = 0;

        playerNames[1] = "Computer";
        playerSymbols[1] = aiSymbol;
        playerScores[1] = 0;

        this.ai = new GomokuAI(aiSymbol, symbol);

        this.scanner = new Scanner(System.in);
        this.gameBoardRender = new GameBoard();
        this.gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        gameBoardRender.initBoard(gameBoard);
    }

    public GameLogic(String name1, char symbol1, String name2, char symbol2)
    {
        this.isSinglePlayer = false;

        playerNames[0] = name1;
        playerNames[1] = name2;
        playerSymbols[0] = symbol1;
        playerSymbols[1] = symbol2;
        playerScores[0] = 0;
        playerScores[1] = 0;

        this.scanner = new Scanner(System.in);
        this.gameBoardRender = new GameBoard();
        this.gameBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];
        gameBoardRender.initBoard(gameBoard);
    }

    public void startTwoPlayer()
    {
        boolean player1Turn = (playerSymbols[0] == 'B');

        while (true)
        {
            gameBoardRender.drawBoard(gameBoard);
            if (player1Turn)
            {
                System.out.println(playerNames[0] + "'s turn (" + playerSymbols[0] + ")");
                int[] move = makeValidMove(playerSymbols[0]);

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], playerSymbols[0]))
                {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 1 Wins!");
                    break;
                }
            }
            else
            {
                System.out.println(playerNames[1] + "'s turn (" + playerSymbols[1] + ")");
                int[] move = makeValidMove(playerSymbols[1]);

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], playerSymbols[1]))
                {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 2 Wins!");
                    break;
                }
            }

            if (gameBoardRender.isBoardFull(gameBoard))
            {
                gameBoardRender.drawBoard(gameBoard);
                System.out.println("Its a draw!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }

    public void startSinglePlayer()
    {
        boolean player1Turn = (playerSymbols[0] == 'B');

        // Loop until win or draw
        while (true)
        {
            gameBoardRender.drawBoard(gameBoard);

            if (player1Turn)
            {
                System.out.println(playerNames[0] + "'s turn (" + playerSymbols[0] + ")");
                int[] move = makeValidMove(playerSymbols[0]);

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], playerSymbols[0])) {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 1 Wins!");
                    break;
                }
            }
            else
            {
                System.out.println("Computers turn (" + playerSymbols[1] + ")");
                System.out.println("Please wait for Computers Turn.");
                int[] move = ai.getBestNextMove(gameBoard);
                gameBoard[move[0]][move[1]] = playerSymbols[1];

                if (gameBoardRender.checkWin(gameBoard, move[0], move[1], playerSymbols[1]))
                {
                    gameBoardRender.drawBoard(gameBoard);
                    System.out.println("Player 2 Wins!");
                    break;
                }
            }

            if (gameBoardRender.isBoardFull(gameBoard))
            {
                gameBoardRender.drawBoard(gameBoard);
                System.out.println("Its a draw!");
                break;
            }

            player1Turn = !player1Turn;
        }
    }

    private int[] makeValidMove(char symbol)
    {
        int row, col;

        while (true)
        {
            try
            {
                System.out.print("Enter row (0-8): ");
                row = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter col (0-8): ");
                col = Integer.parseInt(scanner.nextLine());


                if (row >= 0 && row < 9 && col >= 0 && col < 9 && gameBoard[row][col] == '.')
                {
                    gameBoard[row][col] = symbol;
                    return new int[]{row, col};
                }
                else
                {
                    System.out.println("Invalid Move. Try again.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter valid integers");
            }
        }
    }


    public char[][] getGameBoardCopy()
    {
        char[][] copy = new char[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++)
        {
            System.arraycopy(gameBoard[i], 0, copy[i], 0, BOARD_WIDTH);
        }

        return copy;
    }

}
