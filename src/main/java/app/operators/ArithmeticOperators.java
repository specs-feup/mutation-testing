package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static app.operators.BitwiseOperators.*;
public final class ArithmeticOperators extends Operators {

    /***
     * Arithmetic Operators (Relates with Bitwise)
     */
    private static List<String> identifiers = new ArrayList<>();
    private final static String DESCRIPTION = "Arithmetic";
    private static final String MUTATOR_TYPE = "BinaryMutator";

    protected final static String PLUS = "+";
    protected final static String MINUS = "-";
    protected final static String MULTIPLY = "*";
    protected final static String DIVIDE = "/";
    protected final static String REMAINDER = "%";


    private final static List<String> PLUS_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private final static List<String> MINUS_OP = Arrays.asList(PLUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private final static List<String> MULTIPLY_OP = Arrays.asList(MINUS, PLUS, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private final static List<String> DIVIDE_OP = Arrays.asList(MINUS, MULTIPLY, PLUS, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private final static List<String> REMAINDER_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, PLUS, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);

    private final static List<List<String>> MUTATORS = Arrays.asList(PLUS_OP, MINUS_OP, MULTIPLY_OP, DIVIDE_OP, REMAINDER_OP);
    private final static List<String> OPERATORS = Arrays.asList(PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER);

    @Override
    public List<List<String>> getMutators() {
        return MUTATORS;
    }

    @Override
    public List<String> getOperators() {
        return OPERATORS;
    }

    @Override
    public List<DataKey> getDataKeys()  {
        List<DataKey> dataKeysList = new ArrayList<>();
        MUTATORS.forEach(stringList -> {
            String operator = OPERATORS.stream().filter(s -> !stringList.contains(s)).collect(Collectors.joining());
            String operatorIdentifier = DESCRIPTION +" "+operator;
            identifiers.add(operatorIdentifier);
            dataKeysList.add(
                KeyFactory.multipleStringList(operatorIdentifier, stringList).setLabel(operator+"  ")
                        .setDefault(() -> stringList));
        });
        return dataKeysList;
    }

    @Override
    public String getMutatorType() {
        return MUTATOR_TYPE;
    }

    @Override
    public List<String> getIdentifiers() {
        return identifiers;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

}
