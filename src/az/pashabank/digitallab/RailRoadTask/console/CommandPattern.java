package az.pashabank.digitallab.RailRoadTask.console;

public class CommandPattern {

    private String pattern;
    private boolean hasOperations;

    public CommandPattern(String pattern, boolean hasOperations) {
        this.pattern = pattern;
        this.hasOperations = hasOperations;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean isHasOperations() {
        return hasOperations;
    }
}
