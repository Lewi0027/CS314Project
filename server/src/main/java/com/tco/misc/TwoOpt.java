package com.tco.misc;

public class TwoOpt extends TourOptimizer{
    
    @Override
    public void improve() {
        int tourLength = tour.length;
        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for(int i = 0; i <= tourLength - 3; i++){
                for(int k = 0; k <= tourLength - 1; k++){
                    if(isImproved(tour, i, k)){
                        swapIndex(tour, i, k);
                        improvement = true;
                    }
                }
            }
        }
    }
    private boolean isImproved(int[] tour, int i, int k){
        //calculate if new leg distance swapping is shorter than current leg distance
        //return (newLeg < oldLeg);
        return true;
    }

    private void swapIndex(int[] tour, int i1, int k){
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
