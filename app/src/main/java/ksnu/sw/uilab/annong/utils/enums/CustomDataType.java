package ksnu.sw.uilab.annong.utils.enums;

import android.os.Build.VERSION_CODES;
import android.util.Log;
import androidx.annotation.RequiresApi;
import java.util.Arrays;
import ksnu.sw.uilab.annong.utils.validator.DataTypeValidator;
import ksnu.sw.uilab.annong.utils.validator.exception.NotCorrectDataTypeException;

@FunctionalInterface
interface validateFunction<T>{
    T apply(T t) throws NotCorrectDataTypeException;
}

@RequiresApi(api = VERSION_CODES.N)
public enum CustomDataType {
    NUMBER(0, "숫자", (data)->{
        Log.e("Validator", DataTypeValidator.validateNumberTypeData(data));
        return DataTypeValidator.validateNumberTypeData(data);
    }),
    /*
    * 추후 텍스트 타입의 데이터에 대해서 검증 조건이 추가될 경우 람다식으로 메서드 작성
    * */
    STRING(1, "텍스트", (data)-> data);

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

    public static CustomDataType getCustomDataType(String dataType){
        return Arrays.stream(CustomDataType.values())
                .filter(customDataType -> customDataType.getDataType().equals(dataType))
                .findFirst().get();
    }

    @RequiresApi(api = VERSION_CODES.N)
    public String validate(String data) throws NotCorrectDataTypeException{
        return validateExpression.apply(data);
    }
}
