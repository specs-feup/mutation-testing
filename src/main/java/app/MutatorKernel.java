package app;

import org.suikasoft.jOptions.Datakey.DataKey;
import org.suikasoft.jOptions.Datakey.DataKeyExtraData;
import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;

import java.util.Collection;


public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {

        /*List<String> stringList = dataStore.get(Tese_UI.MINOR_OP);
        System.out.println(stringList.isEmpty());
        for(DataKey dataKey : main.dataKeys)
            System.out.println("Value: " + dataStore.get(dataKey));*/

        Collection<Object> list =  dataStore.getValues();//dataStore.getStoreDefinition().get().getSections().get(1).getKeys().get(0);
        return 0;
    }
}
