package az.pashabank.digitallab.RailRoadTask.console;

import az.pashabank.digitallab.RailRoadTask.rule.Operator;
import az.pashabank.digitallab.RailRoadTask.rule.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandLineParser {

    private Command command;
    private String argument;

    private Operation operation;
    private String from;
    private String to;
    private Source source;
    private Operator operator;
    private int value;

    public Command getCommand() {
        return command;
    }

    public String getArgument() {
        return argument;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Source getSource() {
        return source;
    }

    public Operator getOperator() {
        return operator;
    }

    public int getValue() {
        return value;
    }

    private List<CommandPattern> loaderPatterns;
    private List<String> operationPatterns;

    public CommandLineParser() {
        loaderPatterns = new ArrayList<>();
        loaderPatterns.add(new CommandPattern("-(?<command>init)\\s+(?<arguments>([A-Z]{2}\\d+,\\s*)+[A-Z]{2}\\d+)", true));
        loaderPatterns.add(new CommandPattern("-(?<command>load)\\s+(?<arguments>(.+\\.txt))", true));
        loaderPatterns.add(new CommandPattern("-(?<command>help)\\s*", false));
        loaderPatterns.add(new CommandPattern("-(?<command>test)\\s*", false));

        operationPatterns = new ArrayList<>();
        operationPatterns.add("(?<operation>directDistance)\\s+-route:(?<from>([A-Z]-)+[A-Z])\\s*");
        operationPatterns.add("(?<operation>shortestRoute)\\s+-from:(?<from>[A-Z])\\s+-to:(?<to>[A-Z])\\s*");
        operationPatterns.add("(?<operation>tripsCount)\\s+-from:(?<from>[A-Z])\\s+-to:(?<to>[A-Z])\\s+-where(?<operator>[<=>]+)(?<value>\\d+)\\s*");
        operationPatterns.add("(?<operation>routesCount)\\s+-from:(?<from>[A-Z])\\s+-to:(?<to>[A-Z])\\s+-where(?<operator>[<=>]+)(?<value>\\d+)\\s*");
    }

    public boolean parse (String[] args)
    {
        String cmdArgs = String.join(" ", args);

        for (CommandPattern p : loaderPatterns) {
            Pattern pattern = Pattern.compile(p.getPattern() + (p.isHasOperations() ? ".*" : ""));
            Matcher matcher = pattern.matcher(cmdArgs);

            if (!matcher.matches())
                continue;

            this.command = Command.valueOf(matcher.group("command").toUpperCase());
            this.argument = getVal(matcher, "arguments", "");

            if (!p.isHasOperations())
                return true;

            cmdArgs = cmdArgs.replaceFirst(p.getPattern() + "\\s+", "");

            for(String op: operationPatterns)
            {
                pattern = Pattern.compile(op);
                matcher = pattern.matcher(cmdArgs);

                if (!matcher.matches())
                    continue;

                this.operation = Operation.parse(getVal(matcher, "operation", ""));
                this.from = getVal(matcher, "from", "");
                this.to = getVal(matcher, "to", "");
                this.source = Source.parse(getVal(matcher, "source", ""));
                this.operator = Operator.parse(getVal(matcher, "operator", ""));
                this.value = Integer.valueOf(getVal(matcher, "value", "0"));
                return true;
            }

            break;
        }

        return false;
    }

    private String getVal(Matcher matcher, String groupName, String defaultValue)
    {
        // unfortunately there is no way in Java to know
        // if the given group is present in the matcher or not
        try {
            return matcher.group(groupName);
        } catch (Exception ex){
            return defaultValue;
        }
    }
}
