package az.pashabank.digitallab.RailRoadTask;

import az.pashabank.digitallab.RailRoadTask.graphs.GraphException;
import az.pashabank.digitallab.RailRoadTask.graphs.Graph;
import az.pashabank.digitallab.RailRoadTask.graphs.Route;
import az.pashabank.digitallab.RailRoadTask.rule.Condition;
import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import az.pashabank.digitallab.RailRoadTask.rule.Source;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MyGraphTest {
    Graph graph;

    @BeforeEach
    void setUp() throws GraphException {
        graph = Graph.init("AQ5, QP8, PJ6, JO4, OG8, GT10, TL8, LN10, NC8, CI12, IM12, MS5, SR10, RB7, BE16, EH6, HV3, VU10, UF11, FW3, WX3, XD4, DY5, YZ12, ZK14, KP7, RA5, AM8, SN9, NT9, TK5, KJ11, JE9, OH7, VF5, UK6, UD11, GW7, IK10, IR9, XG7");
    }

    @Test
    void shortestPath(){
        long result = graph.findShortestRoute("Q", "Y");
        assertEquals(45, result , "Shortest between Q and Y");
    }

    @Test
    void numberOfRoutes(){
        graph.setNodeMaxRevisitCount(1);
        List<Route> routes = graph.findRoutes("Q", "Y", new Condition(Source.DISTANCE, Operator.SMALLER, 1000), true);
        for (Route r: routes)
        {
            System.out.println(r.getRoute() + " " + r.getDistance());
        }
        assertEquals(7360, routes.size());
    }
}
