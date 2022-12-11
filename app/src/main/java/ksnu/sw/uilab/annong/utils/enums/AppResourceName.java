package ksnu.sw.uilab.annong.utils.enums;

public enum AppResourceName {

    CROPS_LIST_DIRECTORY("cropsList.csv");

    private final String resourceName;


    AppResourceName(String resourceName){
        this.resourceName = resourceName;
    }

    public String getResourceName(){
        return this.resourceName;
    }

}
