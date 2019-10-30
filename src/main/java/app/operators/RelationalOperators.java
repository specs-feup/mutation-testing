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
    public final static String TYPE = "Relational";

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


    public final static List<List<String>> MUTATORS = Arrays.asList(MINOR_OP, MINOR_OR_EQUAL_OP, GREATER_OP, GREATER_OR_EQUAL_OP, DIFFERENT_OP, EQUAL_OP);
    public final static List<String> OPERATORS = Arrays.asList(MINOR, MINOR_OR_EQUAL, GREATER, GREATER_OR_EQUAL, DIFFERENT, EQUAL);

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
