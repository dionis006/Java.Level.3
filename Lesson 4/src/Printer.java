public class Printer implements Runnable {

    private static Object lock = new Object();
    private static char symbol = 'A';

    private char current;
    private char next;

    public Printer(char current, char next) {
        this.current = current;
        this.next = next;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                while (symbol != current) {
                    try {
                        lock.wait();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(current);
                symbol = next;
                lock.notifyAll();
            }
        }
    }

}
