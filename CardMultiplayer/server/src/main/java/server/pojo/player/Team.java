package server.pojo.player;

public class Team {
    private Player player1;
    private Player player2;
    private int points;
    private int victories;

    public Team(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        points = 0;
        victories = 0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getVictories() {
        return victories;
    }

    public void addVictories(int roundPoints) {
        if (roundPoints >= 60 && roundPoints < 90) {
            victories++;
        } else if (roundPoints >= 90 && roundPoints < 120) {
            victories+=2;
        } else if (roundPoints >= 120) {
            victories+=4;
        }
    }
}
