package com.tco.misc;

import java.util.Arrays;

public class ThreeOpt extends TwoOpt{
    
    @Override
    public void improve() {
        
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
