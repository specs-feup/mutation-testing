package app;

import app.operators.Operators;
import org.json.JSONException;
import org.json.JSONObject;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;

import java.io.FileWriter;
import java.io.IOException;

public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {
        JSONObject jsonObject = new JSONObject();

        try {
            for (Operators operators : Operators.assignedOperators) {
                JSONObject operator = new JSONObject();
                for (String identifier : operators.getIdentifiers()){
                    operator.put(identifier.substring(identifier.indexOf(' ')+1), dataStore.get(identifier));
                }
                jsonObject.put(operators.getType(),operator);
            }

            try (FileWriter file = new FileWriter("operators.json")) {
                file.write(jsonObject.toString(2));
            }

        }catch (JSONException | IOException E){
            E.printStackTrace();
            return -1;
        }


        return 0;
    }
}
