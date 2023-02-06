package app.operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

public final class UnaryOperators extends Operators {

    /***
     * Unary Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private static final String DESCRIPTION = "Unary";
    private static final String MUTATOR_TYPE = "UnaryMutator";

    protected static final String UNARY_PLUS = "+";
    protected static final String UNARY_MINUS = "-";
    protected static final String INCREMENT_BEFORE = "++_";
    protected static final String INCREMENT_AFTER = "_++";
    protected static final String DECREMENT_BEFORE = "--_";
    protected static final String DECREMENT_AFTER = "_--";
    protected static final String NEGATE = "!";

    private static final List<String> UNARY_PLUS_OP = Arrays.asList(UNARY_MINUS, INCREMENT_BEFORE, INCREMENT_AFTER,
            DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    private static final List<String> UNARY_MINUS_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, INCREMENT_AFTER,
            DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    private static final List<String> INCREMENT_BEFORE_OP = Arrays.asList(UNARY_PLUS, UNARY_MINUS, INCREMENT_AFTER,
            DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    private static final List<String> INCREMENT_AFTER_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, UNARY_MINUS,
            DECREMENT_BEFORE, DECREMENT_AFTER, NEGATE);
    private static final List<String> DECREMENT_BEFORE_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, INCREMENT_AFTER,
            UNARY_MINUS, DECREMENT_AFTER, NEGATE);
    private static final List<String> DECREMENT_AFTER_OP = Arrays.asList(UNARY_PLUS, INCREMENT_BEFORE, INCREMENT_AFTER,
            DECREMENT_BEFORE, UNARY_MINUS, NEGATE);
    private static final List<String> NEGATE_OP = Collections.singletonList("undefined");

    private static final List<List<String>> MUTATORS = Arrays.asList(UNARY_PLUS_OP, UNARY_MINUS_OP, INCREMENT_BEFORE_OP,
            INCREMENT_AFTER_OP, DECREMENT_AFTER_OP, DECREMENT_BEFORE_OP, NEGATE_OP);
    private static final List<String> OPERATORS = Arrays.asList(UNARY_PLUS, UNARY_MINUS, INCREMENT_BEFORE,
            INCREMENT_AFTER, DECREMENT_AFTER, DECREMENT_BEFORE, NEGATE);

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
            String operator = stringList.size() > 1
                    ? OPERATORS.stream().filter(s -> !stringList.contains(s)).collect(Collectors.joining())
                    : "!";
            String operatorIdentifier = DESCRIPTION + " " + operator;
            identifiers.add(operatorIdentifier);
            dataKeysList.add(
                    KeyFactory.multipleStringList(operatorIdentifier, stringList).setLabel(operator + "  ")
                            .setDefault(() -> new ArrayList<>(stringList)));
        });
        return dataKeysList;
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
    public String getMutatorType() {
        return MUTATOR_TYPE;
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
                        .append(identifier.substring(identifier.indexOf(' ') + 1))
                        .append("\",\"")
                        .append(mutator)
                        .append("\"),\n");
            }
        }

        return mutatorString.toString();
    }
}