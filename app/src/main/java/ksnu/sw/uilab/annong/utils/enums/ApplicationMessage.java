package ksnu.sw.uilab.annong.utils.enums;

public enum ApplicationMessage {
    MAKE_NEW_CROP_SELECT_MESSAGE("새로운 작물");

    private final String message;

    ApplicationMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }
}
