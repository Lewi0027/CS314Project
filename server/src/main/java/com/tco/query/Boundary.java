package com.tco.query;

public class Boundary {
    
        public double latMin;
        public double latMax;
        public double lonMin;
        public double lonMax;

        public Boundary() {
            this.latMin = 0;
            this.latMax = 0;
            this.lonMin = 0;
            this.lonMax = 0;
        }

        public Boundary(double latMin, double latMax, double lonMin, double lonMax ) {
            this.latMin = latMin;
            this.latMax = latMax;
            this.lonMin = lonMin;
            this.lonMax = lonMax;
        }

        public Boundary(Boundary other) {
            this.latMin = other.latMin;
            this.latMax = other.latMax;
            this.lonMin = other.lonMin;
            this.lonMax = other.lonMax;
        }
        
        @Override
        public String toString() {
            String res = "";
            res += "latMin: " + this.latMin + "\n";
            res += "latMax: " + this.latMax + "\n";
            res += "lonMin: " + this.lonMin + "\n";
            res += "lonMax: " + this.lonMax + "\n";
            return res;
        }
}
