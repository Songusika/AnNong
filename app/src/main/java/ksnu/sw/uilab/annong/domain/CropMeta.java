package ksnu.sw.uilab.annong.domain;

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

    public void addRow(CropRowMeta rowMeta){
        this.rows.add(rowMeta);
    }
}
