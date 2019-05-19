package server.pojo.game;


import server.pojo.player.Team;
import server.pojo.card.Deck;
import server.pojo.player.Player;

import java.util.ArrayList;
import java.util.List;

class Table {

    private Deck deck;
    private Team teamA;
    private Team teamB;
    private GameRound gameRound;

    Deck getDeck() {
        return deck;
    }

    private Team getTeamA() {
        return teamA;
    }

    Team getTeamB() {
        return teamB;
    }

    GameRound getGameRound() {
        return gameRound;
    }

    Table(String[] playerNames) {
        deck = new Deck();

        gameRound = new GameRound();

        teamA = new Team(new Player(playerNames[0], Player.Order.FIRST, gameRound),
                new Player(playerNames[2], Player.Order.THIRD, gameRound));

        teamB = new Team(new Player(playerNames[1], Player.Order.SECOND, gameRound),
                new Player(playerNames[3], Player.Order.FOURTH, gameRound));
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(getTeamA().getPlayer1());
        players.add(getTeamB().getPlayer1());
        players.add(getTeamA().getPlayer2());
        players.add(getTeamB().getPlayer2());

        return players;
    }

    public void setPlayers(String[] playerNames) {
        teamA = new Team(new Player(playerNames[0], Player.Order.FIRST, gameRound),
                new Player(playerNames[2], Player.Order.THIRD, gameRound));

        teamB = new Team(new Player(playerNames[1], Player.Order.SECOND, gameRound),
                new Player(playerNames[3], Player.Order.FOURTH, gameRound));
    }

    void shuffleDeck() {
        deck.shuffle();
    }

//    public void manualShuffleDeck() {
//        deck.manualShuffle();
//    }

    void cutDeck() {
        deck.cut();
    }

    void giveCards() {
        for (int i = 0; i < 40; i += 4) {
            getTeamA().getPlayer1().getHand().add(deck.getCards()[i]);
            getTeamB().getPlayer1().getHand().add(deck.getCards()[i + 1]);
            getTeamA().getPlayer2().getHand().add(deck.getCards()[i + 2]);
            getTeamB().getPlayer2().getHand().add(deck.getCards()[i + 3]);
        }
    }

    boolean noTeamWon() {
        return getTeamA().getVictories() < 4 && getTeamB().getVictories() < 4;
    }

    void reorderPlayers(Player winner) {
        if (getTeamA().getPlayer1() == winner) {
            getTeamA().getPlayer1().setOrder(Player.Order.FIRST);
            getTeamB().getPlayer1().setOrder(Player.Order.SECOND);
            getTeamA().getPlayer2().setOrder(Player.Order.THIRD);
            getTeamB().getPlayer2().setOrder(Player.Order.FOURTH);
        } else if (getTeamB().getPlayer1() == winner) {
            getTeamB().getPlayer1().setOrder(Player.Order.FIRST);
            getTeamA().getPlayer2().setOrder(Player.Order.SECOND);
            getTeamB().getPlayer2().setOrder(Player.Order.THIRD);
            getTeamA().getPlayer1().setOrder(Player.Order.FOURTH);
        } else if (getTeamA().getPlayer2() == winner) {
            getTeamA().getPlayer2().setOrder(Player.Order.FIRST);
            getTeamB().getPlayer2().setOrder(Player.Order.SECOND);
            getTeamA().getPlayer1().setOrder(Player.Order.THIRD);
            getTeamB().getPlayer1().setOrder(Player.Order.FOURTH);
        } else {
            getTeamB().getPlayer2().setOrder(Player.Order.FIRST);
            getTeamA().getPlayer2().setOrder(Player.Order.SECOND);
            getTeamB().getPlayer1().setOrder(Player.Order.THIRD);
            getTeamA().getPlayer1().setOrder(Player.Order.FOURTH);
        }
    }

    Team getWinningTeam(Player winningPlayer) {
        if (getTeamA().getPlayer1() == winningPlayer || getTeamA().getPlayer2() == winningPlayer) {
            return getTeamA();
        } else  {
            return getTeamB();
        }
    }

    String getRoundResultString(Team winningTeam, Player winningPlayer, int vaza) {
        String roundResult="";
        roundResult+=("==============================================================\n");
        roundResult+=("+++++ The round " + vaza + " was won by player " + winningPlayer.getName() + " +++++\n");
        roundResult+=("| " + getGameRound().getTotalPoints() + " Points to attribute to players "
                + winningTeam.getPlayer1().getName() + " and " + winningTeam.getPlayer2().getName() + "|\n");
        roundResult+=("==============================================================\n");
        roundResult+=("\n\n");
        return roundResult;
    }

    public String getGameReportString() {
        String gameReportString = "";
        String array[][] = new String[5][15];
        array[0][0] = array[4][0] = "P";
        array[0][1] = array[4][1] = "a";
        array[0][2] = array[4][2] = "r";
        array[0][4] = "1";
        array[4][4] = "2";
        array[0][5] = array[4][5] = ":";

        gameReportString+=("===================Game Report==================\n");
        int teamAPoints = teamA.getPoints();
        int teamBPoints = teamB.getPoints();

        gameReportString+=("Team A with player " + teamA.getPlayer1().getName() + " and " + teamA.getPlayer2().getName()
                + " finished with " + teamAPoints + " points\n");
        gameReportString+=("Team B with player " + teamB.getPlayer1().getName() + " and " + teamB.getPlayer2().getName()
                + " finished with " + teamBPoints + " points\n");

        gameReportString+=("======================================================\n");
        gameReportString+=("===================General Status=====================\n");
        for (int a = 7; a < teamA.getVictories() + 7; a++) {
            array[0][a] = "O";
            array[1][a] = "|";
            array[2][a] = "-";
        }
        for (int b = 7; b < teamB.getVictories() + 7; b++) {
            array[3][b] = "|";
            array[4][b] = "O";
            array[2][b] = "-";
        }
        for (int line = 0; line < array.length; line++) {
            for (int column = 0; column < array[0].length; column++) {
                if (array[line][column] == null) {
                    array[line][column] = " ";
                }
            }
        }

        for (int column = 0; column < array[0].length; column++) {
            gameReportString+=(array[0][column]);
        }
        gameReportString+=(teamB.getVictories() + " Victories\n");
        for (int column = 0; column < array[0].length; column++) {
            gameReportString+=(array[1][column]);
        }
        gameReportString+=("\n");
        for (int column = 0; column < array[0].length; column++) {
            gameReportString+=(array[2][column]);
        }
        gameReportString+=("\n");
        for (int column = 0; column < array[0].length; column++) {
            gameReportString+=(array[3][column]);
        }
        gameReportString+=("\n");
        for (int column = 0; column < array[0].length; column++) {
            gameReportString+=(array[4][column]);
        }
        gameReportString+=(teamA.getVictories() + " Victories\n");
        gameReportString+=("======================================================\n");

        return gameReportString;
    }

    void updateTeamsVictories() {
        teamA.addVictories(teamA.getPoints());
        teamB.addVictories(teamB.getPoints());
    }

    void resetTeamsPoints() {
        teamA.setPoints(0);
        teamB.setPoints(0);
    }

    public String getFinalResultString() {

        String finalResultString = "";

        if (teamA.getVictories() >= 4 || teamB.getVictories() >= 4 ) {
            finalResultString+=("*********************************\n");
            finalResultString+=("*        " + (teamA.getVictories() >= 4 ? "Congrats Team A":"Congrats Team B") +"        *\n");
            finalResultString+=("*********************************\n");
            finalResultString+=("        _\n");
            finalResultString+=("       @ |\n");
            finalResultString+=("      /  /\n");
            finalResultString+=("     /  <____\n");
            finalResultString+=("    /   (O___)\n");
            finalResultString+=("   /   (@____)\n");
            finalResultString+=("  /    (@____)\n");
            finalResultString+=(" /     _(__o)\n");
            finalResultString+=("/     /  \n");
        }

        return finalResultString;
    }

}
