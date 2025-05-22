package practice.mcpcise.mountain;

class ElevationOperator implements MountainOperator {
 
    private final int elevation;
    private final String operator;

    public ElevationOperator(int elevation, String operator) {
        this.elevation = elevation;
        this.operator = operator;
    }

    @Override
    public boolean operate(Mountain mountain) {
        if (mountain == null) {
            return false;
        }
        var mountainElevation = mountain.elevation();
        if (elevation < 0) {  // elevationが-1の場合は全ての山を対象とする
            return true;
        }
        if (operator == null || operator.isEmpty()) {
            return false;
        }
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
