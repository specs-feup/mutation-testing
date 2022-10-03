package app.operators;

import static app.operators.BitwiseOperators.SIGNED_LEFT_SHIFT;
import static app.operators.BitwiseOperators.SIGNED_RIGHT_SHIFT;
import static app.operators.BitwiseOperators.UNSIGNED_RIGHT_SHIFT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

public final class ArithmeticOperators extends Operators {

    /***
     * Arithmetic Operators (Relates with Bitwise)
     */
    private static List<String> identifiers = new ArrayList<>();
    private static final String DESCRIPTION = "Arithmetic";
    private static final String MUTATOR_TYPE = "BinaryMutator";

    protected static final String PLUS = "+";
    protected static final String MINUS = "-";
    protected static final String MULTIPLY = "*";
    protected static final String DIVIDE = "/";
    protected static final String REMAINDER = "%";

    private static final List<String> PLUS_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT,
            SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private static final List<String> MINUS_OP = Arrays.asList(PLUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT,
            SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private static final List<String> MULTIPLY_OP = Arrays.asList(MINUS, PLUS, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT,
            SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private static final List<String> DIVIDE_OP = Arrays.asList(MINUS, MULTIPLY, PLUS, REMAINDER, SIGNED_LEFT_SHIFT,
            SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private static final List<String> REMAINDER_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, PLUS, SIGNED_LEFT_SHIFT,
            SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);

    private static final List<List<String>> MUTATORS = Arrays.asList(PLUS_OP, MINUS_OP, MULTIPLY_OP, DIVIDE_OP,
            REMAINDER_OP);
    private static final List<String> OPERATORS = Arrays.asList(PLUS, MINUS, MULTIPLY, DIVIDE, REMAINDER);

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
            String operatorIdentifier = DESCRIPTION + " " + operator;
            identifiers.add(operatorIdentifier);
            dataKeysList.add(
                    KeyFactory.multipleStringList(operatorIdentifier, stringList).setLabel(operator + "  ")
                            .setDefault(() -> new ArrayList<>(stringList)));
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

    @Override
    public String getMutatorString(DataStore dataStore) {
        StringBuilder mutatorString = new StringBuilder();

        for (String identifier : this.getIdentifiers()) {
            List<String> selectedMutators = (List<String>) dataStore.get(identifier);
            for (String mutator : selectedMutators) {
                mutatorString
                        .append("\tnew ")
                        .append(this.getMutatorType())
                        .append("(\"")
                        .append(mutator)
                        .append("\",\"")
                        .append(identifier.substring(identifier.indexOf(' ') + 1))
                        .append("\"),\n");
            }
        }

        return mutatorString.toString();
    }
}
