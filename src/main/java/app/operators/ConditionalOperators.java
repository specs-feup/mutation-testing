package app.operators;

import java.util.Arrays;
import java.util.List;


public final class ConditionalOperators extends Operators{

    /***
     * Conditional Operators
     */

    public final static String AND = "&&";
    public final static String OR = "||";

    public final static List<String> AND_OP = Arrays.asList(OR);
    public final static List<String> OR_OP = Arrays.asList(AND);

    public final static List<List<String>> OPERATORS = Arrays.asList(AND_OP,OR_OP);

    @Override
    List<List<String>> getOperators() {
        return OPERATORS;
    }

}
