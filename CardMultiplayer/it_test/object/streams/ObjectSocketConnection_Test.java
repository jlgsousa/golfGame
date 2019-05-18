package it_test.object.streams;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ObjectSocketConnection_Test {

    public ExecutorService service;

    @Before
    public void setup() {
        service = Executors.newFixedThreadPool(5);
    }

    @Test
    public void testConnectionMultipleClients() throws IOException, InterruptedException {
        startServer();

        int nClients = 4;
        startNClients(nClients);

        service.awaitTermination(5000L, TimeUnit.MILLISECONDS);

        assert nClients == ServerRunnable.serverList.size();
    }

    private void startServer() {
        service.execute(new ServerRunnable());
    }

    private void startNClients(int nClients) throws IOException {
        for (int i = 0; i < nClients; i++) {
            service.execute(new ClientRunnable());
        }
    }
}
