package com.larionov.digitrecognizer;

public class Utils {

    private Utils(){}

    public static int getIndexMaxElement(double[] array) {
        int maxIndex = 0;
        double maxElement = array[0];
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > maxElement) {
                maxElement = array[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
