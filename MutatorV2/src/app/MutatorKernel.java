package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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
    private static int threadCount = 0;
    private static Boolean isInterrupted = false;
    
    private final String MUTATORS_CODE_LOCATION = "javascript_src"+ File.separator;
    private final String ENTRY_FILE_LOCATION = MUTATORS_CODE_LOCATION + "Main.js";
    private final String CHOSEN_MUTATOR_FILE_LOCATION = MUTATORS_CODE_LOCATION + "Mutators.js";
	
    @Override
    public int execute(DataStore dataStore) {
        //LOGGER.debug("CONFIG:" + dataStore);

        //Gets all the files from project location
        File projectPath = dataStore.get(TeseUI.PROJECT_PATH);
        List<File> filesList = SpecsIo.getFilesRecursive(projectPath, "java");
        
        if (filesList.isEmpty()) {
        	//LOGGER.error("No Java files detected");
        	System.out.print("No Java files detected");
            return 1;
        }

        //Javascript code path
        String javascriptPath = dataStore.get(TeseUI.JAVASCRIPT_FILE).isFile() ? dataStore.get(TeseUI.JAVASCRIPT_FILE).getAbsolutePath() : ENTRY_FILE_LOCATION;

        //output path
        File OutputPath = dataStore.get(TeseUI.OUTPUT_FOLDER);
        if (!OutputPath.isDirectory()) {
        	//LOGGER.error("No output file selected");
        	System.out.print("No output file selected");
            return 1;
        }

        List<DataStore> listArguments = new ArrayList<>();

        //For each java file detected
        for (File file : filesList) {        	
            DataStore data = DataStore.newInstance("Kadabra Options");

            data.put(LaraiKeys.LARA_FILE, new File(javascriptPath));
            data.put(LaraiKeys.OUTPUT_FOLDER, OutputPath);
            data.put(LaraiKeys.WORKSPACE_FOLDER, FileList.newInstance(file));
            data.put(LaraiKeys.VERBOSE, VerboseLevel.warnings);
            data.set(LaraiKeys.TRACE_MODE);
            data.set(JavaWeaverKeys.FULLY_QUALIFIED_NAMES);
            data.set(LaraiKeys.DEBUG_MODE);
            data.set(JavaWeaverKeys.NO_CLASSPATH);
            data.set(JavaWeaverKeys.WRITE_CODE, false);
            data.set(LaraiKeys.EXTERNAL_DEPENDENCIES,StringList.newInstance("https://github.com/specs-feup/lara-framework.git?folder=experimental/SourceAction","https://github.com/specs-feup/lara-framework.git?folder=experimental/Mutation"));
            data.put(LaraiKeys.INCLUDES_FOLDER, FileList.newInstance(new File(MUTATORS_CODE_LOCATION)));
            
            JSONObject javascriptArguments = new JSONObject();
            javascriptArguments.put("outputPath", OutputPath.getAbsolutePath());
            javascriptArguments.put("filePath", file.getAbsolutePath());
            javascriptArguments.put("outputFolder" , OutputPath.getAbsolutePath() + File.separator + "mutatedFiles");
            javascriptArguments.put("traditionalMutation", dataStore.get(TeseUI.TRADITIONAL_MUTATION));
            javascriptArguments.put("projectPath", dataStore.get(TeseUI.PROJECT_PATH).getAbsolutePath()+ File.separator + " ");
            javascriptArguments.put("debugMessages", dataStore.get(TeseUI.DEBUG));

            
            data.put(LaraiKeys.ASPECT_ARGS, javascriptArguments.toJSONString());
         
            
            var replacer = new Replacer(SpecsIo.getResource("template.js"));
            replacer.replace("<IMPORT>", "");
            replacer.replace("<MUTATORS>", Operators.generateMutatorString(dataStore));
            SpecsIo.write(new File(CHOSEN_MUTATOR_FILE_LOCATION), replacer.toString());

            listArguments.add(data);
        }
        
        
        threadCount = 0;
        
        if (dataStore.get(TeseUI.NUMBER_OF_THREADS) < filesList.size()) {
        	executeParallel(listArguments, dataStore.get(TeseUI.NUMBER_OF_THREADS));
        }else {
        	executeParallel(listArguments, filesList.size());
        }
    	
    	return 0;
    }
    
    public static boolean executeParallel(List<DataStore> args,  int threads) {
        var customThreadPool = threads > 0 ? new ForkJoinPool(threads) : new ForkJoinPool();

        //LOGGER.info("Launching " + args.size() + " instances of Kadabra in parallel, using " + threads + " threads");
        //System.out.println("Launching " + args.size() + " instances of Kadabra in parallel, using " + threads + " threads");
        
        try {
            var results = customThreadPool.submit(() -> args.parallelStream()
                    .map(MutatorKernel::executeSafe)
                    .collect(Collectors.toList())).get();

            return results.stream()
                    .filter(result -> result == false)
                    .findFirst()
                    .orElse(true);
        } catch (InterruptedException e) {
            //LOGGER.error(e);
        	System.out.print(e);
        	
            isInterrupted = true;
            Thread.currentThread().interrupt();
            return false;
        } catch (ExecutionException e) {
            //LOGGER.error("Unrecoverable exception while executing parallel instances of Kadabra: " + e);
        	System.out.print(e);
            return false;
        }

    }
    
    private static boolean executeSafe(DataStore args) {
        if (!isInterrupted)
            try {
                //LOGGER.info("New Thead. Thread count -> " + ++threadCount);
                //LOGGER.info(" ARGS: " + args);
            	
            	System.out.print("[DEBUG] New Thead. Thread count -> " + ++threadCount);
                System.out.println("[DEBUG] ARGS: " + args);
                return KadabraLauncher.execute(args);
            } catch (Exception e) {
                //LOGGER.error("Exception during Kadabra execution: " + e);
            	System.out.print("Exception during Kadabra execution: " + e);
                return false;
            }
        return true;
    }
    
   
}
