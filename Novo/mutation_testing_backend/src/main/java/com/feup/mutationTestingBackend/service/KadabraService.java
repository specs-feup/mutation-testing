package com.feup.mutationTestingBackend.service;

import com.feup.mutationTestingBackend.wrapper.KadabraWrapper;
import org.json.simple.JSONObject;
import org.lara.interpreter.joptions.config.interpreter.LaraiKeys;
import org.lara.interpreter.joptions.config.interpreter.VerboseLevel;
import org.lara.interpreter.joptions.keys.FileList;
import org.springframework.stereotype.Service;
import org.suikasoft.jOptions.Interfaces.DataStore;
import pt.up.fe.specs.util.utilities.StringList;
import weaver.gui.KadabraLauncher;
import weaver.options.JavaWeaverKeys;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class KadabraService {

    public boolean executeKadabra(KadabraWrapper kw) {
        System.out.println(kw);
        try {
            DataStore data = DataStore.newInstance("Kadabra Options");

            data.put(LaraiKeys.LARA_FILE, new File(kw.getFilePath()));
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
            data.put(LaraiKeys.INCLUDES_FOLDER, FileList.newInstance(new File("C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src\\")));

            JSONObject javascriptArguments = new JSONObject();
            javascriptArguments.put("outputPath", kw.getOutputPath());
            javascriptArguments.put("traditionalMutation", kw.isTraditionalMutation());
            javascriptArguments.put("folderToIgnore", "C:\\Users\\david\\Desktop\\TestProject\\src\\test");
            javascriptArguments.put("projectPath", kw.getProjectPath());
            javascriptArguments.put("debugMessages", kw.isDebugMessages());

            data.put(LaraiKeys.ASPECT_ARGS, javascriptArguments.toJSONString());

            /*var replacer = new Replacer(SpecsIo.getResource("template.js"));
            replacer.replace("<IMPORT>", "");
            replacer.replace("<MUTATORS>", "new BinaryMutator(\"+\",\"*\"),\n" +
                    "\tnew BinaryMutator(\"+\",\"-\"),\n" +
                    "\tnew BinaryMutator(\"+\",\"/\"),\n" +
                    "\tnew BinaryMutator(\"+\",\"%\"),\n" +
                    "\tnew BinaryMutator(\"-\",\"+\"),\n" +
                    "\tnew BinaryMutator(\"-\",\"/\"),\n" +
                    "\tnew BinaryMutator(\"-\",\"*\"),\n" +
                    "\tnew BinaryMutator(\"-\",\"%\") ");
            SpecsIo.write(new File("javascript_src_2\\Mutators.js"), replacer.toString());*/

            return KadabraLauncher.execute(data);
            //return true;
        } catch (Exception e) {
            // LOGGER.error("Exception during Kadabra execution: " + e);
            System.out.print("Exception during Kadabra execution: " + e);
            e.printStackTrace();
            return false;
        }
    }

    public KadabraWrapper getKadabraWrapperExample() {
        KadabraWrapper kw = new KadabraWrapper("", "", true, "", false);

        return kw;
    }

    public ArrayList<String> getFileList(String path) {
        ArrayList<File> resultList = auxGetFileList(path);
        ArrayList<String> finalList = new ArrayList<>();

        for (File file : resultList) {
            if (!file.getAbsolutePath().contains("\\.idea") && !file.getAbsolutePath().contains("\\target")) {
                finalList.add(file.getAbsolutePath().substring(path.length(), file.getAbsolutePath().length()));
            }
        }

        Collections.sort(finalList);

        return finalList;
    }


    public static ArrayList<File> auxGetFileList(String directoryName) {
        File directory = new File(directoryName);

        ArrayList<File> resultList = new ArrayList<File>();

        // get all the files from a directory
        File[] fList = directory.listFiles();
        resultList.addAll(Arrays.asList(fList));
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                resultList.addAll(auxGetFileList(file.getAbsolutePath()));
            }
        }

        return resultList;
    }


    public Map<String, List<String>> listTestClassesAndCases(String pathToProject) {
        Map<String, List<String>> testClassesAndCases = new HashMap<>();
        try {
            Files.walk(Paths.get(pathToProject)).forEach(filePath -> {
                if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".java")) {
                    System.out.println(filePath);
                    String fileContent = null;
                    try {
                        fileContent = Files.readString(filePath, StandardCharsets.UTF_8);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String testClassName = extractTestClassName(fileContent);
                    System.out.println(testClassName);
                    List<String> testCaseNames = extractTestCaseNames(fileContent);
                    System.out.println(testCaseNames);
                    testClassesAndCases.put(testClassName, testCaseNames);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return testClassesAndCases;
    }

    private static String extractTestClassName(String fileContent) {
        String testClassName = null;
        Pattern pattern = Pattern.compile("public class (\\w+)");
        Matcher matcher = pattern.matcher(fileContent);
        if (matcher.find()) {
            testClassName = matcher.group(1);
        }
        return testClassName;
    }

    private static List<String> extractTestCaseNames(String fileContent) {
        List<String> testCaseNames = new ArrayList<>();
        Pattern pattern = Pattern.compile("@Test\\s+public void (\\w+)\\(");
        Matcher matcher = pattern.matcher(fileContent);
        while (matcher.find()) {
            testCaseNames.add(matcher.group(1));
        }
        return testCaseNames;
    }
}
