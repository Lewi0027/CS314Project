package com.tco.misc;

import com.tco.requests.Places;
import com.tco.requests.Place;
import com.tco.requests.Distances;

public abstract class TourOptimizer {

    protected long[][] distanceMatrix;
    protected int[] tour;
    protected double response;
    protected long startTime;

    public Places construct(Places places, Double earthRadius, String formula, Double response){
        startTime = System.nanoTime();
        this.response = response;
        fillDistanceMatrix(earthRadius, formula, places);
        fillTour(places.size());
        calculateShortestTour();
        rotateTour();
        places = rearrangePlaces(places);

        return places;
    }

    protected boolean tooMuchTimeElapsed(){
        Double responseThreshold = (this.response * 1000) - 150;
        long currTime = System.nanoTime();
        double totalDuration = (currTime - this.startTime) / 1e6;
        return (totalDuration >= responseThreshold);
    }

    protected void rotateTour() {
        int startIndex = findStartIndex();
        if (startIndex == 0) return;

        int[] rotatedTour = new int[tour.length];

        // Copy the part from startIndex to the end of tour to the beginning of rotatedTour
        System.arraycopy(tour, startIndex, rotatedTour, 0, tour.length - startIndex);

        // Copy the part from 0 to startIndex of tour to the end of rotatedTour
        System.arraycopy(tour, 0, rotatedTour, tour.length - startIndex, startIndex);

        this.tour = rotatedTour;
    }

    protected int findStartIndex() {
        int start = 0;
        for(int i = 0; i < tour.length; i++) {
            if(tour[i] == 0) {
                start = i;
                break;
            }
        }
        return start;
    }
    
    protected Places rearrangePlaces(Places places) {
        int tourLength = this.tour.length;
        Places modifiedPlaces = new Places();
        
        for (int i = 0; i < tourLength; i++) {
            Place placeToAdd = places.get(tour[i]);
            modifiedPlaces.add(placeToAdd);
        }

        return modifiedPlaces;
    }

    protected void calculateShortestTour() {
        long shortestTourDistance = totalDistanceOfTour();
        long tourDistance = 0l;
        int[] bestTourSoFar = this.tour.clone();
        int tourLength = this.tour.length;
        if(tourLength == 0) return;

        for(int i = 0; i < tourLength; i++) {
            this.tour = nearestNeighbor(i);
            improve();
            tourDistance = totalDistanceOfTour();
            if(tourDistance < shortestTourDistance) {
                shortestTourDistance = tourDistance;
                bestTourSoFar = this.tour.clone();
            }
        }

        this.tour = bestTourSoFar;
    }

    protected long totalDistanceOfTour() {
        Distances tourDistances = new Distances();
        int tourLength = this.tour.length;

        for(int i = 0; i < tourLength; i++) {
            int fromPlace = this.tour[i];
            int toPlace = this.tour[(i + 1) % tourLength];

            long distance = distanceMatrix[fromPlace][toPlace];
            tourDistances.add(distance);
        }

        return tourDistances.total();
    }

    protected void fillTour(int tourSize) {
        this.tour = new int[tourSize];
        for(int i = 0; i < tourSize; i++) {
            this.tour[i] = i;
        }
    }

    protected void fillDistanceMatrix(Double earthRadius, String formula, Places places) {
        int tourLength = places.size();
        this.distanceMatrix = new long[tourLength][tourLength];
        GreatCircleDistance calculator = FormulaFactory.createFormula(formula);

        for(int from = 0; from < tourLength; from++) {
            GeographicCoordinate fromPlace = places.get(from);
            for(int to = from + 1; to < tourLength; to++) {
                GeographicCoordinate toPlace = places.get(to);
                long distance = calculator.between(fromPlace, toPlace, earthRadius);
                this.distanceMatrix[from][to] = distance;
                this.distanceMatrix[to][from] = distance;
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

    protected int[] getTour() {
        return this.tour;
    }
    
    protected void setResponse(double response){
        this.response = response;
    }

    protected void setStartTime(long startTime){
        this.startTime = startTime;
    }
}
