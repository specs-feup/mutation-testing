package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public final class ArithmeticOperatorDeletion extends Operators{

    /***
     * Conditional Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private final static String DESCRIPTION = "Arithmetic Operators Deletion";
    public static final String MUTATOR_TYPE = "ArithmeticOperatorDeletionMutator";


    @Override
    public List<List<String>> getMutators() {
        return null;
    }

    @Override
    public List<String> getOperators() {
        return null;
    }

    @Override
    public List<DataKey> getDataKeys()  {
        List<DataKey> dataKeysList = new ArrayList<>();

        identifiers.add(getDescription());
        dataKeysList.add(
                KeyFactory.bool(getDescription()).setLabel(getDescription())
        );

        return dataKeysList;
    }

    @Override
    public List<String> getIdentifiers() {
        return Collections.singletonList(getDescription());
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
            Boolean selectedMutators = (Boolean) dataStore.get(identifier);

            if(selectedMutators)
                mutatorString
                        .append("\tnew ")
                        .append(this.getMutatorType())
                        .append("(),\n");

        }

        return mutatorString.toString();
    }
}