package app.operators;

import java.util.Arrays;
import java.util.List;

import static app.operators.ArithmeticOperators.*;

public final class BitwiseOperators extends Operators{

    /***
     * Bitwise Operators (Relates with Arithmetic Operators)
     */
    public final static String SIGNED_RIGHT_SHIFT = ">>";
    public final static String SIGNED_LEFT_SHIFT = "<<";
    public final static String UNSIGNED_RIGHT_SHIFT = ">>>";

    public final static List<String> SIGNED_RIGHT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, PLUS, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> SIGNED_LEFT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, PLUS, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> UNSIGNED_RIGHT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, PLUS);

    public final static List<List<String>> OPERATORS = Arrays.asList(SIGNED_LEFT_SHIFT_OP, SIGNED_RIGHT_SHIFT_OP, UNSIGNED_RIGHT_SHIFT_OP);

    @Override
    List<List<String>> getOperators() {
        return OPERATORS;
    }

}
