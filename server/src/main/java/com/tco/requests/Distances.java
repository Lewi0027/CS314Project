package com.tco.requests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Distances extends ArrayList<Long> {
    
    private static final transient Logger log = LoggerFactory.getLogger(Distances.class);
    
    public Long total() {
        Long sum = 0L;
        for (Long distance : this) {
            if (distance < 0) {
                log.info("One of the passed distance values is negative or larger than Long.MAX_VALUE. Returning a sum of 0.");
                sum = 0L;
                break;
            }
            sum += distance;
            if (sum < 0) {
                log.info("The sum exceeds Long.MAX_VALUE. Sum set equal to Long.MAX_VALUE.\n");
                sum = Long.MAX_VALUE;
                break;
            }
        }
        return sum;
    }
}