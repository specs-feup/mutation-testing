package app;

import app.operators.Operators;
import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.JOptionsUtils;
import org.suikasoft.jOptions.app.App;
import org.suikasoft.jOptions.app.AppPersistence;
import org.suikasoft.jOptions.persistence.XmlPersistence;
import org.suikasoft.jOptions.storedefinition.StoreDefinition;
import org.suikasoft.jOptions.storedefinition.StoreDefinitionBuilder;
import pt.up.fe.specs.util.SpecsSystem;
import pt.up.fe.specs.util.properties.SpecsProperty;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;


public class Tese_UI {
    static{
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
        System.setProperty("current.date.time", dateFormat.format(new Date()));
    }
    public static StoreDefinition storeDefinition;



    public static final DataKey<File> PROJECT_FILE = KeyFactory.folder("ProjectPath").setLabel("Project path");
    public static final DataKey<File> PROJECT_CLASS_FILE = KeyFactory.folder("ClassPath").setLabel("Compiled Class files path");
    public static final DataKey<File> OUTPUT_FILE = KeyFactory.folder("OutputFile").setLabel("Output Path (Empty for default)");
    public static final DataKey<File> LARA_FILE = KeyFactory.file("LaraPath", "lara").setLabel("Lara file path (for unimplemented mutators)");
    public static final DataKey<Integer> NUMBER_OF_THREADS  = KeyFactory.integer("NUMBER_OF_THREADS").setLabel("Number of threads to run").setDefault(() -> 20);
//    public static String defaultPath = KeyFactory.folder("").getDefault().orElse(new File("")).getAbsolutePath(); //For default paths

    public static void main(String[] args) {

        SpecsSystem.programStandardInit();

        StoreDefinitionBuilder storeDefinitionBuilder = new StoreDefinitionBuilder(Tese_UI.class);

        for(Operators operators: Operators.assignedOperators) {
            storeDefinitionBuilder.startSection(operators.getDescription() + " Operators");
            operators.getDataKeys().forEach(storeDefinitionBuilder::addKey);
        }

        //SpecsProperty.ShowMemoryHeap.applyProperty("true");
        storeDefinition = storeDefinitionBuilder.build();
        AppPersistence persistence = new XmlPersistence(storeDefinition);

        App app = App.newInstance(storeDefinition, persistence, new MutatorKernel());
        JOptionsUtils.executeApp(app, Arrays.asList(args));
    }

}
