package ksnu.sw.uilab.annong.utils.enums;

public enum ApplicationConstants {
    MAKE_NEW_CROP_SELECT_MESSAGE("새로운 작물");


    private final String message;

    ApplicationConstants(String message){
        this.message = message;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
