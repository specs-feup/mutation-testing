package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public final class ConstantMutator extends Operators{

    /***
     * Unary Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private static final String DESCRIPTION = "Constant Mutator";
    private static final String MUTATOR_TYPE = "ConstantMutator";

    protected static final String CONSTANT_TO_ONE = "Constant to 1";
    protected static final String CONSTANT_TO_ZERO = "Constant to 0";
    protected static final String CONSTANT_TO_MINUS_ONE = "Constant to -1";
    protected static final String CONSTANT_TO_MINUS_CONSTANT = "Constant to -Constant";
    protected static final String CONSTANT_TO_CONSTANT_PLUS_ONE = "Constant to Constant + 1";
    protected static final String CONSTANT_TO_CONSTANT_MINUS_ONE = "Constant to Constant - 1";

    @Override
    public List<List<String>> getMutators() {
        return null;
    }

    @Override
    public List<String> getOperators() {
        return Arrays.asList(CONSTANT_TO_ONE,CONSTANT_TO_ZERO,CONSTANT_TO_MINUS_ONE,CONSTANT_TO_MINUS_CONSTANT,CONSTANT_TO_CONSTANT_PLUS_ONE,CONSTANT_TO_CONSTANT_MINUS_ONE);
    }

    @Override
    public List<DataKey> getDataKeys()  {
        List<DataKey> dataKeysList = new ArrayList<>();

        for(String operator : this.getOperators()) {
            identifiers.add(operator);
            dataKeysList.add(
                    KeyFactory.bool(operator).setLabel(operator)
            );
        }

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
            Boolean selectedMutators = (Boolean) dataStore.get(identifier);

            if(selectedMutators)
                switch (identifier){
                    case CONSTANT_TO_ZERO:
                        mutatorString
                                .append("\tnew ConstantMutator(KadabraNodes.literal(\"0\", \"int\")),\n");
                        break;

                    case CONSTANT_TO_ONE:
                        mutatorString
                                .append("\tnew ConstantMutator(KadabraNodes.literal(\"1\", \"int\")),\n");
                        break;

                    case CONSTANT_TO_MINUS_ONE:
                        mutatorString
                                .append("\tnew ConstantMutator(KadabraNodes.literal(\"-1\", \"int\")),\n");
                        break;

                    case CONSTANT_TO_MINUS_CONSTANT:
                        mutatorString
                                .append("\tnew ConstantMutator(\n" +
                                        "\t\tfunction($expr) {\n" +
                                        "\t\t\t\tvar newValue = Number($expr); \n" +
                                        "\t\t\t\t//newValue = newValue.substring(0, newValue.length-1);\n" +
                                        "\t\t\t\tnewValue = newValue * (-1); \n" +
                                        "\t\t\t\treturn KadabraNodes.literal(newValue.toString(),\"int\");\n" +
                                        "\t\t}\n" +
                                        "\t),\n");
                        break;

                    case CONSTANT_TO_CONSTANT_PLUS_ONE  :
                        mutatorString
                                .append("\tnew ConstantMutator(\n" +
                                        "\t\tfunction($expr) {\n" +
                                        "\t\t\t\tvar newValue = Number($expr); \n" +
                                        "\t\t\t\t//newValue = newValue.substring(0, newValue.length-1);\n" +
                                        "\t\t\t\tnewValue = newValue + 1; \n" +
                                        "\t\t\t\treturn KadabraNodes.literal(newValue.toString(),\"int\");\n" +
                                        "\t\t}\n" +
                                        "\t),\n");
                        break;

                    case CONSTANT_TO_CONSTANT_MINUS_ONE :
                        mutatorString
                                .append("\tnew ConstantMutator(\n" +
                                        "\t\tfunction($expr) {\n" +
                                        "\t\t\t\tvar newValue = Number($expr); \n" +
                                        "\t\t\t\t//newValue = newValue.substring(0, newValue.length-1);\n" +
                                        "\t\t\t\tnewValue = newValue - 1; \n" +
                                        "\t\t\t\treturn KadabraNodes.literal(newValue.toString(),\"int\");\n" +
                                        "\t\t}\n" +
                                        "\t),\n");
                        break;

                }


        }

        return mutatorString.toString();
    }


}