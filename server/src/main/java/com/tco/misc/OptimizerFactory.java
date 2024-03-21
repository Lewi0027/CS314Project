package com.tco.misc;

public class OptimizerFactory {

    public static TourOptimizer createOptimizer(int placesSize, Double response) {
        if(response <= 0) { // If it is a negative value, should it throw an exception?
            return new NoOpt();
        }
        if(placesSize < 4){
            return new NoOpt();
        }

        double responseThresholdMatrix = calculateMatrixTime(placesSize);
        double responseThresholdNoOpt = calculateNoOpt(placesSize);
        double responseThresholdOneOpt = calculateOneOpt(placesSize);
        double responseThresholdTwoOpt = calculateTwoOpt(placesSize);
        double responseThresholdThreeOpt = calculateThreeOpt(placesSize);

        double responseMillis = response * 1000; // converts response to milliseconds for threshold comparibility
        
        if(responseMillis - 50 < responseThresholdMatrix)
            return new NoOpt();

        if(responseMillis > responseThresholdThreeOpt / 10)
            return new ThreeOpt();

        if(responseMillis > responseThresholdTwoOpt / 10)
            return new TwoOpt();

        if(responseMillis > responseThresholdOneOpt / 10)
            return new OneOpt();

        return new NoOpt();
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
        double quarticTerm = 1e-10 * (Math.pow(placesSize, 4));
        double cubicTerm = 5.999e-7 * (Math.pow(placesSize, 3));
        double quadraticTerm = -0.000111 * (Math.pow(placesSize, 2));
        double linearTerm = 0.4580 * placesSize;
        double constantTerm = 14.9673;
        return quarticTerm + cubicTerm + quadraticTerm + linearTerm + constantTerm;
    }

    protected static double calculateTwoOpt(int placesSize){
        double quarticTerm = 7.35150e-10 * (Math.pow(placesSize, 4));
        double cubicTerm = 1.05654e-5 * (Math.pow(placesSize, 3));
        double quadraticTerm = -1.48424e-3 * (Math.pow(placesSize, 2));
        double linearTerm = 4.64993e-1 * placesSize;
        double constantTerm = 8.38256;
        return quarticTerm + cubicTerm + quadraticTerm + linearTerm + constantTerm;
    }

    protected static double calculateThreeOpt(int placesSize) {
        double quarticTerm = 3.16846e-6 * (Math.pow(placesSize, 4));
        double cubicTerm = -2.14276e-4 * (Math.pow(placesSize, 3));
        double quadraticTerm = 3.61923e-2 * (Math.pow(placesSize, 2));
        double linearTerm = -1.49333 * placesSize;
        double constantTerm = 1.93161e1;
        return quarticTerm + cubicTerm + quadraticTerm + linearTerm + constantTerm;
    } 
}