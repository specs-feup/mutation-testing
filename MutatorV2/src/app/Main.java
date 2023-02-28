package app;

import java.util.Arrays;

import org.suikasoft.jOptions.JOptionsUtils;
import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.app.App;
import org.suikasoft.jOptions.app.AppPersistence;
import org.suikasoft.jOptions.persistence.XmlPersistence;
import org.suikasoft.jOptions.storedefinition.StoreDefinition;
import org.suikasoft.jOptions.storedefinition.StoreDefinitionBuilder;

import app.operators.Operators;
import pt.up.fe.specs.util.SpecsSystem;

public class Main {

	public static void main(String[] args) {
		SpecsSystem.programStandardInit();

		StoreDefinitionBuilder storeDefinitionBuilder = new StoreDefinitionBuilder(TeseUI.class);

		for (Operators operators : Operators.assignedOperators) {
			storeDefinitionBuilder.startSection(operators.getDescription() + " Operators");

			for (DataKey dk : operators.getDataKeys()) {
				storeDefinitionBuilder.addKey(dk);
			}
		}

		TeseUI.storeDefinition = storeDefinitionBuilder.build();
		AppPersistence persistence = new XmlPersistence(TeseUI.storeDefinition);

		App app = App.newInstance(TeseUI.storeDefinition, persistence, new MutatorKernel());
		JOptionsUtils.executeApp(app, Arrays.asList(args));
	}
}
