package com.tco.query;

import com.tco.query.Select;
import com.tco.query.Database;
import com.tco.misc.BadRequestException;
import com.tco.requests.Places;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FindLocations {
    private Places places;
    private String match;
    private List<String> type;
    private List<String> where;
    private int limit;

    public FindLocations(String match, List<String> type, List<String> where, int limit){
        this.match = match;
        this.type = type;
        this.where = where;
        this.limit = limit == 0 ? 100000 : limit;
    }

    public Places find() throws Exception {
        this.places = Database.places(Select.match(this.match,this.limit));
        return this.places;
    }

    public Integer found() throws Exception {
        return Database.found(Select.found(this.match));
    }

    public Integer foundType() throws Exception {
        return Database.found(Select.foundType(this.match, this.type));
    }

    public Integer foundWhere() throws Exception {
        return Database.found(Select.foundWhere(this.match, this.where));
    }

    public Places where() throws Exception {
        this.places = Database.places(Select.where(this.match, this.limit, this.where));
        return this.places;
    }

    public Places whereAndType() throws Exception {
        this.places = Database.places(Select.whereAndType(this.match, this.limit, this.where, this.type));
        return this.places;
    }

    public Integer foundWhereAndType() throws Exception {
        return Database.found(Select.foundWhereAndType(this.match, this.where, this.type));
    }

    public Places type() throws Exception {
        if(!typeListIsValid()) {
            throw new BadRequestException();
        }
        this.places = Database.places(Select.type(this.match, this.limit, this.type));
        return this.places;
    }

    private boolean typeListIsValid() {
        int portCount = 0;
        for(String port : this.type) {
            if(port.equalsIgnoreCase("airport") || port.equalsIgnoreCase("heliport") || port.equalsIgnoreCase("balloonport") || port.equalsIgnoreCase("other")) {
                portCount++;
            }
        }

        return portCount == this.type.size();    
    }
}
