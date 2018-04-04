package az.pashabank.digitallab.RailRoadTask.console;

import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandLineParserTest {

    CommandLineParser parser;

    @BeforeEach
    void setUp() {
        parser = new CommandLineParser();
    }

    @Test
    void parseHelp() {
        boolean result = parser.parse(new String[]{"-help"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.HELP);
    }

    @Test
    void parseHelpInvalid() {
        boolean result = parser.parse(new String[]{"-help routesCount -from:A -to:C -where<30"});
        assertFalse(result);

    }

    @Test
    void parseTest() {
        boolean result = parser.parse(new String[]{"-test"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.TEST);
    }

    @Test
    void parseTestInvalid() {
        boolean result = parser.parse(new String[]{"-test routesCount -from:A -to:C -where<30"});
        assertFalse(result);

    }

    @Test
    void parseInit ()
    {
        boolean result = parser.parse(new String[]{"-init", "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7", "directDistance", "-route:A-B-C"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.INIT);
        assertEquals(parser.getArgument(), "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        assertEquals(parser.getOperation(), Operation.DIRECT_DISTANCE);
        assertEquals(parser.getFrom(), "A-B-C");
    }

    @Test
    void parseInitWithNoOperation ()
    {
        boolean result = parser.parse(new String[]{"-init", "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7"});
        assertFalse(result, "The command must not successfully parsed, because operation is not specified");
    }

    @Test
    void parseLoad()
    {
        String path = "C:\\Development Projects/Sample Documents\\_grapth.txt";
        boolean result = parser.parse(new String[]{"-load", path, "directDistance", "-route:A-B-C"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.LOAD);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.DIRECT_DISTANCE);
        assertEquals(parser.getFrom(), "A-B-C");
    }

    @Test
    void parseDirectDistance()
    {
        String path = "C:\\Development Projects/Sample Documents\\_grapth.txt";
        boolean result = parser.parse(new String[]{"-load", path, "directDistance", "-route:A-C"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.LOAD);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.DIRECT_DISTANCE);
        assertEquals(parser.getFrom(), "A-C");
    }

    @Test
    void parseDirectDistance2()
    {
        String path = "C:\\Development Projects/Sample Documents\\_grapth.txt";
        boolean result = parser.parse(new String[]{"-load", path, "directDistance", "-route:A"});
        assertFalse(result);
    }

    @Test
    void parseDirectDistance3()
    {
        String path = "C:\\Development Projects/Sample Documents\\_grapth.txt";
        boolean result = parser.parse(new String[]{"-load", path, "directDistance", "-route:A-C-G-F-D-E-R-F-C-G-H"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.LOAD);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.DIRECT_DISTANCE);
        assertEquals(parser.getFrom(), "A-C-G-F-D-E-R-F-C-G-H");
    }

    @Test
    void shortestRoute()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "shortestRoute", "-from:A", "-to:C"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.INIT);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.SHORTEST_ROUTE);
        assertEquals(parser.getFrom(), "A");
        assertEquals(parser.getTo(), "C");
    }

    @Test
    void shortestRoute2()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "shortestRoute", "-from:A", "-to:C", "_where<30"});
        assertFalse(result);
    }

    @Test
    void tripsCount()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "tripsCount", "-from:W", "-to:Z", "-where<=15"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.INIT);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.TRIPS_COUNT);
        assertEquals(parser.getFrom(), "W");
        assertEquals(parser.getTo(), "Z");
        assertEquals(parser.getOperator(), Operator.SMALLER_THAN_EQUAL);
        assertEquals(parser.getValue(), 15);
    }

    @Test
    void routesCount()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "routesCount", "-from:W", "-to:Z", "-where>=15"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.INIT);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.ROUTES_COUNT);
        assertEquals(parser.getFrom(), "W");
        assertEquals(parser.getTo(), "Z");
        assertEquals(parser.getOperator(), Operator.GREATHER_THAN_EQUAL);
        assertEquals(parser.getValue(), 15);
    }
}