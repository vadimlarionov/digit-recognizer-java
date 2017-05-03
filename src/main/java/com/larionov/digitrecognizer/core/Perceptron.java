package com.larionov.digitrecognizer.core;

import com.larionov.digitrecognizer.Utils;

public class Perceptron {

    private static final int NUMBER_OF_DIGITS = 10;

    private Neuron[] neurons;

    public Perceptron(int numberOfInputs) {
        neurons = new Neuron[NUMBER_OF_DIGITS];
        for (int i = 0; i < neurons.length; ++i) {
            neurons[i] = new Neuron(numberOfInputs);
        }
    }

    public double[] activate(double[] inputs) {
        double[] result = new double[neurons.length];
        for (int i = 0; i < neurons.length; ++i) {
            result[i] = neurons[i].activate(inputs);
        }
        return result;
    }

    public void updateWeights(double[] inputs, int classNumber, double delta, double learningRate) {
        neurons[classNumber].updateWeights(inputs, learningRate, delta);
    }

    public int evaluate(double[] inputs) {
        return Utils.getIndexMaxElement(activate(inputs));
    }
}
