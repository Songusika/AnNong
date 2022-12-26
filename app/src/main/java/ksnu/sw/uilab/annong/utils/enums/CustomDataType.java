package ksnu.sw.uilab.annong.utils.enums;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.Arrays;

public enum CustomDataType {
    NUMBER(0, "숫자"),
    STRING(1, "텍스트");

    private final int index;
    private final String dataType;

    CustomDataType(int index, String dataType){
        this.index = index;
        this.dataType = dataType;
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
}
