package az.pashabank.digitallab.RailRoadTask;

import az.pashabank.digitallab.RailRoadTask.graphs.GraphException;
import az.pashabank.digitallab.RailRoadTask.rule.Condition;
import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import az.pashabank.digitallab.RailRoadTask.console.CommandLineParser;
import az.pashabank.digitallab.RailRoadTask.graphs.Graph;
import az.pashabank.digitallab.RailRoadTask.graphs.Route;
import az.pashabank.digitallab.RailRoadTask.rule.Source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args)  {

        // initializing the command line parser
        // and parsing the input command
        CommandLineParser parser = new CommandLineParser();
        if (!parser.parse(args)) {
            System.out.println("Could not parse command line arguments. Please see -help for operation instructions");
            return;
        }

        String contents;
        switch (parser.getCommand())
        {
            case TEST:
                // this function will print out the samples as has been stated in the
                // code exercise email:
                // You should provide sufficient evidence that your solution is complete by,
                // as a minimum, indicating that it works correctly against the supplied test data
                runSampleTests();
                return;
            case HELP:
                // print out the usage instructions
                printHelp();
                return;
            case LOAD:
                contents = loadFileContents(parser.getArgument());
                if (contents == null)
                    return;
                break;
            case INIT:
                contents = parser.getArgument();
                break;
            default:
                return;
        }

        // initialize the graph with the given input
        // the input is supposed to be in the "AB5, CD6,..."
        // format as described in the email
        Graph graph = null;
        try {
            graph = Graph.init(contents);
        } catch (GraphException e) {
            System.out.println(e.getMessage());
            return;
        }

        // calling necessary operation based on what the user has requested
        switch (parser.getOperation())
        {
            case DIRECT_DISTANCE:
                // this function calculates the distance of the specified route
                // if no such route available it returns -1
                Route r = new Route(parser.getFrom().replaceAll("-", ""));
                System.out.println(w(graph.findDistance(r)));
                break;
            case ROUTES_COUNT:
                // Here we are counting total possible number of routes
                // from one node to another node
                System.out.println(w(graph.findTotalRoutesCount(parser.getFrom(), parser.getTo(), parser.getOperator(), parser.getValue())));
                break;
            case SHORTEST_ROUTE:
                // Here we display the shortest possible route between two nodes
                System.out.println(w(graph.findShortestRoute(parser.getFrom(), parser.getTo())));
                break;
            case TRIPS_COUNT:
                // Calculating number of possible trips between two nodes
                System.out.println(w(graph.findTripsCount(parser.getFrom(), parser.getTo(), parser.getOperator(), parser.getValue())));
                break;
        }
    }

    // Reading contents of the text file and preparing it for Graph as input
    public static String loadFileContents (String fileName){
        if (Files.exists(Paths.get(fileName))){
            String fileContent = null;
            try {
                fileContent = new String(Files.readAllBytes(Paths.get(fileName))).replaceAll("\r\n", "");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return null;
            }

            if (!fileContent.matches("([A-Z]{2}[0-9]+\\s*,?\\s*)+")){
                System.out.println("File body doesn't match expected format. See -help for operation instructions");
                return null;
            }
            return fileContent;
        }
        else {
            System.out.println("Could not load from file " + fileName + ". File doesn't exist");
            return null;
        }
    }

    // This function runs the sample test as an evidence, to prove that it works
    private static void runSampleTests()  {
        Graph graph = null;

        try {
            graph = Graph.init("AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7");
        } catch (GraphException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Output #1: " + w(graph.findDistance( new Route("ABC"))));
        System.out.println("Output #2: " + w(graph.findDistance( new Route ("AD"))));
        System.out.println("Output #3: " + w(graph.findDistance( new Route ("ADC"))));
        System.out.println("Output #4: " + w(graph.findDistance( new Route ("AEBCD"))));
        System.out.println("Output #5: " + w(graph.findDistance( new Route ("AED"))));
        System.out.println("Output #6: " + w(graph.findTripsCount("C", "C", Operator.SMALLER_THAN_EQUAL, 3)));
        System.out.println("Output #7: " + w(graph.findTripsCount("A", "C", Operator.EQUAL, 4)));
        System.out.println("Output #8: " + w(graph.findShortestRoute("A", "C")));
        System.out.println("Output #9: " + w(graph.findShortestRoute("B", "B")));
        System.out.println("Output #10: " + w(graph.findTotalRoutesCount("C", "C", Operator.SMALLER, 30)));
    }

    private static void printHelp(){
        System.out.println("This is the coding exercise task for DigitalLab developed by Rashad Farajullayev\n" +
                "Below you can see the operation instructiions:\n" +
                "\n" +
                "-help\n" +
                "   To type the operation instructions as you see here\n" +
                "   \n" +
                "-test\n" +
                "   To run the sample tests as have been provided in the coding exercise e-mail\n" +
                "   This is to demonstrate that the code can calculate the requested parameters \n" +
                "   and the output results meet the expectations\n" +
                "   \n" +
                "--init \"<graph_initialization>\" <operation> <options>\n" +
                "   This will init the graph with the input data. The input must follow the for-\n" +
                "   mat described in the email. \n" +
                "      Example:\n" +
                "      -init \"AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7\" <operation> <options>\n" +
                "   Please note the graph initialization string must be enclosed in quotation marks,\n" +
                "   and this command must be followed by the operation and corresponding options. \n" +
                "   For the operation and options see below\n" +
                "\n" +
                "--load <path-file.txt> <operation> <options>\n" +
                "   This will load graph initialization text from a text file. Text file's name must \n" +
                "   have a .txt extension. The initialization line inside of the file can be suplied \n" +
                "   in one line or be divided into multiple lines. This command must follow the \n" +
                "   operation and its corresponding options.\n" +
                "       Example:\n" +
                "      -load C:\\sample.txt directDistance -route:A-B-C\n" +
                "   In the above example you may see the \"directDistance\" operation and its options\t  \n" +
                "\n" +
                "   Operations and their options:\n" +
                "   ------------------------------   \n" +
                "   directDistance -route:<node-names>\n" +
                "      This will calculate the distance of the route. The route can be specified as \n" +
                "      dash separated node names:\n" +
                "      For example, if you want to calculate route distance from A to B and then \n" +
                "      to B to C, you can specify as \"directDistance -route:A-B-C\"\n" +
                "      Example:  \n" +
                "        -init \"AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7\" directDistance -route:A-B-C\n" +
                "\n" +
                "   shortestRoute -from:<from-node> -to:<to-node>\n" +
                "        This operation will calculate the length of the shortest route (in terms of \n" +
                "        distance to travel) from the <from-node> to <to-node>\n" +
                "        Example: \n" +
                "        -load fileName.dat shortestRoute -from:A -to:C\n" +
                "\n" +
                "        tripsCount -from:<from-node> -to:<to-node> -where<condition>\n" +
                "        This operation calculate the count of trips between <from-node> and <to-node>\n" +
                "        which meets the <contition> for the number of stops. The condition consists \n" +
                "        of comparosion operator and the value to compare to. For example, you may want \n" +
                "        to calculate the number of trips from A to C whith maximum of three stops. \n" +
                "           In this case you can type:\n" +
                "           -load fileName.dat tripsCount -from:A -to:C -where^<30\n" +
                "        The -where option accepts operators like <, <=, =, >=, > for comparison and \n" +
                "        the target value to compare against. Please note, there must be no symbol between \n" +
                "        this option and its parameters but the \"^\" symbol to escape special symbols\n" +
                "\n" +
                "    routesCount -from:<from-node> -to:<to-node> -where<condition>\n" +
                "            This will calculate number of different routes between nodes meeting the condition.\n" +
                "        For example, if you want to calculate the number of different routes from C to C \n" +
                "        with a distance of less than 30, then you can type:\n" +
                "           -load fileName.dat routesCount -from:A -to:C -where^<30\n" +
                "        The -where option accepts operators like <, <=, =, >=, > for comparison and \n" +
                "        the target value to compare against. Don't forget to escape special symbols for \n" +
                "        this option by using \"^\" symbol.\n" +
                "        NOTE: where option in this operation compares total route length, not trips count\n" +
                "\n" +
                "Examples:\n" +
                "  -load fileName.dat directDistance -route:A-B-C\n" +
                "  -init \"AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7\" directDistance  -route:A-B-C\n" +
                "  -load fileName.dat shortestRoute -from:A -to:C\n" +
                "  -load fileName.dat tripsCount -from:A -to:C -where^<30\n" +
                "  -load fileName.dat routesCount -from:A -to:C -where^<30");
    }

    // Helper function to return required text if the route is not found
    // This function just for the sake of code beauty
    private static String w (long d){ return d < 0 ? "NO SUCH ROUTE" : Long.toString(d);}
}
