package com.tco.misc;

import java.util.Arrays;

public class ThreeOpt extends TwoOpt{
    
    @Override
    public void improve() {
        this.route = Arrays.copyOf(tour, tour.length + 1);
        this.route[route.length - 1] = tour[0];
        int n = tour.length; 

        boolean improvement = true;
        while (improvement) {
            improvement = false;
            for (int i = 0; i <= n - 3; i++) {
                for (int j = i + 1; j <= n - 2; j++) {
                    for (int k = j + 1; k <= n - 1; k++) {
                        int reversals = threeOptReversals(i, j, k);
                        if (threeOptReverseI1J(reversals)) swapIndex(i + 1, j);
                        if (threeOptReverseJ1K(reversals)) swapIndex(j + 1, k);
                        if (threeOptReverseI1K(reversals)) swapIndex(i + 1, k);
                        if (reversals > 0) improvement = true;
                    }
                }
            }
        }
        this.tour = Arrays.copyOf(this.route, this.route.length - 1);
    }

    protected boolean threeOptReverseI1J(int reversals){
        return (reversals & 0b001) > 0;
    }

    protected boolean threeOptReverseJ1K(int reversals){
        return (reversals & 0b010) > 0;
    }

    protected boolean threeOptReverseI1K(int reversals){
        return (reversals & 0b100) > 0;
    }

    protected int threeOptReversals(int i, int j, int k) {
        int reversals = 0;
        
        if (isImproved(i, j)) reversals += 1;
        if (isImproved(j, k)) reversals += 2;
        if (isImproved(i, k)) reversals += 4;
        
        return reversals;
    }

}
