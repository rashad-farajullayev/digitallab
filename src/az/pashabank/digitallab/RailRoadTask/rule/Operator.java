package az.pashabank.digitallab.RailRoadTask.rule;

public enum Operator {
    SMALLER,
    SMALLER_THAN_EQUAL,
    EQUAL,
    GREATHER_THAN_EQUAL,
    GREATER;

    public static Operator parse(String operation) {
        switch (operation)
        {
            case "<":
                return Operator.SMALLER;
            case "=":
                return Operator.EQUAL;
            case "<=":
            case "=<":
                return Operator.SMALLER_THAN_EQUAL;
            case ">":
                return Operator.GREATER;
            case ">=":
            case "=>":
                return Operator.GREATHER_THAN_EQUAL;
        }

        return Operator.EQUAL;
    }
}

