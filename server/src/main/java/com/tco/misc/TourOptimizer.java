package com.tco.misc;

import com.tco.requests.Places;

public abstract class TourOptimizer {

    private long[][] distanceMatrix;
    private int[] tour;

    public Places construct(Places places, Double earthRadius, String formula, Double response){
        fillDistanceMatrix(earthRadius, formula, places);
        return places;
    }

    protected void fillDistanceMatrix(Double earthRadius, String formula, Places places) {
        int tourLength = places.size();
        this.distanceMatrix = new long[tourLength][tourLength];
        GreatCircleDistance calculator = FormulaFactory.createFormula(formula);

        for(int from = 0; from < tourLength; from++) {
            GeographicCoordinate fromPlace = places.get(from);
            for(int to = 0; to < tourLength; to++) {
                GeographicCoordinate toPlace = places.get(to);
                long distance = calculator.between(fromPlace, toPlace, earthRadius);
                this.distanceMatrix[from][to] = distance;
            }
        }
    }

    protected int[] nearestNeighbor(int startingPoint) {
        int[] currentTour = new int[this.tour.length];
        currentTour[0] = startingPoint;
        boolean[] visited = new boolean[this.tour.length];
        visited[startingPoint] = true;
        int currentPlace = startingPoint;
        
        for (int i = 1; i < this.tour.length; i++) {
            int nearestPlace = findNearestPlace(visited, currentPlace);
            visited[nearestPlace] = true;
            currentTour[i] = nearestPlace;
            currentPlace = nearestPlace;
        }

        return currentTour;
    }

    protected int findNearestPlace(boolean[] visited, int currentPlace) {
        int nearestPlace = -1;
        long nearestDistance = Long.MAX_VALUE;
        for (int j = 0; j < this.tour.length; j++) {
            if (!visited[j] && this.distanceMatrix[currentPlace][j] < nearestDistance) {
                nearestPlace = j;
                nearestDistance = this.distanceMatrix[currentPlace][j];
            }
        }
        return nearestPlace;
    }

    public abstract void improve(); 

    // for testing
    protected long[][] getDistanceMatrix() {
        return this.distanceMatrix;
    }
    protected void setDistanceMatrix(long[][] matrix) {
        this.distanceMatrix = matrix;
    }
    protected void setTour(int[] array) {
        this.tour = array;
    }
}
