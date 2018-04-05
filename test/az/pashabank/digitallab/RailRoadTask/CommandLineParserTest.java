package az.pashabank.digitallab.RailRoadTask;

import az.pashabank.digitallab.RailRoadTask.console.Command;
import az.pashabank.digitallab.RailRoadTask.console.CommandLineParser;
import az.pashabank.digitallab.RailRoadTask.console.Operation;
import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Assertions.assertEquals(parser.getCommand(), Command.HELP);
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
        Assertions.assertEquals(parser.getOperation(), Operation.DIRECT_DISTANCE);
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
        assertEquals(true, parser.getRevisit());
    }

    @Test
    void tripsCount2()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "tripsCount", "-from:W", "-to:Z", "-where<=15", "-revisit:t"});
        assertTrue(result);
        assertEquals(parser.getCommand(), Command.INIT);
        assertEquals(parser.getArgument(), path);
        assertEquals(parser.getOperation(), Operation.TRIPS_COUNT);
        assertEquals(parser.getFrom(), "W");
        assertEquals(parser.getTo(), "Z");
        assertEquals(parser.getOperator(), Operator.SMALLER_THAN_EQUAL);
        assertEquals(parser.getValue(), 15);
        assertEquals(true, parser.getRevisit());
    }

    @Test
    void routesCount()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "routesCount", "-from:W", "-to:Z", "-where>=15"});
        assertTrue(result);
        assertEquals(Command.INIT, parser.getCommand());
        assertEquals(path, parser.getArgument());
        assertEquals(Operation.ROUTES_COUNT, parser.getOperation());
        assertEquals("W", parser.getFrom());
        assertEquals("Z", parser.getTo());
        assertEquals(Operator.GREATHER_THAN_EQUAL, parser.getOperator());
        assertEquals(15, parser.getValue());
        assertEquals(true, parser.getRevisit());
    }

    @Test
    void matchTest()
    {
        String input = "routesCount -from:W -to:Z -where>=15 -revisit:f -maxvisits:10";
        Pattern pattern = Pattern.compile("(?<operation>routesCount)\\s+-from:(?<from>[A-Z])\\s+-to:(?<to>[A-Z])\\s+-where(?<operator>[<=>]+)(?<value>\\d+)(\\s+-revisit:(?<revisit>[tf]))?(\\s+-maxvisits:(?<maxvisits>[0-9]+))?\\s*");

        Matcher matcher = pattern.matcher(input);
        assertTrue(matcher.matches());
    }
    @Test
    void routesCount2()
    {
        String path = "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7";
        boolean result = parser.parse(new String[]{"-init", path, "routesCount", "-from:W", "-to:Z", "-where>=15", "-revisit:f", "-maxvisits:1"});
        assertTrue(result);
        assertEquals(Command.INIT, parser.getCommand());
        assertEquals(path, parser.getArgument());
        assertEquals(Operation.ROUTES_COUNT, parser.getOperation());
        assertEquals("W", parser.getFrom());
        assertEquals("Z", parser.getTo());
        assertEquals(Operator.GREATHER_THAN_EQUAL, parser.getOperator());
        assertEquals(15, parser.getValue());
        assertEquals(false, parser.getRevisit());
        assertEquals(1, parser.getMaxVisits());
    }
}