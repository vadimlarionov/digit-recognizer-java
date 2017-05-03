package com.larionov.digitrecognizer.core;

import java.util.List;

import com.larionov.digitrecognizer.Utils;
import com.larionov.digitrecognizer.dataset.TrainDataset;

public class Trainer {

    private Perceptron perceptron;

    private double learningRate;

    public Trainer(Perceptron perceptron, double learningRate) {
        this.perceptron = perceptron;
        this.learningRate = learningRate;
    }

    public void train(List<TrainDataset> trainDatasets, int epochs) {
        for (int epoch = 0; epoch < epochs; ++epoch) {
            int rightRecognized = trainEpoch(trainDatasets);
            double percent = (double) rightRecognized / (double) trainDatasets.size() * 100.;
            String message = String.format("Epoch %d; right recognized %.2f", epoch, percent) + "%";
            System.out.println(message);
        }
    }

    /**
     * @return Процент верно распознанных цифр
     */
    private int trainEpoch(List<TrainDataset> datasets) {
        int successCounter = 0;
        for (TrainDataset dataset : datasets) {
            double[] activationResult = perceptron.activate(dataset.getInputs());
            if (Utils.getIndexMaxElement(activationResult) == dataset.getDigit()) {
                ++successCounter;
            }

            double delta;
            for (int i = 0; i < activationResult.length; ++i) {
                if (i != dataset.getDigit()) {
                    delta = 0. - activationResult[i];
                } else {
                    delta = 1. - activationResult[i];
                }
                perceptron.updateWeights(dataset.getInputs(), i, delta, learningRate);
            }
        }

        return successCounter;
    }
}
