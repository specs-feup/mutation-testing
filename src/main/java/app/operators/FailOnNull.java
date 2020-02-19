package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.List;


public final class FailOnNull extends Operators{

    /***
     * Conditional Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private final static String DESCRIPTION = "Fail On Null";
    public static final String MUTATOR_TYPE = "FailOnNull";


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