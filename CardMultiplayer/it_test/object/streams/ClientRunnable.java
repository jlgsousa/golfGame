package it_test.object.streams;

import java.io.*;
import java.net.Socket;

public class ClientRunnable implements Runnable {
    private Socket socket;
    private ObjectOutputStream dataOut;
    private ObjectInputStream dataIn;

    public ClientRunnable() throws IOException {
        socket = new Socket("localhost", 59000);
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        dataIn = new ObjectInputStream(in);
        dataOut = new ObjectOutputStream(out);
    }

    @Override
    public void run() {
        try {
            Message messageServer = (Message) dataIn.readObject();
            System.out.println(messageServer);

            synchronized (ServerRunnable.serverList) {
                ServerRunnable.serverList.add(messageServer);
            }
            dataOut.writeUTF("Thanks bro, bye");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
