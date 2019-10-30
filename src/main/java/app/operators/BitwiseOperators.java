package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static app.operators.ArithmeticOperators.*;

public final class BitwiseOperators extends Operators{

    /***
     * Bitwise Operators (Relates with Arithmetic Operators)
     */

    private static List<String> identifiers = new ArrayList<>();
    public final static String TYPE = "Bitwise";

    public final static String SIGNED_RIGHT_SHIFT = ">>";
    public final static String SIGNED_LEFT_SHIFT = "<<";
    public final static String UNSIGNED_RIGHT_SHIFT = ">>>";

    public final static List<String> SIGNED_RIGHT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, PLUS, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> SIGNED_LEFT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, PLUS, UNSIGNED_RIGHT_SHIFT);
    public final static List<String> UNSIGNED_RIGHT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, PLUS);

    public final static List<List<String>> MUTATORS = Arrays.asList(SIGNED_LEFT_SHIFT_OP, SIGNED_RIGHT_SHIFT_OP, UNSIGNED_RIGHT_SHIFT_OP);
    public final static List<String> OPERATORS = Arrays.asList(SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);

    @Override
    public List<List<String>> getMutators() {
        return MUTATORS;
    }

    @Override
    public List<String> getOperators() {
        return OPERATORS;
    }

    @Override
    public List<DataKey> getDataKeys() {
        List<DataKey> dataKeysList = new ArrayList<>();
        MUTATORS.forEach(stringList -> {
            String operator = OPERATORS.stream().filter(s -> !stringList.contains(s)).collect(Collectors.joining());
            String operatorIdentifier = TYPE+" "+operator;
            identifiers.add(operatorIdentifier);
            dataKeysList.add(
                KeyFactory.multipleStringList(operatorIdentifier, stringList).setLabel(operator+"  ")
                        .setDefault(() -> stringList));
        });
        return dataKeysList;
    }

    @Override
    public List<String> getIdentifiers() {
        return identifiers;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
