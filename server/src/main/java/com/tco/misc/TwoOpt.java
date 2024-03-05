package com.tco.misc;

import java.util.Arrays;

public class TwoOpt extends TourOptimizer{
    
    @Override
    public void improve() {
        int[] route = Arrays.copyOf(tour, tour.length + 1);
        route[route.length - 1] = tour[0];
        boolean improvement = true;
        
        while (improvement) {
            improvement = false;
            for(int i = 0; i <= route.length - 3; i++){
                for(int k = 2; k <= route.length - 1; k++){
                    if(isImproved(tour, i, k)){
                        swapIndex(route, i, k);
                        improvement = true;
                    }
                }
            }
        }
        this.tour = Arrays.copyOf(route, route.length - 1);
    }


    private boolean isImproved(int[] tour, int i, int k){
        long newLegStart = distanceMatrix[tour[i]][tour[k]];
        long newLegEnd = distanceMatrix[tour[i+1]][tour[k+1]];
        long oldLegStart = distanceMatrix[tour[i]][tour[i+1]];
        long oldLegEnd = distanceMatrix[tour[k]][tour[k+1]];

        long newDistance = newLegStart + newLegEnd;
        long oldDistance = oldLegStart + oldLegEnd;
        
        return newDistance < oldDistance;
    }

    private void swapIndex(int[] route, int i1, int k){
        //Reverse the places in the tour without making new data structure

        /*
        Dave's pseudo code:

        while(i1 < k){
            temp = tour[i1]
            tour[i1] = tour[k]
            tour[k] = temp
            i1++
            k--
        }
        */
    }

}
