package com.agricultural.swing.frames.allinformation;

import lombok.Data;

/**
 * Created by Alexey on 23.03.2017.
 */
@Data
public class DataInformation {

    private String operation;
    private String machine;
    private double[] monthData;

    public DataInformation(String operation, String machine, double[] monthData) {
        this.operation = operation;
        this.machine = machine;
        this.monthData = monthData;

    }
}
