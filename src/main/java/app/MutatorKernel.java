package app;

import app.operators.Operators;
import org.json.JSONException;
import org.json.JSONObject;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.utilities.Replacer;
import weaver.gui.KadabraLauncher;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {
        System.out.println("CONFIG:" + dataStore);

        JSONObject jsonObject = new JSONObject();

        JSONObject laraArguments = new JSONObject();
        String projectPath = dataStore.get(Tese_UI.PROJECT_FILE).getAbsolutePath();
        String laraPath = dataStore.get(Tese_UI.LARA_FILE).getAbsolutePath();
        String outputPath = dataStore.get(Tese_UI.OUTPUT_FILE).getAbsolutePath() + File.separator +"Output";

        List<String> arguments = new ArrayList<>(Arrays.asList(laraPath, "-p", projectPath, "-o", outputPath+"_Main"));

        laraArguments.put("outputPath", outputPath);

        String templatePath = "src/Lara_Files/template.lara";
        String mutatorsPath =  "src/Lara_Files/Mutators.lara";

        Replacer replacer = null;

        try {
            replacer = new Replacer(new String(Files.readAllBytes(Paths.get(templatePath))));
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        replacer.replace("<IMPORT>", Operators.getImportString());
        replacer.replace("<MUTATORS>", Operators.getMutatorString(dataStore));

        SpecsIo.write(new File(mutatorsPath), replacer.toString());

        arguments.add("-av");
        arguments.add(laraArguments.toString());

        arguments.add("-X");
        arguments.add("-b");
        arguments.add("2");
        arguments.add("-s");

        System.out.println("Project path: " + projectPath);
        System.out.println("ARGS:\n" + String.join(" ", arguments));

        KadabraLauncher.execute(arguments.toArray(String[]::new));

        return 0;
    }
}
