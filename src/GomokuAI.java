/*
    This class acts as an interface between the Game Logic and Minimax algo.
    Takes both symbols and uses algo to determine the best move.
 */

public class GomokuAI
{

    private final MinimaxAlgo minimaxAlgo;
    private final char aiSymbol;
    private final char playerSymbol;

    public GomokuAI(char aiSymbol, char playerSymbol)
    {
        this.minimaxAlgo = new MinimaxAlgo();
        this.aiSymbol = aiSymbol;
        this.playerSymbol = playerSymbol;
    }

    // Param here is current state of the game board, and returns an array with best move [row, col]
    public int[] getBestNextMove(char[][] board)
    {
        return minimaxAlgo.findBestMove(board, 3, true, aiSymbol, playerSymbol);
    }

}
