package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public final class ConditionalOperators extends Operators{

    /***
     * Conditional Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private final static String DESCRIPTION = "Conditional";
    private static final String MUTATOR_TYPE = "BinaryMutator";

    protected final static String AND = "&&";
    protected final static String OR = "||";

    private final static List<String> AND_OP = Arrays.asList(OR);
    private final static List<String> OR_OP = Arrays.asList(AND);

    private final static List<List<String>> MUTATORS = Arrays.asList(AND_OP,OR_OP);
    private final static List<String> OPERATORS = Arrays.asList(AND,OR);

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