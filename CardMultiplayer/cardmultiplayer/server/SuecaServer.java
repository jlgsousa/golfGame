package cardmultiplayer.server;

import cardmultiplayer.api.ICard;
import cardmultiplayer.protocol.IMessage;
import cardmultiplayer.protocol.ProtocolConstants;
import cardmultiplayer.protocol.SuecaInputStream;
import cardmultiplayer.protocol.SuecaOutputStream;
import cardmultiplayer.server.pojo.card.Card;
import cardmultiplayer.server.pojo.game.Game;
import cardmultiplayer.server.pojo.player.Player;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static cardmultiplayer.protocol.ProtocolConstants.INVALID_MOVE;
import static cardmultiplayer.protocol.ProtocolConstants.VALID_MOVE;
import static cardmultiplayer.server.pojo.card.Card.Symbol.JOKER;

public class SuecaServer {

    protected static List<SuecaHandler> handlers = new LinkedList<>();
    protected static List<String> names = new ArrayList<>();
    protected static Game game;

    protected static List<ICard> playedCards = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket listener = new ServerSocket(59000)) {
            System.out.println("Sueca server is on");

            acceptConnections(listener, 40);
        }
    }

    private static void acceptConnections(ServerSocket listener, int maxConnections) throws IOException {
        ExecutorService executor = Executors.newFixedThreadPool(maxConnections);

        for (int playerNumber = 1; playerNumber < 5; playerNumber++) {
            SuecaHandler suecaHandler = new SuecaHandler(listener.accept(), playerNumber);
            executor.execute(suecaHandler);

            handlers.add(suecaHandler);
        }
    }

    public static void sendToAll(String message) throws IOException {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setHeader(ProtocolConstants.INFO_TO_ALL);
        serverMessage.setMessage(message);
        for (SuecaHandler handler : handlers) {
            handler.getDataOut().writeMessage(serverMessage);
        }
    }

    public static void startGame() throws IOException {

        System.out.println("Starting game with " + names.size() + " players");
        game = new Game();
        game.start(names);
        game.setDeck();

        List<Player> sortedPlayers = game.getSortedPlayers();

        for (int i = 0; i < handlers.size(); i++) {
            handlers.get(i).setPlayer(sortedPlayers.get(i));
        }

        givePlayersHands(handlers);

        nextPlayer(0);
    }

    public static void nextPlayer(int playerNumber) throws IOException {

        if (playerNumber == 4) {
            System.out.println("\n\n!!FINISH ROUND!!!\n\n");

            game.finishRound();
            playedCards.clear();

            sendToAll("\n=>New round<=\n");
        }

        int nextPlayerNumber = getNextPlayerIndex(playerNumber);

        SuecaHandler nextPlayerHandler = handlers.stream()
                .filter(suecaHandler -> suecaHandler.getPlayer().getOrder().getValue() == nextPlayerNumber)
                .findAny()
                .orElse(new SuecaHandler(null, playerNumber));

        StringBuilder playedCardsSoFar = new StringBuilder();
        synchronized (SuecaServer.playedCards) {
            for (ICard card : SuecaServer.playedCards)  {
                if (card.getSymbol() != JOKER) {
                    playedCardsSoFar.append(card.toString());
                }
            }
        }

        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setHeader(ProtocolConstants.TABLE_CARDS);
        serverMessage.setMessage(playedCardsSoFar.toString());
        nextPlayerHandler.getDataOut().writeMessage(serverMessage);

        ServerMessage yourTurnMessage = new ServerMessage();
        yourTurnMessage.setHeader(ProtocolConstants.YOUR_TURN);
        yourTurnMessage.setMessage("");
        nextPlayerHandler.getDataOut().writeMessage(yourTurnMessage);

    }

    private static int getNextPlayerIndex(int playerIndex) {
        if (playerIndex == 4) {
            return 1;
        }
        return playerIndex + 1;
    }

    private static void givePlayersHands(List<SuecaHandler> handlerList) throws IOException {
        ServerMessage message = new ServerMessage();
        message.setHeader(ProtocolConstants.USER_HAND);
        for (SuecaHandler handler : handlerList) {
            message.setCardHand(handler.getPlayer().getHand());
            handler.getDataOut().writeMessage(message);
        }
    }
}




class SuecaHandler implements Runnable {
    private Player player;

    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private SuecaInputStream dataIn;
    private SuecaOutputStream dataOut;
    private int playerNumber;

    private boolean onGame = true;

    public SuecaHandler(Socket socket, int playerNumber) {
        this.socket = socket;
        this.playerNumber = playerNumber;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public SuecaOutputStream getDataOut() {
        return dataOut;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    @Override
    public void run() {
        try {
            setup();
            askUserName();
            receiveCommand();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setup() throws IOException {
        in = socket.getInputStream();
        out = socket.getOutputStream();
        //ORDER MATTERS!!!
        dataOut = new SuecaOutputStream(out);
        dataIn = new SuecaInputStream(in);
    }

    private void askUserName() throws IOException, ClassNotFoundException {
        ServerMessage serverMessage = new ServerMessage();
        serverMessage.setHeader(ProtocolConstants.USER_NAME_REQUEST);
        serverMessage.setMessage("==>Welcome to the Sueca Game<==\nWhat shall your name be?");

        dataOut.writeMessage(serverMessage);
        Object response = dataIn.readObject();
        String playerName = ((IMessage)response).getMessage();

        synchronized (SuecaServer.names) {
            SuecaServer.names.add(playerName);
            System.out.println("New Player added " + playerName);
            if (SuecaServer.names.size() == 4) {
                SuecaServer.startGame();
            }
        }
    }

    private void receiveCommand() throws Exception {
        while (onGame) {
            IMessage clientMessage = dataIn.readMessage();

            if (ProtocolConstants.PLAYED_CARD.equals(clientMessage.getHeader())) {
                int cardIndex = clientMessage.getPlayedCard();

                ServerMessage serverMessage = new ServerMessage();

                if (Game.isPlayedCardValid(player, player.getHand().get(cardIndex))) {

                    playCard(player, cardIndex);
                    serverMessage.setHeader(VALID_MOVE);

                    SuecaServer.nextPlayer(player.getOrder().getValue());
                } else {
                    serverMessage.setHeader(INVALID_MOVE);
                    serverMessage.setMessage("If you have a card matching trunfo you need to use it!");
                }

                dataOut.writeMessage(serverMessage);
            }

        }
    }

    private void playCard(Player player, int cardIndex) {

        SuecaServer.game.gameRound.getSortedNaipes().add(player.getHand().get(cardIndex).getNaipe());

        SuecaServer.game.gameRound.getPlayedCards().put(player, player.getHand().get(cardIndex));

        synchronized (SuecaServer.playedCards) {
            SuecaServer.playedCards.add(player.getHand().get(cardIndex));
        }

        player.getHand().remove(cardIndex);
    }
}
