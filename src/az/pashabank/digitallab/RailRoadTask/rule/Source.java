package az.pashabank.digitallab.RailRoadTask.rule;

public enum Source {
    STOPS,
    DISTANCE;

    public static Source parse (String value){
        return value.equalsIgnoreCase("s") ? Source.STOPS: Source.DISTANCE;
    }
}
