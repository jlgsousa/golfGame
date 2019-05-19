package client;

import api.ICard;
import api.protocol.message.IMessage;
import api.protocol.ProtocolConstants;
import impl.protocol.SuecaInputStream;
import impl.protocol.SuecaOutputStream;
import impl.protocol.message.Message;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import static api.protocol.ProtocolConstants.*;


public class SuecaClient {
    private Socket socket;
    private SuecaInputStream dataIn;
    private SuecaOutputStream dataOut;
    private List<ICard> hand;


    private int playedCardIndex;

    private boolean gameOver;
    private int round;

    private SuecaClient() throws IOException {
        socket = new Socket("localhost", 59000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        //ORDER MATTERS!!!
        dataOut = new SuecaOutputStream(out);
        dataIn = new SuecaInputStream(in);
        hand = new ArrayList<>();
        gameOver = false;
        round = 1;
    }

    public void setHand(List<ICard> hand) {
        this.hand = hand;
    }

    private void play() throws Exception {
        while (!gameOver) {
            processMessage(dataIn.readMessage());
        }
    }

    private void processMessage(IMessage message) throws Exception {
        String header = Optional.ofNullable(message.getHeader()).orElse("");

        IMessage response = new Message();

        switch (header) {
            case USER_NAME_REQUEST:
                System.out.println(message.getMessage());
                response.setHeader(USER_NAME_RESPONSE);
                response.setMessage(getPlayerName());
                dataOut.writeMessage(response);
                break;
            case USER_HAND:
                hand = message.getCardHand();
                System.out.println("Great, let's go my hand is set");
                break;
            case TABLE_CARDS:
                System.out.println("==========Played Cards=========");
                System.out.println(message.getMessage());
                break;
            case YOUR_TURN:
                printHand();
                playedCardIndex = getCardToPlay(hand);
                response.setHeader(ProtocolConstants.PLAYED_CARD);
                response.setCardIndex(playedCardIndex);
                dataOut.writeMessage(response);
                round++;
                //printLoading
                break;
            case VALID_MOVE:
                hand.remove(playedCardIndex);
                break;
            case INVALID_MOVE:
                System.out.println(message.getMessage());
                printHand();
                playedCardIndex = getCardToPlay(hand);
                response.setHeader(ProtocolConstants.PLAYED_CARD);
                response.setCardIndex(playedCardIndex);
                dataOut.writeMessage(response);
                //printLoading
                break;
            case ROUND_END:
            case SET_END:
                System.out.println(message.getMessage());
                break;
            case GAME_END:
                System.out.println(message.getMessage());
                endGame(((IMessage) dataIn.readObject()).getMessage());
                printCredits();
                break;
            default:
                System.out.println(message.getMessage());
                break;
        }
    }

    public void printHand() {
        System.out.println();
        System.out.println("================Your hand in the round " + round + "================");
        int numberOfCards = hand.size();
        StringBuilder cardNumber = new StringBuilder();
        StringBuilder horizontalLine = new StringBuilder();
        StringBuilder upperSymbol = new StringBuilder();
        StringBuilder suits = new StringBuilder();
        StringBuilder lowerSymbol = new StringBuilder();

        for (int i = 0; i < numberOfCards; i++) {
            cardNumber.append("->").append((i+1)).append("<-  ");
            horizontalLine.append("|---|  ");
            upperSymbol.append("|").append(hand.get(i).getSymbol().getString()).append("  |  ");
            suits.append("|").append(hand.get(i).getNaipe().getString()).append(" ")
                    .append(hand.get(i).getNaipe().getString()).append("|  ");
            lowerSymbol.append("|  ").append(hand.get(i).getSymbol().getString()).append("|  ");

        }
        System.out.println(cardNumber);
        System.out.println(horizontalLine.toString());
        System.out.println(upperSymbol.toString());
        System.out.println(suits.toString());
        System.out.println(lowerSymbol.toString());
        System.out.println(horizontalLine.toString());
    }

    private void printLoading(SuecaInputStream dataIn) throws IOException {
        int dots = 0;
        while (dataIn.available() == 0) {
            System.out.print("");
            dots++;
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            if (dots > 100) {
                System.out.println("");
                dots = 0;
            }
        }
    }

    private String getPlayerName() {
        return new Scanner(System.in).nextLine();
    }

    private int getCardToPlay(List<ICard> hand) {
        int cardNumber;
        String input;

        while (true) {
            System.out.print("Which card to play? ");
            System.out.println("Introduce a number between 1 and " + hand.size());
            input = new Scanner(System.in).next();

            if (!isIntegerParseable(input)) {
                continue;
            }

            cardNumber = Integer.parseInt(input) - 1;
            if (cardNumber >= 0 && cardNumber < hand.size()) {
                System.out.println("\nPlease wait for the other players\n");
                break;
            }
        }

        return cardNumber;
    }

    private boolean isIntegerParseable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    private void endGame(String isWinner) {
        if (Boolean.parseBoolean(isWinner)) {
            System.out.println("*********************************");
            System.out.println("*        Congrats You Won       *");
            System.out.println("*********************************");
            System.out.println("        _");
            System.out.println("       @ |");
            System.out.println("      /  /");
            System.out.println("     /  /____");
            System.out.println("    /   (O___)");
            System.out.println("   /   (@____)");
            System.out.println("  /    (@____)");
            System.out.println(" /      (o__)");
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

        gameOver = true;
    }

    private void printCredits() {
        String creditsHeader = "===========Credits===========";
        String credits = "Game Design & Art: Joao Sousa\n" +
                "Game Development & Editing: Joao Sousa\n" +
                "Playtesters: Sousa Joao";
        String creditsFooter = "=============================";
        System.out.println(creditsHeader);
        System.out.println(credits);
        System.out.println(creditsFooter);
        System.out.println("\n\uD83D\uDE01\uD83D\uDE01 THANK YOU FOR PLAYING \uD83D\uDE01\uD83D\uDE01");
        System.out.println("\uD83E\uDD73\uD83E\uDD73 SEE YOU NEXT TIME \uD83E\uDD73\uD83E\uDD73");
    }

    public static void main(String[] args) throws Exception {
        SuecaClient client = new SuecaClient();
        client.play();
    }
}
