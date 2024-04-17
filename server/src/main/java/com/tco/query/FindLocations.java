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

    public Places where() throws Exception {
        return this.places;
    }


}
