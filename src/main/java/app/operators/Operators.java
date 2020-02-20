package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.List;

public abstract class Operators {

    public static Operators[] assignedOperators = {new ArithmeticOperatorDeletion(), new FailOnNull(), new RemoveNullCheck(), new NullifyInputVariable(), new NullifyObjectInitialization(), new NullifyReturnValue(),
            new ConditionalOperatorDeletionMutator(), new ConditionalOperatorInsertionMutator(), new ConstructorCallMutator(), new InheritanceIPCMutator(),
            new ArithmeticOperators(),new BitwiseOperators(),new ConditionalOperators(),new RelationalOperators(), new UnaryOperators()};

    public abstract List<List<String>> getMutators();
    public abstract List<String> getOperators();
    public abstract List<String> getIdentifiers();
    public abstract String getDescription();
    public abstract List<DataKey> getDataKeys();
    public abstract String getMutatorType();

    public static String getMutatorString(DataStore dataStore){
        StringBuilder mutatorString = new StringBuilder();

        for(Operators operators : assignedOperators)
            if(operators.getMutatorType().equals("UnaryMutator") || operators.getMutatorType().equals("BinaryMutator"))
                for (String identifier : operators.getIdentifiers()){
                    List<String> selectedMutators = (List<String>) dataStore.get(identifier);
                    for(String mutator : selectedMutators){
                        mutatorString
                                .append("\tnew ")
                                .append(operators.getMutatorType())
                                .append("(\"")
                                .append(mutator)
                                .append("\",\"")
                                .append(identifier.substring(identifier.indexOf(' ')+1))
                                .append("\"),\n");
                    }
                }
            else    if(operators instanceof ArithmeticOperatorDeletion
                    || operators instanceof FailOnNull
                    || operators instanceof RemoveNullCheck
                    || operators instanceof NullifyObjectInitialization
                    || operators instanceof NullifyReturnValue
                    || operators instanceof NullifyInputVariable
                    || operators instanceof ConditionalOperatorDeletionMutator
                    || operators instanceof ConditionalOperatorInsertionMutator
                    || operators instanceof ConstructorCallMutator
                    || operators instanceof InheritanceIPCMutator)
                for (String identifier : operators.getIdentifiers()){
                    Boolean selectedMutators = (Boolean) dataStore.get(identifier);

                    if(selectedMutators)
                        mutatorString
                            .append("\tnew ")
                            .append(operators.getMutatorType())
                            .append("(),\n");

                }

            if(mutatorString.length() == 0)
                return "";

            mutatorString.replace(mutatorString.lastIndexOf(","),mutatorString.length()," ");
        return mutatorString.toString();
    }

    public static String getImportString(){
        StringBuilder importString = new StringBuilder();

        for(Operators operators : assignedOperators){
            importString.append("import mutation.");
            importString.append(operators.getMutatorType());
            importString.append(";\n");
        }

        return importString.toString();
    }

}
