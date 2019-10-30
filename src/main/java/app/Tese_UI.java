package app;

import app.operators.*;
import org.suikasoft.GsonPlus.JsonStringListPanel;
import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.KeyFactory;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.JOptionsUtils;
import org.suikasoft.jOptions.app.App;
import org.suikasoft.jOptions.app.AppPersistence;
import org.suikasoft.jOptions.gui.panels.option.BooleanPanel;
import org.suikasoft.jOptions.persistence.XmlPersistence;
import org.suikasoft.jOptions.storedefinition.StoreDefinition;
import org.suikasoft.jOptions.storedefinition.StoreDefinitionBuilder;
import pt.up.fe.specs.util.SpecsSystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;


public class Tese_UI {
    public static StoreDefinition storeDefinition;

    public static final DataKey<File> PROJECT_PATH = KeyFactory.folder("Project Path");//.setDefault((Supplier<? extends File>) new File("C:\\Gits\\Tese\\kadaba_android_example\\android-AppShortcuts\\app"));
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
