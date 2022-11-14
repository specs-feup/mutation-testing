package app;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.lara.interpreter.joptions.config.interpreter.LaraiKeys;
import org.lara.interpreter.joptions.config.interpreter.VerboseLevel;
import org.lara.interpreter.joptions.keys.FileList;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;

import app.operators.Operators;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.utilities.Replacer;
import pt.up.fe.specs.util.utilities.StringList;
import weaver.gui.KadabraLauncher;
import weaver.options.JavaWeaverKeys;

public class MutatorKernel implements AppKernel {
    public static final Logger LOGGER = LogManager.getLogger(MutatorKernel.class);

    private final String Entry_CODE_LOCATION = "javascript_src" + File.separator;
    private final String CHOSEN_MUTATOR_FILE_LOCATION = "javascript_src_2" + File.separator + "Mutators.js";

    @Override
    public int execute(DataStore dataStore) {
        DataStore data = createKadabraOptions(dataStore);

        executeKadabra(data);

        return 0;
    }

    private DataStore createKadabraOptions(DataStore dataStore) {
        DataStore data = DataStore.newInstance("Kadabra Options");

        data.put(LaraiKeys.LARA_FILE, new File(dataStore.get(TeseUI.JAVASCRIPT_FILE).getAbsolutePath()));
        data.put(LaraiKeys.OUTPUT_FOLDER, dataStore.get(TeseUI.OUTPUT_FOLDER));
        data.put(LaraiKeys.VERBOSE, VerboseLevel.warnings);
        data.set(LaraiKeys.TRACE_MODE);
        data.set(JavaWeaverKeys.FULLY_QUALIFIED_NAMES);
        data.set(LaraiKeys.DEBUG_MODE);
        data.set(JavaWeaverKeys.NO_CLASSPATH);
        data.set(JavaWeaverKeys.WRITE_CODE, false);
        data.set(LaraiKeys.EXTERNAL_DEPENDENCIES,
                StringList.newInstance(
                        "https://github.com/specs-feup/lara-framework.git?folder=experimental/SourceAction",
                        "https://github.com/specs-feup/lara-framework.git?folder=experimental/Mutation"));
        data.put(LaraiKeys.INCLUDES_FOLDER, FileList.newInstance(new File(Entry_CODE_LOCATION)));

        JSONObject javascriptArguments = new JSONObject();
        javascriptArguments.put("outputPath", dataStore.get(TeseUI.OUTPUT_FOLDER).getAbsolutePath());
        javascriptArguments.put("traditionalMutation", dataStore.get(TeseUI.TRADITIONAL_MUTATION));
        javascriptArguments.put("projectPath",
                dataStore.get(TeseUI.PROJECT_PATH).getAbsolutePath() + File.separator);
        javascriptArguments.put("debugMessages", dataStore.get(TeseUI.DEBUG));

        data.put(LaraiKeys.ASPECT_ARGS, javascriptArguments.toJSONString());

        var replacer = new Replacer(SpecsIo.getResource("template.js"));
        replacer.replace("<IMPORT>", "");
        replacer.replace("<MUTATORS>", Operators.generateMutatorString(dataStore));
        SpecsIo.write(new File(CHOSEN_MUTATOR_FILE_LOCATION), replacer.toString());

        return data;
    }

    private boolean executeKadabra(DataStore data) {
        try {
            // LOGGER.info("New Thead. Thread count -> " + ++threadCount);
            // LOGGER.info(" ARGS: " + args);

            System.out.print("[DEBUG] Executing Kadabra");
            System.out.println("[DEBUG] ARGS: " + data);
            return KadabraLauncher.execute(data);
        } catch (Exception e) {
            // LOGGER.error("Exception during Kadabra execution: " + e);
            System.out.print("Exception during Kadabra execution: " + e);
            e.printStackTrace();
            return false;
        }
    }

}
