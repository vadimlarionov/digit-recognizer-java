package com.larionov.digitrecognizer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.larionov.digitrecognizer.core.Perceptron;
import com.larionov.digitrecognizer.core.Trainer;
import com.larionov.digitrecognizer.dataset.TestDataset;
import com.larionov.digitrecognizer.dataset.TrainDataset;

public class Main {

    private static final String TRAIN_FILE = "train.csv";

    private static final String TEST_FILE = "test.csv";

    public static void main(String[] args) throws IOException {
        int numberOfInputs = 28 * 28;
        int maxEpochs = 25;
        double minRmse = 0.1;
        double learningRate = 0.01;
        boolean withNormalization = true;

        Reader reader = new Reader();
        List<TrainDataset> datasets = reader.readTrainDataset(TRAIN_FILE, -1,true, withNormalization);

        Perceptron perceptron = new Perceptron(numberOfInputs);
        Trainer trainer = new Trainer(perceptron, learningRate);
        trainer.train(datasets, maxEpochs, minRmse);

        // Output
        List<TestDataset> testDatasets = reader.readTestDataset(TEST_FILE, -1, true, withNormalization);
        try(PrintWriter printWriter = new PrintWriter(new FileOutputStream("out.csv"))) {
            printWriter.write("ImageId,Label\n");
            int label = 1;
            int value;
            for (TestDataset dataset : testDatasets) {
                value = perceptron.evaluate(dataset.getInputs());
                printWriter.write(String.format("%d,%d\n", label, value));
                ++label;
            }
        }
    }
}
