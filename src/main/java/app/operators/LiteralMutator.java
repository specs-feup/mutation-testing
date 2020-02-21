package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public final class LiteralMutator extends Operators{

    /***
     * Unary Operators
     */

    private static List<String> identifiers = new ArrayList<>();
    private static final String DESCRIPTION = "Literal Mutator";
    private static final String MUTATOR_TYPE = "LiteralMutator";

    protected static final String ZERO_TO_ONE = " 0 to 1";
    protected static final String ONE_TO_MINUS_ONE = "1 to -1";
    protected static final String TRUE_TO_FALSE = "True to False";
    protected static final String FALSE_TO_TRUE = " False to True";
    protected static final String STRING_TO_EMPTY = " one String to \"\"";
    protected static final String ONE_BYTE_SHORT_TO_ZERO = "int byte short: 1 to 0 ; ";
    protected static final String MINUS_ONE_BYTE_SHORT_TO_ONE = "int byte short -1 to 1 ; ";
    protected static final String BYTE_SHORT_TO_MINUS_ONE = "int byte short 5 to -1";
    protected static final String BYTE_SHORT_INCREMENT_ONE = "int byte short otherwise increments by 1";
    protected static final String LONG_ONE_TO_ZERO = "long: 1 to 0, ";
    protected static final String LONG_INCREMENT_BY_ONE = "long: otherwise increments by 1";
    protected static final String FLOAT_ONE_TO_ZERO = "float: 1.0 to 0, ";
    protected static final String FLOAT_TWO_TO_ZERO = "float 2.0 to 0, ";
    protected static final String FLOAT_OTHER_NUMBER_TO_ZERO = "float other number to 1.0";
    protected static final String DOUBLE_ONE_TO_ZERO = "double: 1.0 to 0, ";
    protected static final String DOUBLE_NUMBER_TO_ONE= "double another number to 1.0";

    @Override
    public List<List<String>> getMutators() {
        return null;
    }

    @Override
    public List<String> getOperators() {
        return Arrays.asList(
        ZERO_TO_ONE,
        ONE_TO_MINUS_ONE,
        TRUE_TO_FALSE,
        FALSE_TO_TRUE,
        STRING_TO_EMPTY,
        ONE_BYTE_SHORT_TO_ZERO,
        MINUS_ONE_BYTE_SHORT_TO_ONE,
        BYTE_SHORT_TO_MINUS_ONE,
        BYTE_SHORT_INCREMENT_ONE,
        LONG_ONE_TO_ZERO,
        LONG_INCREMENT_BY_ONE,
        FLOAT_ONE_TO_ZERO,
        FLOAT_TWO_TO_ZERO,
        FLOAT_OTHER_NUMBER_TO_ZERO,
        DOUBLE_ONE_TO_ZERO,
        DOUBLE_NUMBER_TO_ONE);
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
                    case ZERO_TO_ONE:
                        mutatorString.append("\t").append("new LiteralMutator(KadabraNodes.literal(\"1\", \"int\"), KadabraNodes.literal(\"0\", \"int\")),").append("\n");
                        break;
                    case ONE_TO_MINUS_ONE:
                        mutatorString.append("\t").append("new LiteralMutator(KadabraNodes.literal(\"-1\", \"int\"), KadabraNodes.literal(\"1\", \"int\")),").append("\n");
                        break;
                    case TRUE_TO_FALSE:
                        mutatorString.append("\t").append("new LiteralMutator(KadabraNodes.literal(\"true\", \"boolean\"), KadabraNodes.literal(\"false\", \"boolean\")),").append("\n");
                        break;
                    case FALSE_TO_TRUE:
                        mutatorString.append("\t").append("new LiteralMutator(KadabraNodes.literal(\"false\", \"boolean\"), KadabraNodes.literal(\"true\", \"boolean\")),").append("\n");
                        break;
                    case STRING_TO_EMPTY:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\" \", \"String\"), ['String']),").append("\n");
                        break;
                    case ONE_BYTE_SHORT_TO_ZERO:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"0\", \"int\"), ['int', 'byte', 'short'], \"1\"),").append("\n");
                        break;
                    case MINUS_ONE_BYTE_SHORT_TO_ONE:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"1\", \"int\"), ['int', 'byte', 'short'], \"-1\"),").append("\n");
                        break;
                    case BYTE_SHORT_TO_MINUS_ONE:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"-1\", \"int\"), ['int', 'byte', 'short'], \"5\"),").append("\n");
                        break;
                    case BYTE_SHORT_INCREMENT_ONE:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(\n" +
                                "\t\tfunction($expr) {\n" +
                                "\t\t\tvar newValue = Number($expr.code) + 1; \n" +
                                "\t\t\treturn KadabraNodes.literal(newValue.toString(),$expr.type);\n" +
                                "\t\t}\n" +
                                "\t\t, ['int', 'byte', 'short'], \"1\"),").append("\n");
                        break;
                    case LONG_ONE_TO_ZERO:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"0\", \"int\"), ['long'], \"1\"),").append("\n");
                        break;
                    case LONG_INCREMENT_BY_ONE:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(\n" +
                                "\t\tfunction($expr) {\n" +
                                "\t\t\tvar newValue = $expr.code; \n" +
                                "\t\t\tnewValue = newValue.substring(0, newValue.length-1);\n" +
                                "\t\t\tnewValue = Number(newValue) + 1; \n" +
                                "\t\t\treturn KadabraNodes.literal(newValue.toString(),$expr.type);\n" +
                                "\t\t}\n" +
                                "\t\t, ['long']),").append("\n"); //TODO:Check this last part if correct
                        break;
                    case FLOAT_ONE_TO_ZERO:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"0.0\", \"double\"), ['float'], \"1.0F\"),").append("\n");
                        break;
                    case FLOAT_TWO_TO_ZERO:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"0.0\", \"double\"), ['float'], \"2.0F\"),").append("\n");
                        break;
                    case FLOAT_OTHER_NUMBER_TO_ZERO:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"0.0\", \"double\"), ['float']),").append("\n");
                        break;
                    case DOUBLE_ONE_TO_ZERO:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"0.0\", \"double\"), ['double'], \"1.0\"),").append("\n");
                        break;
                    case DOUBLE_NUMBER_TO_ONE:
                        mutatorString.append("\t").append("LiteralMutator.newTypeFilteredMutator(KadabraNodes.literal(\"1.0\", \"double\"), ['double']),").append("\n");
                        break;
                }


        }

        return mutatorString.toString();
    }


}