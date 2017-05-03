package com.larionov.digitrecognizer.dataset;

public class TrainDataset extends TestDataset {

    private int digit;

    public TrainDataset(String[] row, boolean withNormalization) {
        super(row, 1, withNormalization);
        digit = Integer.parseInt(row[0]);
    }

    public int getDigit() {
        return digit;
    }
}
