package ksnu.sw.uilab.annong.domain;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.List;

public class CropMeta {
    private String cropName;
    private List<CropRowMeta> rows;

    public CropMeta(String cropName){
        this.cropName = cropName;
        this.rows = new ArrayList<>();
    }

    public String getCropName(){
        return this.cropName;
    }

    public List<CropRowMeta> getRows(){
        return this.rows;
    }

    public void setRows(List<CropRowMeta> row){
        this.rows = row;
    }

    @RequiresApi(api = VERSION_CODES.N)
    public String getDataTypeByColumn(String column){
        return rows.stream()
                .filter(row -> row.getColumnName().equals(column))
                .findFirst()
                .get()
                .getDataType();
    }

    @RequiresApi(api = VERSION_CODES.N)
    public boolean getIsRequiredByColumn(String column){
        return rows.stream()
                .filter(row -> row.getColumnName().equals(column))
                .findFirst()
                .get()
                .isRequired();
    }
}
