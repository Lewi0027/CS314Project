package com.tco.requests;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(ConfigRequest.class);

    private String serverName;
    private List<String> features;
    private List<String> formulae

    @Override
    public void buildResponse() {
        serverName = "t28 BarleyBytes";
        features = new ArrayList<>();
        formulae = new ArrayList<>();
        features.add("config");
        features.add("distances");
        features.add("tour");
        formulae.add("vincenty");
        formulae.add("haversine");
        formulae.add("cosines");
        log.trace("buildResponse -> {}", this);
    }

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

    public ConfigRequest() {
        this.requestType = "config";
    }

    public String getServerName() {
        return serverName;
    }

    public boolean validFeature(String feature){
        return features.contains(feature);
    }
}
