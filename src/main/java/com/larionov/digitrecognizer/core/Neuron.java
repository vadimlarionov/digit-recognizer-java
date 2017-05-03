package com.larionov.digitrecognizer.core;

import java.util.function.DoubleFunction;

public class Neuron {

    private double bias;

    private double[] weight;

    private DoubleFunction<Double> activateFunction;

    public Neuron(int numberOfInputs) {
        weight = new double[numberOfInputs];
        activateFunction = x -> ((double) 1) / (1 + Math.exp(-x));
    }

    public double activate(double[] inputs) {
        checkInputs(inputs);
        double value = -bias;
        for (int i = 0; i < weight.length; ++i) {
            value += weight[i] * inputs[i];
        }
        return activateFunction.apply(value);
    }

    public void updateWeights(double[] inputs, double learningRate, double delta) {
        checkInputs(inputs);
        for (int i = 0; i < weight.length; ++i) {
            weight[i] += learningRate * delta * inputs[i];
        }
        bias += learningRate * delta;
    }

    private void checkInputs(double[] inputs) {
        if (inputs.length != weight.length) {
            String errorMessage = "Incorrect number of inputs (" + inputs.length + " != " + weight.length + ")";
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
