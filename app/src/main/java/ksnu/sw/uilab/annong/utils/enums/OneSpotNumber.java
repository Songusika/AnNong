package ksnu.sw.uilab.annong.utils.enums;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum OneSpotNumber {
    ZERO("영", 0),
    ONE("일",1),
    TWO("이", 2),
    THREE("삼", 3),
    FOUR("시",4),
    FIVE("오", 5),
    SIX("육", 6),
    SEVEN("칠", 7),
    EIGHT("팔", 8),
    NINE("구", 9);

    private final String korean;
    private final int value;

    OneSpotNumber(String korean, int value){
        this.korean = korean;
        this.value = value;
    }

    private boolean isMatchKorean(String korean){
        return this.korean.equals(korean);
    }

    private int getValue(){
        return this.value;
    }

    @RequiresApi(api = VERSION_CODES.N)
    public static int findValueByKorean(String korean) throws NoSuchElementException {
        return Arrays.stream(OneSpotNumber.values())
                .filter(oneSpotNumber -> oneSpotNumber.isMatchKorean(korean))
                .findAny().get().getValue();
    }
}
