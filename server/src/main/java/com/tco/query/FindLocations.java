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
    String match;
    List<String> type;
    List<String> where;
    int limit;

    public FindLocations(String match, List<String> type, List<String> where, int limit){
        this.match = match;
        this.type = type;
        this.where = where;
        this.limit = limit == 0 ? 100000 : limit;
    }

    public Places find() throws Exception {
        return null;
    }

    public Integer found() throws Exception {
        return null;
    }
}
