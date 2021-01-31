public class Main {

    public static void main(String[] args) {

        new Thread(new Printer('A','B')).start();
        new Thread(new Printer('B','C')).start();
        new Thread(new Printer('C','A')).start();

    }

}
