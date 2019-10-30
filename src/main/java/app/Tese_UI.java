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

    public static final DataKey<File> PROJECT_PATH = KeyFactory.folder("Project Path");
    public static final DataKey<File> OUTPUT_PATH = KeyFactory.folder("Output Path (Empty for default)");

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
