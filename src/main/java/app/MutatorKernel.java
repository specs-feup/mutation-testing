package app;

import app.operators.Operators;
import org.json.JSONException;
import org.json.JSONObject;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;
import weaver.gui.KadabraLauncher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {
        System.out.println("CONFIG:" + dataStore);

        JSONObject jsonObject = new JSONObject();

        String projectPath = dataStore.get(Tese_UI.PROJECT_PATH).getAbsolutePath();
        String outputPath = dataStore.get(Tese_UI.OUTPUT_PATH).getAbsolutePath() + File.separator +"Output";

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

        String[] args = {"C:\\Gits\\Tese\\kadaba_android_example\\BinaryAndUnaryOp.lara", "-o", outputPath, "-p", projectPath,  "-X"};
        System.out.println("Project path: " + projectPath);
        System.out.println("ARGS:\n" + Arrays.asList(args).stream().collect(Collectors.joining(" ")));

        KadabraLauncher.main(args);

        return 0;
    }
}
