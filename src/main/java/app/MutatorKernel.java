package app;

import app.operators.Operators;
import org.json.JSONObject;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.utilities.Replacer;
import weaver.gui.KadabraLauncher;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {
        System.out.println("CONFIG:" + dataStore);


        File projectPath = dataStore.get(Tese_UI.PROJECT_FILE);
        String laraPath = dataStore.get(Tese_UI.LARA_FILE).isFile() ? dataStore.get(Tese_UI.LARA_FILE).getAbsolutePath() : "src/Lara_Files/Main.lara";
        String outputPath = dataStore.get(Tese_UI.OUTPUT_FILE).getAbsolutePath() + File.separator +"Output";


        List<File> filesList = getFiles(projectPath, new ArrayList<>());

        List<String []> listArguments = new ArrayList<>();

        if(filesList.isEmpty())
            filesList.add(projectPath);

        for(File folder : filesList) {
            JSONObject laraArguments = new JSONObject();

            List<String> arguments = new ArrayList<>(Arrays.asList(laraPath,  "-p", folder.getAbsolutePath(), "-o", outputPath + "MainOutputs" + File.separator + folder.getName()));

            laraArguments.put("outputPath", outputPath);
            laraArguments.put("packageName", folder.getAbsolutePath().replace(projectPath.getAbsolutePath(), "").replace(File.separatorChar, '.'));

            String templatePath = "src/Lara_Files/template.lara";
            String mutatorsPath = "src/Lara_Files/Mutators.lara";

            Replacer replacer = null;

            try {
                replacer = new Replacer(new String(Files.readAllBytes(Paths.get(templatePath))));
            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }

            replacer.replace("<IMPORT>", "");//Operators.getImportString());
            replacer.replace("<MUTATORS>", Operators.generateMutatorString(dataStore));

            SpecsIo.write(new File(mutatorsPath), replacer.toString());

            arguments.add("-av");
            arguments.add(laraArguments.toString());

            arguments.add("-X");
            arguments.add("-b");
            arguments.add("2");
            arguments.add("-s");
            arguments.add("-Q");
            arguments.add("-d");

            listArguments.add(arguments.toArray(String[]::new));
        }

        executeParallel(listArguments.toArray(String[][]::new), 8);

        return 0;
    }

    public static boolean executeParallel(String [][] args, int threads) {

        var customThreadPool = threads > 0 ? new ForkJoinPool(threads) : new ForkJoinPool();

        System.out.println("Launching " + args.length + " instances of Kadabra in parallel, using " + threads + " threads");

        try {
            var results = customThreadPool.submit(() -> Arrays.asList(args).parallelStream()
                    .map(MutatorKernel::executeSafe)
                    .collect(Collectors.toList())).get();

            return results.stream()
                    .filter(result -> result == false)
                    .findFirst()
                    .orElse(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return false;
        } catch (ExecutionException e) {
            System.err.println("Unrecoverable exception while executing parallel instances of Clava: " + e);
            e.printStackTrace();
            return false;
        }

    }


    private static boolean executeSafe(String[] args) {
        try {
            return KadabraLauncher.execute(args);
        } catch (Exception e) {
            System.err.println("Exception during Kadabra execution: " + e);
            return false;
        }
    }

    public static List<File> getFiles(File directory, List<File> list){
        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile())
                    list.add(file);
                else
                    if(file.isDirectory() && !file.getName().matches("test*.{0,}")) //Regex to ignore the folders with name starting as test
                        list = getFiles(file, list);

            }
        return list;
    }
}
