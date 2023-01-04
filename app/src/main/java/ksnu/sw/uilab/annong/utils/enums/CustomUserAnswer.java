package ksnu.sw.uilab.annong.utils.enums;

public enum CustomUserAnswer {
    RETRY("다시"),
    NONE("없음"),
    STOP("중지"),
    SAVE("저장");

    private final String answer;

    CustomUserAnswer(String answer){
        this.answer = answer;
    }

    @Override
    public String toString(){
        return this.answer;
    }
}
