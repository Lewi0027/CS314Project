package com.tco.misc;

public class OptimizerFactory {

    public static TourOptimizer createOptimizer(int placesSize, Double response) {
        TourOptimizer optimizer = new NoOpt();

        if (response > 0 && placesSize >= 4) {
            double responseMillis = response * 1000;
            double responseThresholdMatrix = calculateOptimizationTime(placesSize, new double[]{1.69777e-11, -3.28461e-8, 1.26646e-4, 1.94351e-3, 1.2237e-1});

            if (responseMillis - 50 >= responseThresholdMatrix) {
                double responseThresholdThreeOpt = calculateOptimizationTime(placesSize, new double[]{3.16846e-6, -2.14276e-4, 3.61923e-2, -1.49333, 19.3161});
                double responseThresholdTwoOpt = calculateOptimizationTime(placesSize, new double[]{7.35150e-10, 1.05654e-5, -1.48424e-3, 4.64993e-1, 8.38256});
                double responseThresholdOneOpt = calculateOptimizationTime(placesSize, new double[]{1e-10, 5.999e-7, -0.000111, 0.4580, 14.9673});

                if (responseMillis > responseThresholdThreeOpt / 10) {
                    optimizer = new ThreeOpt();
                } else if (responseMillis > responseThresholdTwoOpt / 10) {
                    optimizer = new TwoOpt();
                } else if (responseMillis > responseThresholdOneOpt / 10) {
                    optimizer = new OneOpt();
                }
            }
        }

        return optimizer;
    }

    public static double calculateOptimizationTime(int placesSize, double[] coefficients) {
        double result = 0;
        if (coefficients.length == 5) {
            double quarticTerm = coefficients[0] * Math.pow(placesSize, 4);
            double cubicTerm = coefficients[1] * Math.pow(placesSize, 3);
            double quadraticTerm = coefficients[2] * Math.pow(placesSize, 2);
            double linearTerm = coefficients[3] * placesSize;
            double constantTerm = coefficients[4];
            result = quarticTerm + cubicTerm + quadraticTerm + linearTerm + constantTerm;
        }
        return result;
    }
}