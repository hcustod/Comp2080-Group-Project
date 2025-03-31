
// TODO; make this into a working class to be used in Main. Easy encapsulation for player stats.
public class Player {
    private String name;
    private char symbol;
    private int score;

    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void resetScore() {
        this.score = 0;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setName(String name) {
        this.name = name;
    }
}
