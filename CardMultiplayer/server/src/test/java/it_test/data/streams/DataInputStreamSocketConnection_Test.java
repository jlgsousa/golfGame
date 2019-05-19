package it_test.data.streams;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;

public class DataInputStreamSocketConnection_Test {

    public ExecutorService service;
    public static int successConnectedClients = 0;

    @Before
    public void setup() {
        service = Executors.newFixedThreadPool(5);
    }

    @Test
    public void testConnectionMultipleClients() throws IOException, InterruptedException {
        startServer();

        startNClients(4);

        service.awaitTermination(5000L, TimeUnit.MILLISECONDS);
        assertEquals(4, successConnectedClients);
    }

    private void startServer() {
        service.execute(new ServerRunnable());
    }

    private void startNClients(int nClients) throws IOException {
        for (int i = 0; i < nClients; i++) {
            service.execute(new ClientRunnable());
        }
    }

    class ServerRunnable implements Runnable {
        @Override
        public void run() {

            try (ServerSocket listener = new ServerSocket(59000)) {
                ExecutorService executor = Executors.newFixedThreadPool(4);

                for (int playerNumber = 1; playerNumber < 5; playerNumber++) {
                    TestHandler testHandler = new TestHandler(listener.accept());

                    executor.execute(testHandler);
                }
            } catch (IOException e) {
                e.printStackTrace();
                fail();
            }
        }
    }

    class ClientRunnable implements Runnable {
        private Socket socket;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public ClientRunnable() throws IOException {
            socket = new Socket("localhost", 59000);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();
            dataIn = new DataInputStream(in);
            dataOut = new DataOutputStream(out);
        }

        @Override
        public void run() {
            try {
                String messageServer = dataIn.readUTF();
                System.out.println(messageServer);
                dataOut.writeUTF("Thanks bro, bye");
            } catch (IOException e) {
                e.printStackTrace();
                successConnectedClients--;
            } finally {
                successConnectedClients++;
            }
        }
    }

    class TestHandler implements Runnable {
        private Socket socket;
        private InputStream in;
        private OutputStream out;
        private DataInputStream dataIn;
        private DataOutputStream dataOut;

        public TestHandler(Socket socket) throws IOException {
            this.socket = socket;
            in = socket.getInputStream();
            out = socket.getOutputStream();
            dataIn = new DataInputStream(in);
            dataOut = new DataOutputStream(out);
        }

        @Override
        public void run() {
            try {
                String requestServer = "Message Server";
                dataOut.writeUTF(requestServer);
                dataIn.readUTF();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
