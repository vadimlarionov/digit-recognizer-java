package com.larionov.digitrecognizer;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.larionov.digitrecognizer.dataset.Dataset;
import com.larionov.digitrecognizer.dataset.DatasetFactory;
import com.larionov.digitrecognizer.dataset.TestDataset;
import com.larionov.digitrecognizer.dataset.TrainDataset;
import com.opencsv.CSVReader;

public class Reader {

    private static final int INITIAL_CAPACITY = 43_000;

    public List<TestDataset> readTestDataset(String filePath, int limit,
                                             boolean skipHeader, boolean withNormalization) {
        return readCSVFile(filePath, limit, skipHeader, withNormalization,
                (row, withNormalization1) -> new TestDataset(row, 0, withNormalization1));
    }

    public List<TrainDataset> readTrainDataset(String filePath, int limit,
                                               boolean skipHeader, boolean withNormalization) {
        return readCSVFile(filePath, limit, skipHeader, withNormalization, TrainDataset::new);
    }

    private <T extends Dataset> List<T> readCSVFile(String filePath, int limit,
                                                    boolean skipHeader, boolean withNormalization,
                                                    DatasetFactory<T> factory) {
        try(CSVReader csvReader = new CSVReader(new FileReader(filePath), ',', '"')) {
            if (skipHeader) {
                csvReader.readNext();
            }

            List<T> datasets = new ArrayList<>(INITIAL_CAPACITY);
            boolean unlimited = limit == -1;

            for (int i = 0; i < limit || unlimited; ++i) {
                String[] row = csvReader.readNext();
                if (row == null) {
                    break;
                }
                T dataset = factory.create(row, withNormalization);
                datasets.add(dataset);

                if (i % 1000 == 0) {
                    System.out.println("Read " + i + " rows");
                }
            }

            return datasets;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
