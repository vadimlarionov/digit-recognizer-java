package com.larionov.digitrecognizer.core;

import java.util.List;

import com.larionov.digitrecognizer.dataset.TrainDataset;

public class Trainer {

    private Perceptron perceptron;

    private double learningRate;

    public Trainer(Perceptron perceptron, double learningRate) {
        this.perceptron = perceptron;
        this.learningRate = learningRate;
    }

    public void train(List<TrainDataset> trainDatasets, int maxEpochs, double minRmse) {
        double rmse = minRmse + 1;
        for (int epoch = 0; epoch < maxEpochs && rmse > minRmse; ++epoch) {
            rmse = trainEpoch(trainDatasets);
            String message = String.format("Epoch %d; RMSE = %.7f", epoch, rmse);
            System.out.println(message);
        }
    }

    /**
     * @return RMSE
     */
    private double trainEpoch(List<TrainDataset> datasets) {
        double error = 0;
        int rightRecognized = 0;
        for (TrainDataset dataset : datasets) {
            double[] activationResult = perceptron.activate(dataset.getInputs());

            int actualDigit = perceptron.evaluate(dataset.getInputs());
            if (actualDigit == dataset.getDigit()) {
                rightRecognized++;
            }

            double delta;
            for (int i = 0; i < activationResult.length; ++i) {
                if (i != dataset.getDigit()) {
                    delta = 0.0 - activationResult[i];
                } else {
                    delta = 1.0 - activationResult[i];
                }
                error += delta * delta;

                perceptron.updateWeights(dataset.getInputs(), i, delta, learningRate);
            }
        }
        System.out.printf("Right recognized %f\n" + ((float) rightRecognized) / (float) datasets.size());
        return Math.sqrt(error / (float) datasets.size());
    }
}
