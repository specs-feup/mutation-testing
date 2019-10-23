package app;

import org.suikasoft.jOptions.Interfaces.DataStore;
import org.suikasoft.jOptions.app.AppKernel;

import java.util.List;

public class MutatorKernel implements AppKernel {

    public int execute(DataStore dataStore) {

        List<String> stringList = dataStore.get(Tese_UI.MINOR_OP);
        System.out.println(stringList.isEmpty());
        /*for(DataKey dataKey : main.dataKeys)
            System.out.println("Value: " + dataStore.get(dataKey));*/
        return 0;
    }
}
