package com.larionov.digitrecognizer.dataset;

@FunctionalInterface
public interface DatasetFactory<T extends Dataset> {

    T create(String[] row, boolean withNormalization);
}
