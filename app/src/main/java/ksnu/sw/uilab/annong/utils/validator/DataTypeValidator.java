package ksnu.sw.uilab.annong.utils.validator;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;

import java.util.NoSuchElementException;

import ksnu.sw.uilab.annong.utils.enums.OneSpotNumber;
import ksnu.sw.uilab.annong.utils.validator.exception.NotCorrectDataTypeException;

public class DataTypeValidator {
    /*
    * 데이터를 HashMap 에 추가를 용이하게 하기 위해서 String 으로 반환,
    * 추후 데이터 타입이 더 많아 질 경우 데이터 타입에 대한 상위 인터페이스를 작성하여 HashMap 에 넣도록 리팩터링 요망
    * 2023.1.4(수) 기준 요구 데이터 타입 숫자, 문자열
    * */
    @RequiresApi(api = VERSION_CODES.N)
    public static String validateNumberTypeData(String data) throws NotCorrectDataTypeException {
        double value;

        try{
            value = Double.parseDouble(removeBlank(data));
        }catch (NumberFormatException e){
            value = checkOneSpotNumber(data);
        }
        return Double.toString(value);
    }

    @RequiresApi(api = VERSION_CODES.N)
    private static double checkOneSpotNumber(String data) throws NotCorrectDataTypeException{
        try{
            return OneSpotNumber.findValueByKorean(data);
        }catch (NoSuchElementException e){
            throw new NotCorrectDataTypeException("숫자가 아닙니다.");
        }
    }

    private static String removeBlank(String data){
        return data.replace(" ", "");
    }
}
