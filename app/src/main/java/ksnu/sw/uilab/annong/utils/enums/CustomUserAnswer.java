package ksnu.sw.uilab.annong.utils.enums;

public enum CustomUserAnswer {
    RETRY("다시"),
    NONE("없음"),
    SAVE("네"),
    NO("아니요");

    private final String answer;

    CustomUserAnswer(String answer){
        this.answer = answer;
    }

    @Override
    public String toString(){
        return this.answer;
    }
}
