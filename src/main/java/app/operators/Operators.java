package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Interfaces.DataStore;

import java.util.List;

public abstract class Operators {

    public static Operators[] assignedOperators = {new ArithmeticOperators(),new BitwiseOperators(),new ConditionalOperators(),new RelationalOperators(), new UnaryOperators()};

    public abstract List<List<String>> getMutators();
    public abstract List<String> getOperators();
    public abstract List<String> getIdentifiers();
    public abstract String getDescription();
    public abstract List<DataKey> getDataKeys();
    public abstract String getMutatorType();

    public static String getMutatorString(DataStore dataStore){
        StringBuilder mutatorString = new StringBuilder();

        for(Operators operators : assignedOperators)
            for (String identifier : operators.getIdentifiers()){
                List<String> selectedMutators = (List<String>) dataStore.get(identifier);
                for(String mutator : selectedMutators){
                    mutatorString.append("\tnew ");
                    mutatorString.append(operators.getMutatorType());
                    mutatorString.append("(\"");
                    mutatorString.append(mutator);
                    mutatorString.append("\",\"");
                    mutatorString.append(identifier.substring(identifier.indexOf(' ')+1));
                    mutatorString.append("\"),\n");
                }
            }
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
