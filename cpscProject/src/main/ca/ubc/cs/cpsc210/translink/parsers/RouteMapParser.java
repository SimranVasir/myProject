package ca.ubc.cs.cpsc210.translink.parsers;

import ca.ubc.cs.cpsc210.translink.model.Route;
import ca.ubc.cs.cpsc210.translink.model.RouteManager;
import ca.ubc.cs.cpsc210.translink.model.RoutePattern;
import ca.ubc.cs.cpsc210.translink.providers.DataProvider;
import ca.ubc.cs.cpsc210.translink.providers.FileDataProvider;
import ca.ubc.cs.cpsc210.translink.util.LatLon;
import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for routes stored in a compact format in a txt file
 */
public class RouteMapParser {
    private String fileName;
    private int count = 0;

    public RouteMapParser(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Parse the route map txt file
     */
    public void parse() {
        DataProvider dataProvider = new FileDataProvider(fileName);
        try {
            String c = dataProvider.dataSourceToString();
            if (!c.equals("")) {
                int posn = 0;
                while (posn < c.length()) {
                    int endposn = c.indexOf('\n', posn);
                    String line = c.substring(posn, endposn);
                    parseOnePattern(line);
                    posn = endposn + 1;
                }
            }
            System.out.println("\n\n");
            System.out.println("Count: "+ count);
            System.out.println("Num routes: " + RouteManager.getInstance().getNumRoutes());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parse one route pattern, adding it to the route that is named within it
     * @param str
     *
     * Each line begins with a capital N, which is not part of the route number, followed by the
     * bus route number, a dash, the pattern name, a semicolon, and a series of 0 or more real
     * numbers corresponding to the latitude and longitude (in that order) of a point in the pattern,
     * separated by semicolons. The 'N' that marks the beginning of the line is not part of the bus
     * route number.
     */
    private void parseOnePattern(String str) {
       // RouteMapParser p = new RouteMapParser("allroutemaps.txt");
        // TODO: Task 3: Implement this method
        String routeNumber = "([a-zA-Z0-9]{2,3})";
        String LatLon = ";([-0-9.]*;[-0-9.]*)";

        String extractedRouteName = "";
        String extractedRouteNum = "";

        String routeNum = "([a-zA-Z0-9]{2,3})-([a-zA-Z0-9-]{2,3})*";

        Pattern p = Pattern.compile(routeNum);
        Matcher m = p.matcher(str);

        if(m.find()){
            //System.out.println("Group Count: " + m.groupCount());
            extractedRouteName = m.group(1);
            //System.out.println("Route Name: " + extractedRouteName);
            extractedRouteNum = m.group(2);
            //System.out.println("Route Number: " + extractedRouteNum);
        }

        Pattern p1 = Pattern.compile(LatLon);
        Matcher m1 = p1.matcher(str);

        //System.out.println("\n\n");
        //System.out.println("LatLons---------");

        List<LatLon> latLons = new ArrayList<>();

        while(m1.find()){
            //System.out.println("Group Count: " + m1.groupCount());
            //System.out.println("Found value: " + m1.group(0));
            String latLonString = m1.group(1);
            //System.out.println("Found value: " + latLonString);
            String[] latLongArray = latLonString.split(";");
            double latitude = Double.parseDouble(latLongArray[0]);
            //System.out.println("Latitude: " + latitude);
            double longitude = Double.parseDouble(latLongArray[1]);
            //System.out.println("Longitude: " + longitude);
            LatLon latLon = new LatLon(latitude, longitude);
            latLons.add(latLon);
            //System.out.println("\n");
        }
/*
        //System.out.println("\n\n LATLON LIST----------------\n\n");
        for (LatLon latLon : latLons) {
            //System.out.println(latLon.toString());
        }*/

        storeRouteMap(extractedRouteNum, extractedRouteName, latLons);
        count++;
    }







    /**
     * Store the parsed pattern into the named route
     * Your parser should call this method to insert each route pattern into the corresponding route object
     * There should be no need to change this method
     *
     * @param routeNumber       the number of the route
     * @param patternName       the name of the pattern
     * @param elements          the coordinate list of the pattern
     */
    private void storeRouteMap(String routeNumber, String patternName, List<LatLon> elements) {
        Route r = RouteManager.getInstance().getRouteWithNumber(routeNumber);
        RoutePattern rp = r.getPattern(patternName);
        rp.setPath(elements);
    }
}
