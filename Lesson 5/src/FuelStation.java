import java.util.concurrent.Semaphore;

public class FuelStation<T extends Transport> {
    private Semaphore semaphore;

    public FuelStation() {
        this(2);
        System.out.println("Fuel station ready and waiting...");
    }

    public FuelStation(int permits) {
        this.semaphore = new Semaphore(permits);
    }

    public Runnable doFuel(T c) {
        try {
            semaphore.acquire();
            System.out.println(String.format("%s %s is fueling...", c.getBrand(), c.getName()));
            Thread.sleep(5000);
            c.setTank();
            System.out.println(String.format("%s %s fueling completed. Remaining fuel: %s.", c.getBrand(), c.getName(), c.getTank()));
            c.launch();
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
