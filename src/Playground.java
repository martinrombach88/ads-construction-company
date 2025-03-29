import java.util.Arrays;

public class Playground {
    public void main() {
        print("test");

        //test merge sort
        int[] test1 = {1,2,6,7, 5,8,4,3};
        int[] test2 = mergeSort(test1);
        print(Arrays.toString(test2));
    };

    void sortByCost() {
        //can try merge sort for n lg n
        //n traverse and sort
        //lg n merge
    }

    int[] mergeSort(int[] array) {
        //pivot is the mid index to target
        if (array.length ==1) return array;
        int pivot = array.length / 2;

        int[] left = mergeSort(Arrays.copyOfRange(array, 0, pivot));
        int[] right = mergeSort(Arrays.copyOfRange(array, pivot, array.length));

        return merge(left, right);
    }

    int[] merge(int[] array1, int[] array2) {
        int[] combined = new int[array1.length + array2.length];
        int index = 0;
        int i = 0;
        int j = 0;

        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                combined[index] = array1[i];
                index++;
                i++;
            } else {
            combined[index] = array2[j];
            index++;
            j++;
            }
        }
        while (i < array1.length) {
            combined[index] = array1[i];
            index++;
            i++;
        }
        while(j < array2.length) {
            combined[index] = array2[j];
            index++;
            j++;
        }
        return combined;
    }

    void print(String message){
        System.out.println(message);
    }
}
