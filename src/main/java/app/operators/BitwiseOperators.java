package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

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
    private static final String DESCRIPTION = "Bitwise";
    private static final String MUTATOR_TYPE = "BinaryMutator";

    protected static final String SIGNED_RIGHT_SHIFT = ">>";
    protected static final String SIGNED_LEFT_SHIFT = "<<";
    protected static final String UNSIGNED_RIGHT_SHIFT = ">>>";

    private static final List<String> SIGNED_RIGHT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, PLUS, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);
    private static final List<String> SIGNED_LEFT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, PLUS, UNSIGNED_RIGHT_SHIFT);
    private static final List<String> UNSIGNED_RIGHT_SHIFT_OP = Arrays.asList(MINUS, MULTIPLY, DIVIDE, REMAINDER, SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, PLUS);

    private static final List<List<String>> MUTATORS = Arrays.asList(SIGNED_LEFT_SHIFT_OP, SIGNED_RIGHT_SHIFT_OP, UNSIGNED_RIGHT_SHIFT_OP);
    private static final List<String> OPERATORS = Arrays.asList(SIGNED_LEFT_SHIFT, SIGNED_RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT);

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
                mutatorString
                        .append("\tnew ")
                        .append(this.getMutatorType())
                        .append("(\"")
                        .append(mutator)
                        .append("\",\"")
                        .append(identifier.substring(identifier.indexOf(' ')+1))
                        .append("\"),\n");
            }
        }

        return mutatorString.toString();
    }
}
