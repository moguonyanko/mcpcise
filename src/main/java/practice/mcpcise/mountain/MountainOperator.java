package practice.mcpcise.mountain;

class MountainOperator {
 
    private final int elevation;
    private final String operator;

    public MountainOperator(int elevation, String operator) {
        this.elevation = elevation;
        this.operator = operator;
    }

    public boolean compare(int mountainElevation) {
        switch (operator) {
            case "<":
                return mountainElevation < elevation;
            case ">":
                return mountainElevation > elevation;
            case "=":
                return mountainElevation == elevation;
            case "<=":
                return mountainElevation <= elevation;
            case ">=":
                return mountainElevation >= elevation;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
