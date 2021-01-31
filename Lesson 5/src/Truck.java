import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Truck extends Transport {

    static final private String name = "truck";
    static public double tank = 60;
    static final private int fullTank = 60;
    static private final double consumption = 15;
    FuelStation fuelStation;
    private String brand;

    public Truck(FuelStation fuelStation, String brand) {
        this.brand = brand;
        this.fuelStation = fuelStation;
    }

    public double getTank() {
        return tank;
    }

    @Override
    public void setTank() {
        tank = fullTank;
    }

    public String getName() {
        return name;
    }

    public String getBrand(){
        return brand;
    }

    public void launch() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(this::going);
        executorService.execute(this::doChekFuel);
        executorService.shutdown();
    }

    public void doChekFuel() {
        while (tank > 0) {
            try {
                System.out.println(String.format("%s %s remaining fuel: %s", brand, name, tank));
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void going() {
        System.out.println(String.format("%s %s is going...", brand, name));
        while (tank > 0) {
            try {
                Thread.sleep(1000);
                tank = tank - consumption;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println(String.format("%s %s going to the fuel station...", brand, name));
        fuelStation.doFuel(this);
    }

}
