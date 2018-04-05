package az.pashabank.digitallab.RailRoadTask;

import az.pashabank.digitallab.RailRoadTask.graphs.GraphException;
import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import az.pashabank.digitallab.RailRoadTask.graphs.Edge;
import az.pashabank.digitallab.RailRoadTask.graphs.Graph;
import az.pashabank.digitallab.RailRoadTask.graphs.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph graph;

    @BeforeEach
    void setUp() throws GraphException {
        graph = Graph.init("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
    }

    @Test
    void init() throws GraphException {
        Graph g = Graph.init("AB1, AC2, AD3");
        Node nodeA = g.getNode("A");
        assertNotNull(nodeA, "Node A should have been found from the graph");
        List<Edge> edges = nodeA.getOutboundEdges();
        assertEquals(3, edges.size() , "Nodes must have had 3 edges");
    }

    @Test
    void addNode() throws GraphException {
        Graph graph = new Graph();
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("A");

        assertEquals(2, graph.getNodes().size(), "Nodes with the same name actually equal to each other");
    }

    @Test
    void findTripsCount() throws GraphException {
        int result = graph.findTripsCount("C", "C", Operator.SMALLER_THAN_EQUAL, 3, true, 0);
        assertEquals(2, result, "There must be 2 trips from the search criteria");

        result = graph.findTripsCount("A", "C", Operator.EQUAL, 4, true, 0);
        assertEquals(3, result, "There must be exactly 3 trips with 4 stops");
    }

    @Test
    void findShortestRoute() throws GraphException {

        long result = graph.findShortestRoute("A", "C");
        assertEquals(9, result, "Shortest distance from A to C must be equal to 9");

        result = graph.findShortestRoute("B", "B");
        assertEquals(9, result, "Distance from B to B must be equal to 9");
    }

    @Test
    void findTotalRoutesCount() throws GraphException {
        long count = graph.findTotalRoutesCount("C", "C", Operator.SMALLER, 30, true, 0);
        assertEquals(7, count, "There must be 7 total routes from C to C with distance smaller than 30");
    }
}