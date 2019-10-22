package app.operators;

import java.util.Arrays;
import java.util.List;

public final class RelationalOperators extends Operators{

    /***
     * Relational Operators
     */
    public final static String MINOR = "<";
    public final static String MINOR_OR_EQUAL = "<=";
    public final static String GREATER = ">";
    public final static String GREATER_OR_EQUAL = ">=";
    public final static String DIFFERENT = "!=";
    public final static String EQUAL = "==";

    public final static List<String> MINOR_OP = Arrays.asList(GREATER, EQUAL, DIFFERENT, MINOR_OR_EQUAL, GREATER_OR_EQUAL);
    public final static List<String> MINOR_OR_EQUAL_OP = Arrays.asList(GREATER, MINOR, EQUAL, DIFFERENT,  GREATER_OR_EQUAL);
    public final static List<String> GREATER_OP = Arrays.asList(MINOR, EQUAL, DIFFERENT, MINOR_OR_EQUAL, GREATER_OR_EQUAL);
    public final static List<String> GREATER_OR_EQUAL_OP = Arrays.asList(MINOR, GREATER, EQUAL, DIFFERENT, MINOR_OR_EQUAL);
    public final static List<String> DIFFERENT_OP = Arrays.asList(GREATER, MINOR, EQUAL, MINOR_OR_EQUAL, GREATER_OR_EQUAL);
    public final static List<String> EQUAL_OP = Arrays.asList(GREATER, MINOR, DIFFERENT, MINOR_OR_EQUAL, GREATER_OR_EQUAL);


    public final static List<List<String>> OPERATORS = Arrays.asList(MINOR_OP, MINOR_OR_EQUAL_OP, GREATER_OP, GREATER_OR_EQUAL_OP, DIFFERENT_OP, EQUAL_OP);

    @Override
    List<List<String>> getOperators() {
        return OPERATORS;
    }
}
