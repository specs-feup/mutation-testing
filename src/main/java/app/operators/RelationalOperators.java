package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class RelationalOperators extends Operators{

    /***
     * Relational Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private final static String DESCRIPTION = "Relational";
    private static final String MUTATOR_TYPE = "BinaryMutator";

    protected final static String MINOR = "<";
    protected final static String MINOR_OR_EQUAL = "<=";
    protected final static String GREATER = ">";
    protected final static String GREATER_OR_EQUAL = ">=";
    protected final static String DIFFERENT = "!=";
    protected final static String EQUAL = "==";

    private final static List<String> MINOR_OP = Arrays.asList(GREATER, EQUAL, DIFFERENT, MINOR_OR_EQUAL, GREATER_OR_EQUAL);
    private final static List<String> MINOR_OR_EQUAL_OP = Arrays.asList(GREATER, MINOR, EQUAL, DIFFERENT,  GREATER_OR_EQUAL);
    private final static List<String> GREATER_OP = Arrays.asList(MINOR, EQUAL, DIFFERENT, MINOR_OR_EQUAL, GREATER_OR_EQUAL);
    private final static List<String> GREATER_OR_EQUAL_OP = Arrays.asList(MINOR, GREATER, EQUAL, DIFFERENT, MINOR_OR_EQUAL);
    private final static List<String> DIFFERENT_OP = Arrays.asList(GREATER, MINOR, EQUAL, MINOR_OR_EQUAL, GREATER_OR_EQUAL);
    private final static List<String> EQUAL_OP = Arrays.asList(GREATER, MINOR, DIFFERENT, MINOR_OR_EQUAL, GREATER_OR_EQUAL);


    private final static List<List<String>> MUTATORS = Arrays.asList(MINOR_OP, MINOR_OR_EQUAL_OP, GREATER_OP, GREATER_OR_EQUAL_OP, DIFFERENT_OP, EQUAL_OP);
    private final static List<String> OPERATORS = Arrays.asList(MINOR, MINOR_OR_EQUAL, GREATER, GREATER_OR_EQUAL, DIFFERENT, EQUAL);

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
}