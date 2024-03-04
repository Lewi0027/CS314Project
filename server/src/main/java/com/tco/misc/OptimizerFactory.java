package com.tco.misc;

public class OptimizerFactory {

    public static TourOptimizer createOptimizer(int placesSize, Double response) {
        return new OneOpt(); // Not needed after full implementation of method
    }

}