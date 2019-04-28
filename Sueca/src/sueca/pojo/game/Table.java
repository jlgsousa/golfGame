package src.sueca.pojo.game;

import src.sueca.pojo.card.Deck;
import src.sueca.pojo.player.Player;
import src.sueca.pojo.player.Team;

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

    List<Player> getPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(getTeamA().getPlayer1());
        players.add(getTeamB().getPlayer1());
        players.add(getTeamA().getPlayer2());
        players.add(getTeamB().getPlayer2());

        return players;
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

    void showRoundResults(Team winningTeam, Player winningPlayer, int vaza) {
        System.out.println("==============================================================");
        System.out.println("+++++ The round " + vaza + " was won by player " + winningPlayer.getName() + " +++++");
        System.out.println("| " + getGameRound().getTotalPoints() + " Points to attribute to players "
                + winningTeam.getPlayer1().getName() + " and " + winningTeam.getPlayer2().getName() + "|");
        System.out.println("==============================================================");
        System.out.println(" ");
        System.out.println(" ");
    }

    void showGameReport() {
        String array[][] = new String[5][15];
        array[0][0] = array[4][0] = "P";
        array[0][1] = array[4][1] = "a";
        array[0][2] = array[4][2] = "r";
        array[0][4] = "1";
        array[4][4] = "2";
        array[0][5] = array[4][5] = ":";

        System.out.println("===================Game Report==================");
        int teamAPoints = teamA.getPoints();
        int teamBPoints = teamB.getPoints();
        if (teamAPoints > teamBPoints) {
            System.out.println("You and your partner lost with " + teamBPoints + " points");
            System.out.println("Your opponents won with " + teamAPoints + " points");
        } else if (teamAPoints < teamBPoints) {
            System.out.println("You and your partner won and achieved " + teamBPoints + " points");
            System.out.println("Your opponents lost with " + teamAPoints + " points");
        } else {
            System.out.println("You and your partner draw with " + teamAPoints + " points");
        }

        System.out.println("======================================================");
        System.out.println("===================General Status=====================");
        for (int a = 7; a < teamB.getVictories() + 7; a++) {
            array[0][a] = "O";
            array[1][a] = "|";
            array[2][a] = "-";
        }
        for (int b = 7; b < teamA.getVictories() + 7; b++) {
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
            System.out.print(array[0][column]);
        }
        System.out.println(teamB.getVictories() + " Victories");
        for (int column = 0; column < array[0].length; column++) {
            System.out.print(array[1][column]);
        }
        System.out.println(" ");
        for (int column = 0; column < array[0].length; column++) {
            System.out.print(array[2][column]);
        }
        System.out.println(" ");
        for (int column = 0; column < array[0].length; column++) {
            System.out.print(array[3][column]);
        }
        System.out.println(" ");
        for (int column = 0; column < array[0].length; column++) {
            System.out.print(array[4][column]);
        }
        System.out.println(teamA.getVictories() + " Victories");
        System.out.println("======================================================");
    }

    private void showFinalResult(boolean won) {
        if (won) {
            System.out.println("*********************************");
            System.out.println("*        Congrats You Won       *");
            System.out.println("*********************************");
            System.out.println("        _");
            System.out.println("       @ |");
            System.out.println("      /  /");
            System.out.println("     /  <____");
            System.out.println("    /   (O___)");
            System.out.println("   /   (@____)");
            System.out.println("  /    (@____)");
            System.out.println(" /     _(__o)");
            System.out.println("/     /  ");

        } else {
            System.out.println("*********************************");
            System.out.println("*            Game Over          *");
            System.out.println("*********************************");
            System.out.println("        __");
            System.out.println("       /..|__      ");
            System.out.println("      /      |");
            System.out.println("  _  | . ____/   _");
            System.out.println("_| |___|__|_____| |_");
            System.out.println(" |_|            |_| ");

        }
    }

    void updateTeamsVictories() {
        teamA.addVictories(teamA.getPoints());
        teamB.addVictories(teamB.getPoints());
    }

    void resetTeamsPoints() {
        teamA.setPoints(0);
        teamB.setPoints(0);
    }

    void showFinalResult() {
        if (teamB.getVictories() >= 4) {
            showFinalResult(true);
        } else if (teamA.getVictories() >= 4) {
            showFinalResult(false);
        }
    }

}
