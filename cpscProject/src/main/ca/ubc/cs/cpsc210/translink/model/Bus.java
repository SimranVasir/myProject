package ca.ubc.cs.cpsc210.translink.model;

import ca.ubc.cs.cpsc210.translink.util.LatLon;

// Represents a bus having a destination, time and location that serves a particular route
public class Bus {

    private Route route;
    private double lat;
    private double lon;
    private String dest;
    private String time;

    /**
     * Constructor
     *
     * @param route the bus route
     * @param lat   latitude of bus
     * @param lon   longitude of bus
     * @param dest  destination
     * @param time  time at which location was recorded
     */
    public Bus(Route route, double lat, double lon, String dest, String time) {
        this.route = route;
        this.lat = lat;
        this.lon = lon;
        this.dest = dest;
        this.time = time;
    }

    /**
     * Gets bus route
     *
     * @return bus route
     */
    public Route getRoute() {
        return this.route;
    }

    /**
     * Gets bus location as LatLon object
     *
     * @return bus location
     */
    public LatLon getLatLon() {
        LatLon latLon = new LatLon(lat, lon);
        return latLon;
    }

    /**
     * Gets destination
     *
     * @return destination of this bus
     */
    public String getDestination() {
        return this.dest;
    }

    /**
     * Gets time bus location was recorded
     *
     * @return time location was recorded
     */
    public String getTime() {
        return this.time;
    }

}
