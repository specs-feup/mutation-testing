package app.operators;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Operators {
    public abstract List<List<String>> getMutators();
    public abstract List<String> getOperators();
}
