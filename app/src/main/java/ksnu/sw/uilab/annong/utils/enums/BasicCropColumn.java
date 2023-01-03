package ksnu.sw.uilab.annong.utils.enums;

public enum BasicCropColumn {
    MEASUREMENT_DATE("측정일");

    private final String column;

    BasicCropColumn(String column){
        this.column = column;
    }

    @Override
    public String toString() {
        return this.column;
    }
}
