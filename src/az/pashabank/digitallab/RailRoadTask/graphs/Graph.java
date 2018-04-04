package az.pashabank.digitallab.RailRoadTask.graphs;

import az.pashabank.digitallab.RailRoadTask.rule.Condition;
import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import az.pashabank.digitallab.RailRoadTask.rule.RuleException;
import az.pashabank.digitallab.RailRoadTask.rule.Source;

import java.util.*;

public class Graph {
    private Map<String, Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new ArrayList<>();
    }

    public static Graph init(String argument) throws GraphException {
        String[] input = argument.split(",");

        if (input.length == 0)
            throw new GraphException("You must provide a directed graph for input to process");

        Graph graph = new Graph();
        for (String s : input) {
            s = s.trim();
            if (!s.matches("[A-Z]{2}[0-9]"))
                throw new GraphException("The provided argument does not match the input edge format: " + s);

            String fromNode = s.substring(0, 1);
            String toNode = s.substring(1, 2);
            String distance = s.substring(2, 3);

            Edge edge = new Edge(fromNode, toNode, Integer.valueOf(distance).intValue());
            graph.addEdge(edge);
        }
        return graph;
    }

    public void addNode(String nodeName) throws GraphException {
        if (!nodes.containsKey(nodeName))
            nodes.put(nodeName, new Node(nodeName));
    }

    public void addEdge(Edge edge) throws GraphException {

        if (!edges.contains(edge))
            edges.add(edge);

        addNode(edge.getFromNode());
        addNode(edge.getToNode());

        Node fromNode = nodes.get(edge.getFromNode());
        fromNode.addOutboutEdge(edge);
    }

    public Node getNode(String name) {
        return nodes.get(name);
    }


    public Collection<Node> getNodes() {
        return nodes.values();
    }

    public int findDistance(Route route) {

        try {
            int distance = 0;
            while (!route.finished()) {
                Node node = this.getNode(route.current());
                List<Edge> edges = node.getOutboundEdges();

                boolean found = false;
                String nextNode = route.next();
                for (Edge e : edges) {
                    if (nextNode.equals(e.getToNode())) {
                        distance += e.getDistance();
                        found = true;
                        break;
                    }
                }

                if (!found)
                    return -1;
            }
            return distance;
        } catch (GraphException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }

    public List<Route> findRoutes(String from, String to, Condition condition){

        Node node = getNode(from);

        if (node == null) {
            System.out.println("Node with the given name could not be found");
            return null;
        }

        List<Route> routes = new LinkedList<>();

        // setting the first node in the route
        Route route = new Route();
        route.addNode(node.getName(), 0);

        try {
            deepDive(routes, route, node, to, condition);
        }
        catch (RuleException ex){
            System.out.println(ex.getMessage());
        }
        catch (GraphException ex){
            System.out.println(ex.getMessage());
            return null;
        }

        return routes;
    }

    private void deepDive(List<Route> foundRoutes, Route route, Node node, String to, Condition condition) throws GraphException, RuleException {

        if (node == null)
            throw new GraphException("Node with the given name could not be found");

        List<Edge> edges = node.getOutboundEdges();
        for (Edge e : edges) {
            Route newRoute = route.clone();
            newRoute.addNode(e.getToNode(), e.getDistance());

            if (e.getToNode().equals(to) && condition.check(newRoute)) {
                foundRoutes.add(newRoute);
            }

            if (condition.shouldContinue(newRoute))
                deepDive(foundRoutes, newRoute, getNode(e.getToNode()), to, condition);
        }
    }

    public int findTripsCount(String from, String to, Operator operator, int value) {

        List<Route> routes = findRoutes(from, to, new Condition(Source.STOPS, operator, value));
        if (routes == null)
            return 0;
        return routes.size();
    }

    public long findShortestRoute(String from, String to) {

        // find maximum possible distance
        long totalDistance = 0;
        for(Edge e : edges){
            totalDistance += e.getDistance();
        }

        List<Route> routes = findRoutes(from, to, new Condition(Source.DISTANCE, Operator.SMALLER_THAN_EQUAL, totalDistance));

        if (routes == null || routes.size() == 0)
            return -1;

        long min = routes.get(0).getDistance();
        for(Route r: routes){
            if (r.getDistance() < min)
                min = r.getDistance();
        }

        return min;
    }

    public int findTotalRoutesCount (String from, String to, Operator operator, long value) {
        List<Route> routes = findRoutes(from, to, new Condition(Source.DISTANCE, operator, value));

        if (routes == null || routes.size() == 0)
            return -1;

        return routes.size();
    }

}
