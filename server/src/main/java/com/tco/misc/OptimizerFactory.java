package com.tco.misc;

public class OptimizerFactory {

    public static TourOptimizer createOptimizer(int placesSize, Double response) {
        if(response <= 0) // If it is a negative value, should it throw an exception?
            return new NoOpt();

        if (response >= calculateTime(120, placesSize)) {
            return new ThreeOpt();
        } else if (response >= calculateTime(450, placesSize)) {
            return new TwoOpt();
        } else if(response > ( (0.0000005 * Math.pow(placesSize, 2)) - (0.0002 * placesSize) + 0.08956)) {
            return new OneOpt();
        } else {
            return new NoOpt();
        } 
    }

    private static Double calculateTime(int timeForOneSecond, int placesSize) {
        return Math.log(placesSize) / Math.log(timeForOneSecond);
    }

    public static double calculateMatrixTime(int placesSize) {
        double quarticTerm = 1.69777e-11 * (Math.pow(placesSize, 4));
        double cubicTerm = -3.28461e-8 * (Math.pow(placesSize, 3));
        double quadraticTerm = 1.26646e-4 * (Math.pow(placesSize, 2));
        double linearTerm = 1.94351e-3 * placesSize;
        double constantTerm = 1.2237e-1;
        return quarticTerm + cubicTerm + quadraticTerm + linearTerm + constantTerm;
    }

    public static double calculateNoOpt(int placesSize) {
        double quadraticTerm = -0.0000976612 * (Math.pow(placesSize, 2));
        double linearTerm = 0.30235 * placesSize;
        double constantTerm = 17.05127;
        return quadraticTerm + linearTerm + constantTerm;
    }

    protected static double calculateOneOpt(int placesSize){
        double quarticTerm = -5.85444e-10 * (Math.pow(placesSize, 4));
        double cubicTerm = 1.97076e-6* (Math.pow(placesSize, 3));
        double quadraticTerm = -9.79171e-4 * (Math.pow(placesSize, 2));
        double linearTerm = 6.40399e-1 * placesSize;
        double constantTerm = 7.85096;
        return quarticTerm + cubicTerm + quadraticTerm + linearTerm + constantTerm;
    }


}