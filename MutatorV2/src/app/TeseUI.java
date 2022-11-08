package app;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.storedefinition.StoreDefinition;

public class TeseUI {
    static {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));
    }
    
    public static StoreDefinition storeDefinition;
    public static final DataKey<File> PROJECT_PATH = KeyFactory.folder("ProjectPath").setLabel("Project path");
    public static final DataKey<File> OUTPUT_FOLDER = KeyFactory.folder("OutputFolder").setLabel("Output Path (Empty for default)");
    public static final DataKey<File> JAVASCRIPT_FILE = KeyFactory.file("JavascriptPath", "js").setLabel("Javascript file path (for unimplemented mutators)");
    public static final DataKey<Integer> NUMBER_OF_THREADS = KeyFactory.integer("NUMBER_OF_THREADS").setLabel("Number of threads to run").setDefault(() -> 20);
    public static final DataKey<Boolean> TRADITIONAL_MUTATION = KeyFactory.bool("TRADITIONAL_MUTATION").setLabel("Use traditional mutation");
}
