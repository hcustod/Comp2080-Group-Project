/*
Group Names & Student Numbers:

1.  Full Name: Henrique Custodio
    Student ID: 101497015
2.  Full Name: Jinah Ahn
    Student ID: 100902591
3.  Full Name: Fitsum Asgedom
    Student ID: 101510623

 */

import java.util.Random;

public class MinimaxAlgo
{

    private final GameBoard gameBoard = new GameBoard();
    private final int DEPTH = 3;

    public int minimax(char[][] board, int depth, boolean max, char aiSymbol, char playerSymbol, int alpha, int beta)
    {
        if (winningMove(board, aiSymbol))
        {
            return Integer.MAX_VALUE - depth;
        }
        if (winningMove(board, playerSymbol))
        {
            return Integer.MIN_VALUE + depth;
        }
        if (depth == 0 || gameBoard.isBoardFull(board))
        {
            return evaluateBoard(board, aiSymbol, playerSymbol);
        }

        if (max)
        {
            int maxEval = Integer.MIN_VALUE;
            for (int i = 0; i < board.length; i++)
            {
                for (int j = 0; j < board[0].length; j++)
                {
                    if (board[i][j] == '.')
                    {
                        board[i][j] = aiSymbol;
                        int eval = minimax(board, depth - 1, false, aiSymbol, playerSymbol, alpha, beta);
                        board[i][j] = '.';
                        maxEval = Math.max(maxEval, eval);
                        alpha = Math.max(alpha, eval);
                        if (beta <= alpha)
                        {
                            break;
                        }
                    }
                }
            }
            return maxEval;
        }
        else
        {
            int minEval = Integer.MAX_VALUE;
            for (int i = 0; i < board.length; i++)
            {
                for (int j = 0; j < board[0].length; j++)
                {
                    if (board[i][j] == '.')
                    {
                        board[i][j] = playerSymbol;
                        int eval = minimax(board, depth - 1, true, aiSymbol, playerSymbol, alpha, beta);
                        board[i][j] = '.';
                        minEval = Math.min(minEval, eval);
                        beta = Math.min(beta, eval);
                        if (beta <= alpha)
                        {
                            break;
                        }
                    }
                }
            }
            return minEval;
        }
    }

    public int[] findBestMove(char[][] board, int depth, boolean isMax, char aiSymbol, char playerSymbol)
    {

        // Check for any immediate winning moves
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (board[i][j] == '.')
                {
                    board[i][j] = aiSymbol;
                    if (gameBoard.checkWin(board, i, j, aiSymbol))
                    {
                        board[i][j] = '.';
                        return new int[]{i, j};
                    }

                    board[i][j] = '.';
                }
            }
        }

        // Check for player winning move and block it
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[0].length; j++)
            {
                if (board[i][j] == '.')
                {
                    board[i][j] = playerSymbol;
                    if (gameBoard.checkWin(board, i, j, playerSymbol))
                    {
                        board[i][j] = '.';
                        return new int[]{i, j};
                    }
                    board[i][j] = '.';
                }
            }
        }

        // Minimax to find best scoring move
        int bestScore = Integer.MIN_VALUE;
        int[][] bestMove = new int[81][2];
        int count = 0;

        for(int row = 0; row < board.length; row++)
        {
            for(int col = 0; col < board[0].length; col++)
            {
                if(board[row][col] == '.')
                {
                    board[row][col] = aiSymbol;
                    int tempScore = minimax(board, DEPTH, false, aiSymbol, playerSymbol, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[row][col] = '.';

                    if (tempScore > bestScore)
                    {
                        bestScore = tempScore;
                        bestMove[0][0] = row;
                        bestMove[0][1] = col;
                        count = 1;
                    }
                    else if (tempScore == bestScore)
                    {
                        bestMove[count][0] = row;
                        bestMove[count][1] = col;
                        count++;
                    }

                }
            }
        }

        int randomIndex = new Random().nextInt(count);

        return new int[]{bestMove[randomIndex][0], bestMove[randomIndex][1]};
    }

    private boolean winningMove(char[][] board, char symbol)
    {
        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[0].length; col++)
            {
                if (board[row][col] == symbol && gameBoard.checkWin(board, row, col, symbol))
                {
                    return true;
                }
            }
        }

        return false;
    }


    private int evaluateBoard(char[][] board, char aiSymbol, char playerSymbol)
    {
        int score = 0;

        for (int row = 0; row < board.length; row++)
        {
            for (int col = 0; col < board[0].length; col++)
            {
                if (board[row][col] != '.')
                {
                    char currentSymbol = board[row][col];
                    int value = evaluatePosition(board, row, col, currentSymbol);
                    if (currentSymbol == aiSymbol)
                    {
                        // Favour the AI's move more heavily
                        score += value * 2;
                    }
                    else if (currentSymbol == playerSymbol)
                    {
                        score -= value;
                    }
                }
            }
        }

        return score;
    }

    private int evaluatePosition(char[][] board, int row, int col, char symbol)
    {

        int score = 0;
        int[][] directions = {{0, 1}, {1, 0}, {1, 1}, {1, -1}};

        for (int[] direction : directions)
        {
            int count = 1;
            int openEnds = 0;

            int r = row + direction[0];
            int c = col + direction[1];

            while (inBounds(board, r, c) && board[r][c] == symbol)
            {
                count++;
                r = r + direction[0];
                c = c + direction[1];
            }
            if (inBounds(board, r, c) && board[r][c] == '.')
            {
                openEnds++;
            }

            r = row - direction[0];
            c = col - direction[1];
            while (inBounds(board, r, c) && board[r][c] == symbol)
            {
                count++;
                r = r - direction[0];
                c = c - direction[1];
            }
            if (inBounds(board, r, c) && board[r][c] == '.')
            {
                openEnds++;
            }

            score += scorePositions(count, openEnds);

        }

        return score;

    }

    private int scorePositions(int count, int openEnds)
    {
        if (count >= 5)
            return 10000;
        else if (count == 4 && openEnds == 2)
            return 500;
        else if (count == 4 && openEnds == 1)
            return 200;
        else if (count == 3 && openEnds == 2)
            return 100;
        else if (count == 3 && openEnds == 1)
            return 50;
        else if (count == 2 && openEnds == 2)
            return 25;
        else if (count == 2 && openEnds == 1)
            return 5;
        else
            return 0;
    }


    private boolean inBounds(char[][] board, int row, int col)
    {
        return row >= 0 && row < board.length && col >= 0 && col < board[0].length;
    }

}
