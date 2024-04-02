package com.tco.query;
    
public class Credential {

    private static String useTunnel = System.getenv("CS314_USE_DATABASE_TUNNEL");
    private static String onDocker = System.getenv("CS314_DOCKER");

    // shared user with read-only access
    final static String USER = "cs314-db";
    final static String PASSWORD = "eiK5liet1uej";
    // connection information when using port forwarding from localhost
    //final static String URL = "jdbc:mariadb://localhost:3306/cs314";

    public static String getUrl() {
        String dburl = "";

        if(useTunnel != null && useTunnel.equals("true")) {
        dburl = "jdbc:mariadb://127.0.0.1:56247/cs314";
        }
        else if(onDocker != null && onDocker.equals("true")) {
            dburl = "jdbc:mariadb://127.0.0.1:3306/cs314";
        }
        else {
            dburl = "jdbc:mariadb://faure.cs.colostate.edu/cs314";
        }

        return dburl;
    }

    // for testing
    protected void setTunnel(String tunnel) {
        this.useTunnel = tunnel;
    }

    protected void onDocker(String docker) {
        this.onDocker = docker;
    }

}
