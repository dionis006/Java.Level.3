import java.util.ArrayList;
import java.util.Collections;

public class Box<T extends Fruit> {

    public ArrayList<T> box = new ArrayList<>();

    public Box() {
    }


    public void addFruit(T fruit, int amount) {
        for (int i = 0; i < amount; i++) {
            box.add(fruit);
        }
    }

    float getWeight() {
        float weight = 0.0f;
        for (T o : box) {
            weight = weight + o.getWeight();
        }
        return weight;
    }

    boolean compare(Box otherBox) {
        if (otherBox.getWeight() == getWeight()) return true;
        return false;
    }

    public void pourFruit(Box<T> otherBox){
        otherBox.box.addAll(box);
        box.clear();
    }

}

