package ksnu.sw.uilab.annong.utils.enums;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import ksnu.sw.uilab.annong.utils.enums.validator.DataTypeValidator;
import ksnu.sw.uilab.annong.utils.enums.validator.exception.NotCorrectDataTypeException;

@FunctionalInterface
interface validateFunction<T>{
    void apply(T t) throws NotCorrectDataTypeException;
}

@RequiresApi(api = VERSION_CODES.N)
public enum CustomDataType {
    NUMBER(0, "숫자", (data)->{
        DataTypeValidator.validateNumberTypeData(data);
    }),
    /*
    * 추후 텍스트 타입의 데이터에 대해서 검증 조건이 추가될 경우 람다식으로 메서드 작성
    * */
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
    public void validate(String data) throws NotCorrectDataTypeException{
        validateExpression.apply(data);
    }
}
