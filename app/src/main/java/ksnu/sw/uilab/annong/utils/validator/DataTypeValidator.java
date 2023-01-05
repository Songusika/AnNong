package ksnu.sw.uilab.annong.utils.validator;

import android.os.Build.VERSION_CODES;
import android.speech.tts.TextToSpeech;
import androidx.annotation.RequiresApi;

import java.util.NoSuchElementException;

import ksnu.sw.uilab.annong.utils.enums.CustomUserAnswer;
import ksnu.sw.uilab.annong.utils.enums.OneSpotNumber;
import ksnu.sw.uilab.annong.utils.enums.TextToSpeechMessage;
import ksnu.sw.uilab.annong.utils.validator.exception.NotCorrectDataTypeException;
import ksnu.sw.uilab.annong.utils.validator.exception.NotNullDataTypeException;

public class DataTypeValidator {
    public static String validateNotNullTypeData(String data, boolean isRequired) throws NotNullDataTypeException {
        String blank = " ";
        if(data.equals(CustomUserAnswer.NONE.toString()) && isRequired){
            throw new NotNullDataTypeException(TextToSpeechMessage.ASK_REQUIREMENT.toString());
        }

        if(data.equals(CustomUserAnswer.NONE.toString())){
            return blank;
        }

        return data;
    }
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
            throw new NotCorrectDataTypeException(TextToSpeechMessage.WRONG_DATA_TYPE.toString());
        }
    }

    private static String removeBlank(String data){
        return data.replace(" ", "");
    }
}
