package com.larionov.digitrecognizer.core;

import java.util.function.DoubleFunction;

public class Neuron {

    private double bias;

    private double[] weights;

    private DoubleFunction<Double> activateFunction;

    public Neuron(int numberOfInputs) {
        weights = new double[numberOfInputs];
        activateFunction = x -> 1.0 / (1.0 + Math.exp(-x));
    }

    public double activate(double[] inputs) {
        checkInputs(inputs);
        double value = -bias;
        for (int i = 0; i < weights.length; ++i) {
            value += weights[i] * inputs[i];
        }
        return activateFunction.apply(value);
    }

    public void updateWeights(double[] inputs, double learningRate, double delta) {
        checkInputs(inputs);
        for (int i = 0; i < weights.length; ++i) {
            weights[i] += learningRate * delta * inputs[i];
        }
        bias -= learningRate * delta;
    }

    private void checkInputs(double[] inputs) {
        if (inputs.length != weights.length) {
            String errorMessage = "Incorrect number of inputs (" + inputs.length + " != " + weights.length + ")";
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
