package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public final class UnaryOperators extends Operators{

    /***
     * Unary Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    public final static String TYPE = "Unary";

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
    public final static List<String> NEGATE_OP = Collections.singletonList("undefined");


    public final static List<List<String>> MUTATORS = Arrays.asList(UNARY_PLUS_OP, UNARY_MINUS_OP, INCREMENT_BEFORE_OP,INCREMENT_AFTER_OP,DECREMENT_AFTER_OP,DECREMENT_BEFORE_OP,NEGATE_OP);
    public final static List<String> OPERATORS = Arrays.asList(UNARY_PLUS, UNARY_MINUS, INCREMENT_BEFORE,INCREMENT_AFTER,DECREMENT_AFTER,DECREMENT_BEFORE,NEGATE);

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
            String operator = stringList.size()>1 ? OPERATORS.stream().filter(s -> !stringList.contains(s)).collect(Collectors.joining()) : "!";
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
