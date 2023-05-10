package rockpaperscissors;

public class Player {
    private java.lang.String name;
    private int score;

    public Player(java.lang.String name, int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void increaseScore(int score) {

        this.score += score;
    }
}
