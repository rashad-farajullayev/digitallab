package az.pashabank.digitallab.RailRoadTask.console;

public enum Operation {
    UNSPECIFIED,
    DIRECT_DISTANCE,
    SHORTEST_ROUTE,
    TRIPS_COUNT,
    ROUTES_COUNT;

    public static Operation parse (String value){

        if ("directDistance".equalsIgnoreCase(value))
            return Operation.DIRECT_DISTANCE;

        if ("shortestRoute".equalsIgnoreCase(value))
            return Operation.SHORTEST_ROUTE;

        if ("tripsCount".equalsIgnoreCase(value))
            return Operation.TRIPS_COUNT;

        if ("routesCount".equalsIgnoreCase(value))
            return Operation.ROUTES_COUNT;

        return Operation.UNSPECIFIED;
    }
}
