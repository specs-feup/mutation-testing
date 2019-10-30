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

        JSONObject laraArguments = new JSONObject();
        String projectPath = dataStore.get(Tese_UI.PROJECT_FILE).getAbsolutePath();
        String laraPath = dataStore.get(Tese_UI.LARA_FILE).getAbsolutePath();
        String outputPath = dataStore.get(Tese_UI.OUTPUT_FILE).getAbsolutePath() + File.separator +"Output";

        List<String> arguments = new ArrayList<>(Arrays.asList(laraPath, "-p", projectPath, "-o", outputPath+"_Main"));

        try {
            laraArguments.put("outputPath", outputPath);

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

                laraArguments.put("jsonFile", file.getAbsolutePath());
            }

        }catch (JSONException | IOException E){
            E.printStackTrace();
            return -1;
        }





        arguments.add("-av");
        arguments.add(laraArguments.toString());

        arguments.add("-X");
        arguments.add("-b");
        arguments.add("2");
        arguments.add("-s");

        System.out.println("Project path: " + projectPath);
        System.out.println("ARGS:\n" + String.join(" ", arguments));

        KadabraLauncher.main(arguments.toArray(String[]::new));

        return 0;
    }
}
