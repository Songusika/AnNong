package ksnu.sw.uilab.annong.utils.enums;

public enum AppResourceName {

    CROPS_LIST_FILE_NAME("cropsList");

    private final String resourceName;


    AppResourceName(String resourceName){
        this.resourceName = resourceName;
    }

    public String getValue(){
        return this.resourceName;
    }

}
