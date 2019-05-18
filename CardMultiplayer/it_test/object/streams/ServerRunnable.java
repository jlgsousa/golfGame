package it_test.object.streams;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static junit.framework.TestCase.fail;

public class ServerRunnable implements Runnable {

    private int nClients = 4;

    public static final List<Message> serverList = new ArrayList<>();

    @Override
    public void run() {

        try (ServerSocket listener = new ServerSocket(59000)) {
            ExecutorService executor = Executors.newFixedThreadPool(nClients);

            for (int i = 0; i < nClients; i++) {
                TestHandler testHandler = new TestHandler(listener.accept());

                executor.execute(testHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
            fail();
        }
    }
}

class TestHandler implements Runnable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private ObjectInputStream dataIn;
    private ObjectOutputStream dataOut;

    public TestHandler(Socket socket) throws IOException {
        this.socket = socket;
        in = socket.getInputStream();
        out = socket.getOutputStream();
        dataOut = new ObjectOutputStream(out);
        dataIn = new ObjectInputStream(in);
    }

    @Override
    public void run() {
        try {
            Message message = new Message("Message Server");
            dataOut.writeObject(message);
            dataIn.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

