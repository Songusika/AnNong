package ksnu.sw.uilab.annong.utils.enums;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@FunctionalInterface
interface validateFunction<T>{
    void apply(T t) throws Exception;
}

public enum CustomDataType {
    NUMBER(0, "숫자", (data)->{

    }),
    STRING(1, "텍스트", (data)->{});

    private final int index;
    private final String dataType;
    private final validateFunction<String> validateExpression;

    CustomDataType(int index, String dataType, validateFunction<String> validateExpression){
        this.index = index;
        this.dataType = dataType;
        this.validateExpression = validateExpression;
    }
    private String getDataType(){
        return this.dataType;
    }

    private int getIndex(){
        return this.index;
    }

    @RequiresApi(api = VERSION_CODES.N)
    public static int getCustomDataTypeIndex(String dataType){
        return Arrays.stream(CustomDataType.values())
                .filter(customDataType -> customDataType.getDataType().equals(dataType))
                .findAny().get().getIndex();
    }

    @RequiresApi(api = VERSION_CODES.N)
    public void validate(String data) throws Exception{
        validateExpression.apply(data);
    }
}
