package Homeworks.Peptides;

import java.util.ArrayList;
import java.util.List;

public class RadixSort {

    public static void sort(ArrayList<Long> data) {
        if (data.isEmpty()) return;

        long max = getMax(data);
        for (long exp = 1; max / exp > 0; exp *= 10) {
            countSort(data, exp);
        }
    }

    private static long getMax(List<Long> data) {
        long max = data.get(0);
        for (long num : data) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    private static void countSort(ArrayList<Long> data, long exp) {
        int n = data.size();
        ArrayList<Long> output = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            output.add(0L);  // Initialize with zeros
        }

        int[] count = new int[10];

        for (long num : data) {
            int index = (int) ((num / exp) % 10);
            count[index]++;
        }

        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (int i = n - 1; i >= 0; i--) {
            long num = data.get(i);
            int index = (int) ((num / exp) % 10);
            output.set(count[index] - 1, num);
            count[index]--;
        }

        for (int i = 0; i < n; i++) {
            data.set(i, output.get(i));
        }
    }
}