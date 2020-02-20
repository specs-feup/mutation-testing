package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class NonVoidCallMutator extends Operators{

    /***
     * Conditional Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private final static String DESCRIPTION = "Non Void Call Mutator";
    public static final String MUTATOR_TYPE = "NonVoidCallMutator";


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
}