package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.List;

public abstract class Operators {

    public static Operators[] assignedOperators = {new ArithmeticOperatorDeletion(), new FailOnNull(), new RemoveNullCheck(), new NullifyInputVariable(), new NullifyObjectInitialization(), new NullifyReturnValue(),
            new ConditionalOperatorDeletionMutator(), new ConditionalOperatorInsertionMutator(), new ConstructorCallMutator(), new InheritanceIPCMutator(), new NonVoidCallMutator(), new RemoveConditionalMutator(),
            new ReturnValueMutator(),  new ConstantMutator(), new ConstantDeletionMutator(), new LiteralMutator(), new ArithmeticOperators(),new BitwiseOperators(),new ConditionalOperators(),new RelationalOperators(), new UnaryOperators()};

    public abstract List<List<String>> getMutators();
    public abstract List<String> getOperators();
    public abstract List<String> getIdentifiers();
    public abstract String getDescription();
    public abstract List<DataKey> getDataKeys();
    public abstract String getMutatorType();
    public abstract String getMutatorString(DataStore dataStore);


    public static String generateMutatorString(DataStore dataStore){
        StringBuilder mutatorString = new StringBuilder();

        for(Operators operators : assignedOperators)
            mutatorString.append(operators.getMutatorString(dataStore));

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
