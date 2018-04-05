package az.pashabank.digitallab.RailRoadTask.rule;

import az.pashabank.digitallab.RailRoadTask.graphs.GraphException;
import az.pashabank.digitallab.RailRoadTask.graphs.Route;

public class Condition {
    private Operator operator;
    private Source source;
    private long value;

    public Condition(Source source, Operator operator, long value) {
        this.operator = operator;
        this.source = source;
        this.value = value;
    }

    public boolean check (Route route) throws RuleException {

        long toCheck = 0;
        switch (source) {
            case DISTANCE:
                toCheck = route.getDistance();
                break;
            case STOPS:
                toCheck = route.getStops(); // don't count the first node as stop
                break;
            default:
                throw new RuleException ("Check function doesn't support other sources: " + source.toString());
        }

        switch (operator){
            case EQUAL:
                return toCheck == value;
            case GREATER:
                return toCheck > value;
            case GREATHER_THAN_EQUAL:
                return toCheck >= value;
            case SMALLER:
                return toCheck < value;
            case SMALLER_THAN_EQUAL:
                return toCheck <= value;
            default:
                throw new RuleException ("Check function does support this operation: " + operator.toString());
        }
    }

    public boolean shouldContinue (Route route) throws RuleException {

        long toCheck = 0;
        switch (source) {
            case DISTANCE:
                toCheck = route.getDistance();
                break;
            case STOPS:
                toCheck = route.getStops(); // don't count the first node as stop
                break;
            default:
                throw new RuleException("Check function doesn't support other sources: " + source.toString());
        }

        return toCheck <= (long)value;
    }
}
