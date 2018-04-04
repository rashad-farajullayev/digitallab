package az.pashabank.digitallab.RailRoadTask.graphs;

import java.security.InvalidAlgorithmParameterException;

public class Route {

    private StringBuilder route;
    private long distance;
    private int currentIndex;

    public Route (){
        currentIndex = 0;
        route = new StringBuilder();
    }

    public Route(String route) {
        this();
        this.route = new StringBuilder();
        this.route.append(route);
    }

    public void addNode (String node, int distance)
    {
        route.append(node);
        this.distance += distance;
    }

    public StringBuilder getRoute() {
        return route;
    }

    public long getDistance() {
        return distance;
    }

    public int getStops(){
        return this.route.length() - 1;
    }

    public String current() throws GraphException {
        if (currentIndex >= route.length())
            throw new GraphException("The current index states way beyond the boundaries of the route");

        return route.substring(currentIndex, currentIndex + 1);
    }

    public String next () throws GraphException {
        currentIndex ++;
        return current();
    }

    public boolean finished (){
        return currentIndex >= route.length() - 1;
    }

    public Route clone (){
        Route route = new Route();
        route.route.append(this.route.toString());
        route.currentIndex = this.currentIndex;
        route.distance = this.distance;
        return route;
    }
}
