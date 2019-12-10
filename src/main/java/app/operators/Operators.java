package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;

import java.util.List;

public abstract class Operators {

    public static Operators[] assignedOperators = {new ArithmeticOperators(),new BitwiseOperators(),new ConditionalOperators(),new RelationalOperators(), new UnaryOperators()};

    public abstract List<List<String>> getMutators();
    public abstract List<String> getOperators();
    public abstract List<String> getIdentifiers();
    public abstract String getType();
    public abstract List<DataKey> getDataKeys();
}
