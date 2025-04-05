/*
Group Names & Student Numbers:

1.  Full Name: Henrique Custodio
    Student ID: 101497015
2.  Full Name: Jinah Ahn
    Student ID: 100902591
3.  Full Name: Fitsum Asgedom
    Student ID: 101510623

 */

public class GameBoard
{

    private final int BOARD_WIDTH = 9;
    private final int BOARD_HEIGHT = 9;

    public void initBoard(char[][] gameBoard)
    {
        for (int i = 0; i < BOARD_HEIGHT; i++)
        {
            for (int j = 0; j < BOARD_WIDTH; j++)
            {
                gameBoard[i][j] = '.';
            }
        }
    }

    public void drawBoard(char[][] gameBoard)
    {
        System.out.print("    ");
        for (int i = 0; i < BOARD_HEIGHT; i++)
        {
            System.out.print(i + " ");
        }
        System.out.println("\n -----------------------");

        for (int i = 0; i < BOARD_HEIGHT; i++)
        {
            System.out.print(i + " | ");
            for (int j = 0; j < BOARD_WIDTH; j++)
            {
                System.out.print(gameBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isBoardFull(char[][] gameBoard)
    {
        for (int i = 0; i < BOARD_HEIGHT; i++)
        {
            for (int j = 0; j < BOARD_WIDTH; j++)
            {
                if (gameBoard[i][j] == '.')
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkWin(char[][] gameBoard, int row, int col, char symbol)
    {
        return checkDirection(gameBoard, row, col, 0, 1, symbol)
            || checkDirection(gameBoard, row, col, 1, 0, symbol)
            || checkDirection(gameBoard, row, col, 1, 1, symbol)
            || checkDirection(gameBoard, row, col, -1, 1, symbol);
    }

    private boolean checkDirection(char[][] gameBoard, int row, int col, int dRow, int dCol, char symbol)
    {
        int count = 1;
        int r = row + dRow, c = col + dCol;
        while (inBounds(r, c) && gameBoard[r][c] == symbol)
        {
            count++;
            r += dRow;
            c += dCol;
        }

        r = row - dRow;
        c = col - dCol;
        while (inBounds(r, c) && gameBoard[r][c] == symbol)
        {
            count++;
            r -= dRow;
            c -= dCol;
        }

        return count >= 5;
    }

    private boolean inBounds(int row, int col)
    {
        return row >= 0 && row < BOARD_HEIGHT && col >= 0 && col < BOARD_WIDTH;
    }
}
