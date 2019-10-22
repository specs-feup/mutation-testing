package app.operators;

import java.util.Arrays;
import java.util.List;
import static app.operators.BitwiseOperators.*;
public final class ArithmeticOperators extends Operators {

    /***
     * Arithmetic Operators (Relates with Bitwise)
     */
    public final static String PLUS = "+";
    public final static String MINUS = "-";
    public final static String MULTIPLY = "*";
    public final static String DIVIDE = "/";
    public final static String REMAINDER = "%";


    public final static List<String> PLUS_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> MINUS_OP = Arrays.asList(PLUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> MULTIPLY_OP = Arrays.asList(MINUS, PLUS, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> DIVIDE_OP = Arrays.asList(MINUS, MULTIPLY, PLUS, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> REMAINDER_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, PLUS, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);

    public final static List<List<String>> OPERATORS = Arrays.asList(PLUS_OP, MINUS_OP, MULTIPLY_OP, DIVIDE_OP, REMAINDER_OP);

    @Override
    List<List<String>> getOperators() {
        return OPERATORS;
    }
}
