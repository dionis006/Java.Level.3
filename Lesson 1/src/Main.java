import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("======================================");
        System.out.println("Task 1");
        String[] arrayOfStrings = {"A", "B", "C", "D", "E", "F"};
        Integer[] arrayOfIntegers = {1, 2, 3, 4, 5};
        doReplacement(arrayOfStrings, 0, 2);
        doReplacement(arrayOfIntegers, 0, 2);
        System.out.println("======================================");
        System.out.println("Task 2");
        asList(arrayOfStrings);
        System.out.println();
        asList(arrayOfIntegers);
        System.out.println("\n======================================");

        System.out.println("Task 3");
        Box<Orange> orangeBox = new Box<>();
        Box<Apple> appleBox = new Box<>();
        orangeBox.addFruit(new Orange(), 10);
        appleBox.addFruit(new Apple(), 10);
        orangeBox.getWeight();
        appleBox.getWeight();
        System.out.println(orangeBox.getWeight());
        System.out.println(appleBox.getWeight());
        System.out.println(orangeBox.compare(appleBox));
        Box<Apple> appleBox1 = new Box<>();
        appleBox1.addFruit(new Apple(), 5);
        appleBox.pourFruit(appleBox1);
        System.out.println(appleBox1.getWeight());
        System.out.println("\n======================================");
    }

    static <T> void doReplacement(T arr[], int n1, int n2) {
        System.out.println("Before replacement: " + Arrays.toString(arr));
        T tempArr = arr[n1];
        arr[n1] = arr[n2];
        arr[n2] = tempArr;
        System.out.println("After replacement: " + Arrays.toString(arr));
    }

    static <T> void asList(T[] arr) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, arr);
        for (T value : list)
            System.out.print(" " + value);
    }

}
