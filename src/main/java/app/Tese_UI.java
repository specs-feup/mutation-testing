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

import java.io.File;
import java.util.Arrays;


public class Tese_UI {
    public static StoreDefinition storeDefinition;



    public static final DataKey<File> PROJECT_FILE = KeyFactory.folder("ProjectPath").setLabel("Project path");
    public static final DataKey<File> OUTPUT_FILE = KeyFactory.folder("OutputFile").setLabel("Output Path (Empty for default)");
    public static final DataKey<File> LARA_FILE = KeyFactory.file("LaraPath", "lara").setLabel("Lara file path (for unimplemented mutators)");

    public static String defaultPath = KeyFactory.folder("").getDefault().get().getAbsolutePath(); //For default paths

    public static void main(String[] args) {

        SpecsSystem.programStandardInit();

        StoreDefinitionBuilder storeDefinitionBuilder = new StoreDefinitionBuilder(Tese_UI.class);

        for(Operators operators: Operators.assignedOperators) {
            storeDefinitionBuilder.startSection(operators.getType() + " Operators");
            operators.getDataKeys().forEach(storeDefinitionBuilder::addKey);
        }

        storeDefinition = storeDefinitionBuilder.build();
        AppPersistence persistence = new XmlPersistence(storeDefinition);

        App app = App.newInstance(storeDefinition, persistence, new MutatorKernel());
        JOptionsUtils.executeApp(app, Arrays.asList(args));
    }

}
