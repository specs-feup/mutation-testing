package app.operators;

import java.util.Arrays;
import java.util.List;


public final class UnaryOperators extends Operators{

    /***
     * Unary Operators
     */

    public final static String UNARY_PLUS = "+";
    public final static String UNARY_MINUS = "-";
    public final static String INCREMENT_BEFORE = "++_";
    public final static String INCREMENT_AFTER = "_++";
    public final static String DECREMENT_BEFORE = "--_";
    public final static String DECREMENT_AFTER = "_--";
    public final static String NEGATE = "!";


    public final static List<String> UNARY_PLUS_OP = Arrays.asList(UNARY_MINUS, INCREMENT_BEFORE, INCREMENT_AFTER, DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    public final static List<String> UNARY_MINUS_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, INCREMENT_AFTER, DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    public final static List<String> INCREMENT_BEFORE_OP = Arrays.asList(UNARY_PLUS, UNARY_MINUS, INCREMENT_AFTER, DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    public final static List<String> INCREMENT_AFTER_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, UNARY_MINUS, DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    public final static List<String> DECREMENT_BEFORE_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, INCREMENT_AFTER, UNARY_MINUS, DECREMENT_AFTER, NEGATE);
    public final static List<String> DECREMENT_AFTER_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, INCREMENT_AFTER, DECREMENT_BEFORE, UNARY_MINUS, NEGATE);
    public final static List<String> NEGATE_OP = Arrays.asList("null");


    public final static List<List<String>> OPERATORS = Arrays.asList(UNARY_PLUS_OP, UNARY_MINUS_OP, INCREMENT_BEFORE_OP,INCREMENT_AFTER_OP,DECREMENT_AFTER_OP,DECREMENT_BEFORE_OP,NEGATE_OP);

    @Override
    List<List<String>> getOperators() {
        return OPERATORS;
    }

}
