package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public final class BitWiseOperatorMutator extends Operators{

    /***
     * Unary Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private static final String DESCRIPTION = "BitWise Operator Mutator";
    private static final String MUTATOR_TYPE = "BitWiseOperatorMutator";

    protected static final String INCLUSIVE_OR = "|";
    protected static final String LEFT_HAND_SHIFT = "lhs";
    protected static final String EXCLUSIVE_OR = "^";
    protected static final String COMPLEMENT = "~";
    protected static final String RIGHT_HAND_SHIFT = "rhs";
    protected static final String AND = "&";
    protected static final String NULL = "";


    private static final List<String> INCLUSIVE_OP = Arrays.asList(LEFT_HAND_SHIFT, RIGHT_HAND_SHIFT,EXCLUSIVE_OR, AND);
    private static final List<String> AND_OP = Arrays.asList(LEFT_HAND_SHIFT, RIGHT_HAND_SHIFT, EXCLUSIVE_OR, INCLUSIVE_OR);
    private static final List<String> EXCLUSIVE_OP = Arrays.asList(LEFT_HAND_SHIFT, RIGHT_HAND_SHIFT,INCLUSIVE_OR, AND);
    private static final List<String> COMPLEMENT_OP = Collections.singletonList(NULL);



    private static final List<List<String>> MUTATORS = Arrays.asList(INCLUSIVE_OP, AND_OP, EXCLUSIVE_OP, COMPLEMENT_OP);
    private static final List<String> OPERATORS = Arrays.asList(INCLUSIVE_OR,EXCLUSIVE_OR,LEFT_HAND_SHIFT, RIGHT_HAND_SHIFT,AND);

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
            String operator = stringList.size()>1 ? OPERATORS.stream().filter(s -> !stringList.contains(s)).collect(Collectors.joining()) : "~";
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

    @Override
    public String getMutatorString(DataStore dataStore) {
        StringBuilder mutatorString = new StringBuilder();

        for (String identifier : this.getIdentifiers()){
            List<String> selectedMutators = (List<String>) dataStore.get(identifier);
            for(String mutator : selectedMutators){
                mutatorString.append("\tnew ")
                        .append(this.getMutatorType())
                        .append("(\"")
                        .append(identifier.substring(identifier.lastIndexOf(' ')+1))
                        .append("\",\"")
                        .append(mutator.trim())
                        .append("\"),\n");
            }
        }

        return mutatorString.toString();
    }
}