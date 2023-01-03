package ksnu.sw.uilab.annong.utils.enums;

public enum AppResourceExtensions {
    JSON(".json"),
    CSV(".csv");

    private final String fileExtension;

    AppResourceExtensions(String fileExtension){
        this.fileExtension = fileExtension;
    }

    public String getFileExtension(){
        return this.fileExtension;
    }
}
