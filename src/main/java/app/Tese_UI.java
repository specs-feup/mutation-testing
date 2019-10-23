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
import java.util.stream.Collectors;


public class Tese_UI {
    public static StoreDefinition storeDefinition;
//    public static final DataKey<Integer> AN_INT = KeyFactory.integer("anInt");
//    public static final DataKey<Boolean> AN_BOOLEAN = KeyFactory.bool("anBoolean");

/*
    public static final DataKey<List<String>> PLUS_OP = KeyFactory.multipleStringList("ArithmeticOperator: '" + ArithmeticOperators.PLUS + "'  ", ArithmeticOperators.PLUS_OP).setDefault(() -> ArithmeticOperators.PLUS_OP);
    public static final DataKey<List<String>> MINUS_OP = KeyFactory.multipleStringList("ArithmeticOperator: '" + ArithmeticOperators.MINUS + "'  ", ArithmeticOperators.MINUS_OP).setDefault(() -> ArithmeticOperators.MINUS_OP);
    public static final DataKey<List<String>> MULTIPLY_OP = KeyFactory.multipleStringList("ArithmeticOperator: '" + ArithmeticOperators.MULTIPLY + "'  ", ArithmeticOperators.MULTIPLY_OP).setDefault(() -> ArithmeticOperators.MULTIPLY_OP);
    public static final DataKey<List<String>> DIVIDE_OP = KeyFactory.multipleStringList("ArithmeticOperator: '" + ArithmeticOperators.DIVIDE + "'  ", ArithmeticOperators.DIVIDE_OP).setDefault(() -> ArithmeticOperators.DIVIDE_OP);
    public static final DataKey<List<String>> REMAINDER_OP = KeyFactory.multipleStringList("ArithmeticOperator: '" + ArithmeticOperators.REMAINDER + "'  ", ArithmeticOperators.REMAINDER_OP).setDefault(() -> ArithmeticOperators.REMAINDER_OP);


    public static final DataKey<List<String>> SIGNED_LEFT_SHIFT_OP = KeyFactory.multipleStringList("BitwiseOperator: '" + BitwiseOperators.SIGNED_LEFT_SHIFT + "'  ", BitwiseOperators.SIGNED_LEFT_SHIFT_OP).setDefault(() -> BitwiseOperators.SIGNED_LEFT_SHIFT_OP);
    public static final DataKey<List<String>> SIGNED_RIGHT_SHIFT_OP = KeyFactory.multipleStringList("BitwiseOperator: '" + BitwiseOperators.SIGNED_RIGHT_SHIFT + "'  ", BitwiseOperators.SIGNED_RIGHT_SHIFT_OP).setDefault(() -> BitwiseOperators.SIGNED_RIGHT_SHIFT_OP);
    public static final DataKey<List<String>> UNSIGNED_RIGHT_SHIFT_OP = KeyFactory.multipleStringList("BitwiseOperator: '" + BitwiseOperators.UNSIGNED_RIGHT_SHIFT + "'  ", BitwiseOperators.UNSIGNED_RIGHT_SHIFT_OP).setDefault(() -> BitwiseOperators.UNSIGNED_RIGHT_SHIFT_OP);


    public static final DataKey<List<String>> AND_OP = KeyFactory.multipleStringList("ConditionalOperators: '" + ConditionalOperators.AND + "'  ", ConditionalOperators.AND_OP).setDefault(() -> ConditionalOperators.AND_OP);
    public static final DataKey<List<String>> OR_OP = KeyFactory.multipleStringList("ConditionalOperators: '" + ConditionalOperators.OR + "'  ", ConditionalOperators.OR_OP).setDefault(() -> ConditionalOperators.OR_OP);


    public static final DataKey<List<String>> MINOR_OP = KeyFactory.multipleStringList("RelationalOperators: '" + RelationalOperators.MINOR + "'  ", RelationalOperators.MINOR_OP).setDefault(() -> RelationalOperators.MINOR_OP);
    public static final DataKey<List<String>> MINOR_OR_EQUAL_OP = KeyFactory.multipleStringList("RelationalOperators: '" + RelationalOperators.MINOR_OR_EQUAL + "'  ", RelationalOperators.MINOR_OR_EQUAL_OP).setDefault(() -> RelationalOperators.MINOR_OR_EQUAL_OP);
    public static final DataKey<List<String>> GREATER_OP = KeyFactory.multipleStringList("RelationalOperators: '" + RelationalOperators.GREATER + "'  ", RelationalOperators.GREATER_OP).setDefault(() -> RelationalOperators.GREATER_OP);
    public static final DataKey<List<String>> GREATER_OR_EQUAL_OP = KeyFactory.multipleStringList("RelationalOperators: '" + RelationalOperators.GREATER_OR_EQUAL + "'  ", RelationalOperators.GREATER_OR_EQUAL_OP).setDefault(() -> RelationalOperators.GREATER_OR_EQUAL_OP);
    public static final DataKey<List<String>> EQUAL_OP = KeyFactory.multipleStringList("RelationalOperators: '" + RelationalOperators.EQUAL + "'  ", RelationalOperators.EQUAL_OP).setDefault(() -> RelationalOperators.EQUAL_OP);
    public static final DataKey<List<String>> DIFFERENT_OP = KeyFactory.multipleStringList("RelationalOperators: '" + RelationalOperators.DIFFERENT + "'  ", RelationalOperators.DIFFERENT_OP).setDefault(() -> RelationalOperators.DIFFERENT_OP);


    public static final DataKey<List<String>> UNARY_PLUS_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.UNARY_PLUS + "'  ", UnaryOperators.UNARY_PLUS_OP).setDefault(() -> UnaryOperators.UNARY_PLUS_OP);
    public static final DataKey<List<String>> UNARY_MINUS_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.UNARY_MINUS + "'  ", UnaryOperators.UNARY_MINUS_OP).setDefault(() -> UnaryOperators.UNARY_MINUS_OP);
    public static final DataKey<List<String>> NEGATE_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.NEGATE + "'  ", UnaryOperators.NEGATE_OP).setDefault(() -> UnaryOperators.NEGATE_OP);
    public static final DataKey<List<String>> INCREMENT_AFTER_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.INCREMENT_AFTER + "'  ", UnaryOperators.INCREMENT_AFTER_OP).setDefault(() -> UnaryOperators.INCREMENT_AFTER_OP);
    public static final DataKey<List<String>> INCREMENT_BEFORE_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.INCREMENT_BEFORE + "'  ", UnaryOperators.INCREMENT_BEFORE_OP).setDefault(() -> UnaryOperators.INCREMENT_BEFORE_OP);
    public static final DataKey<List<String>> DECREMENT_AFTER_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.DECREMENT_AFTER + "'  ", UnaryOperators.DECREMENT_AFTER_OP).setDefault(() -> UnaryOperators.DECREMENT_AFTER_OP);
    public static final DataKey<List<String>> DECREMENT_BEFORE_OP = KeyFactory.multipleStringList("UnaryOperators: '" + UnaryOperators.DECREMENT_BEFORE + "'  ", UnaryOperators.DECREMENT_BEFORE_OP).setDefault(() -> UnaryOperators.DECREMENT_BEFORE_OP);
*/


public static void main(String[] args) {

        SpecsSystem.programStandardInit();

        StoreDefinitionBuilder storeDefinitionBuilder = new StoreDefinitionBuilder(Tese_UI.class);

        storeDefinitionBuilder.startSection("Arithmetic Operators");
        ArithmeticOperators.getDataKeys().forEach(storeDefinitionBuilder::addKey);

        storeDefinitionBuilder.startSection("BitWise Operators");
        BitwiseOperators.getDataKeys().forEach(storeDefinitionBuilder::addKey);

        storeDefinitionBuilder.startSection("Conditional Operators");
        ConditionalOperators.getDataKeys().forEach(storeDefinitionBuilder::addKey);

        storeDefinitionBuilder.startSection("Relational Operators");
        RelationalOperators.getDataKeys().forEach(storeDefinitionBuilder::addKey);

        storeDefinitionBuilder.startSection("Unary Operators");
        UnaryOperators.getDataKeys().forEach(storeDefinitionBuilder::addKey);


        storeDefinition = storeDefinitionBuilder.build();
        AppPersistence persistence = new XmlPersistence(storeDefinition);
        persistence.saveData(new File("Data.xml"), DataStore.newInstance(storeDefinition));

        App app = App.newInstance(storeDefinition, persistence, new MutatorKernel());
        JOptionsUtils.executeApp(app, Arrays.asList(args));
    }

}
