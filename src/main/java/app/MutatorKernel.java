package app;

import app.operators.Operators;
import org.json.JSONException;
import org.json.JSONObject;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;
import weaver.gui.KadabraLauncher;
import weaver.kadabra.concurrent.KadabraThread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {
        System.out.println("CONFIG:" + dataStore);

        JSONObject jsonObject = new JSONObject();

        String projectPath = dataStore.get(Tese_UI.PROJECT_FILE).getAbsolutePath();
        String laraPath = dataStore.get(Tese_UI.LARA_FILE).getAbsolutePath();
        String outputPath = dataStore.get(Tese_UI.OUTPUT_FILE).getAbsolutePath() + File.separator +"Output";

        List<String> arguments = new ArrayList<>(Arrays.asList(laraPath, "-p", projectPath, "-X"));

        try {
            for (Operators operators : Operators.assignedOperators) {
                JSONObject operator = new JSONObject();
                for (String identifier : operators.getIdentifiers()){
                    operator.put(identifier.substring(identifier.indexOf(' ')+1), dataStore.get(identifier));
                }
                jsonObject.put(operators.getType(),operator);
            }
            File file = new File("operators.json");
            try (FileWriter fw = new FileWriter(file)) {
                fw.write(jsonObject.toString(2));
                arguments.add("-av");
                arguments.add(new JSONObject().put("jsonFile", file.getAbsolutePath()).toString());
            }

        }catch (JSONException | IOException E){
            E.printStackTrace();
            return -1;
        }


        System.out.println("Project path: " + projectPath);
        System.out.println("ARGS:\n" + arguments.stream().collect(Collectors.joining(" ")));

        KadabraLauncher.main(arguments.toArray(String[]::new));

        return 0;
    }
}
