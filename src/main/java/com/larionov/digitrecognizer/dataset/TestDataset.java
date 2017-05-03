package com.larionov.digitrecognizer.dataset;

public class TestDataset implements Dataset {

    private static final int NORMALIZATION_VALUE = 255;

    protected double[] inputs;

    public TestDataset(String[] row, int startIndex, boolean withNormalization) {
        inputs = new double[row.length - startIndex];
        int pixelValue;
        for (int i = startIndex; i < row.length; ++i) {
            pixelValue = Integer.parseInt(row[i]);
            inputs[i - startIndex] = pixelValue;
            if (withNormalization) {
                inputs[i - startIndex] /= NORMALIZATION_VALUE;
            }
        }
    }

    @Override
    public double[] getInputs() {
        return inputs;
    }
}
