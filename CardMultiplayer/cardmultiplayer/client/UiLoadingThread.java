package cardmultiplayer.client;

public class UiLoadingThread extends Thread {

    private volatile boolean running = true;

    @Override
    public synchronized void start() {
        running = true;
        super.start();
    }

    @Override
    public void run() {
        int dots = 0;
        System.out.print("Loading");
        while (running) {
            synchronized (System.out) {
                System.out.print(".");
            }
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

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
