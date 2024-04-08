package com.tco.misc;

import java.util.Arrays;

public class TwoOpt extends TourOptimizer{

    boolean improvement;
    protected int[] route;
    private int i;
    private int k;
    
    @Override
    public void improve() {
        route = Arrays.copyOf(tour, tour.length + 1);
        route[(route.length - 1)] = tour[0];

        improvement = true;
        
        while (improvement) {
            if(tooMuchTimeElapsed()) break;
            improvement = false;
            loopOne();    
        }
        this.tour = Arrays.copyOf(route, route.length - 1);
    }

    private void loopOne(){
        for(i = 0; i <= route.length - 4; i++){
            loopTwo();
        }
    }

    private void loopTwo(){
        for(k = i + 2; k <= route.length - 2; k++){
            if(tooMuchTimeElapsed()) break;
            checkImprovement();
        }
    }

    private void checkImprovement(){
        if(isImproved(i, k)){
            swapIndex(i + 1, k);
            improvement = true;
        }
    }

    protected boolean isImproved(int i, int k){
        long newLegStart = distanceMatrix[route[i]][route[k]];
        long newLegEnd = distanceMatrix[route[i+1]][route[k+1]];
        long oldLegStart = distanceMatrix[route[i]][route[i+1]];
        long oldLegEnd = distanceMatrix[route[k]][route[k+1]];

        long newDistance = newLegStart + newLegEnd;
        long oldDistance = oldLegStart + oldLegEnd;
        return newDistance < oldDistance;
    }

    protected void swapIndex(int i1, int k){
        //Reverse the places in the tour without making new data structure
        while(i1 < k) {
            int temp = route[i1];
            route[i1] = route[k];
            route[k] = temp;
            i1++;
            k--;
        }
    }
}
