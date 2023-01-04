package ksnu.sw.uilab.annong.utils.enums;

public enum TextToSpeechMessage {
    ASK_MEASURE("측정해주세요"),
    ASK_DATA("을 말하세요"),
    ASK_RETRY("다시 말씀해주세요"),
    WRONG_DATA_TYPE("데이터 타입이 다릅니다." + ASK_RETRY.toString()),
    ASK_REQUIREMENT("필수 데이터 입니다" + ASK_RETRY.toString()),
    FINISH_ROW("측정이 완료되었습니다.");

    private final String msg;

    TextToSpeechMessage(String msg){
        this.msg = msg;
    }

    @Override
    public String toString(){
        return this.msg;
    }
}
