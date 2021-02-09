package nextask;

public class NextTask {

    public static int[] newArray(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 4) {
                int newArrayLength = array.length - i - 1;
                int[] newArray = new int[newArrayLength];
                for (int j = 0; j < newArray.length; j++) {
                    newArray[j] = array[i + 1];
                    i++;
                }
                return newArray;
            }
        }
        throw new RuntimeException("Array doesn't contain 4");
    }

    public static boolean chekForOneAndFour(int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == 1 || array[i] == 4) {
                return true;
            }
        }
        return false;
    }

}
