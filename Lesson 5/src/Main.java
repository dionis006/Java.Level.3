import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        FuelStation fuelStation = new FuelStation();
        ExecutorService executorService = Executors.newFixedThreadPool(9);

        executorService.execute(() -> new Car(fuelStation, "HONDA").launch());
        executorService.execute(() -> new Car(fuelStation, "BMW").launch());
        executorService.execute(() -> new Car(fuelStation, "TOYOTA").launch());
        executorService.execute(() -> new Truck(fuelStation, "VOLVO").launch());
        executorService.execute(() -> new Truck(fuelStation,"DAF" ).launch());
        executorService.execute(() -> new Truck(fuelStation,"MAN" ).launch());
        executorService.execute(() -> new Bus(fuelStation, "HYUNDAI"));
        executorService.execute(() -> new Bus(fuelStation, "MAZ"));
        executorService.execute(() -> new Bus(fuelStation, "PAZ"));

        executorService.shutdown();
    }

}
