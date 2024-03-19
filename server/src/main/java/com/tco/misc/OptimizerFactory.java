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

}