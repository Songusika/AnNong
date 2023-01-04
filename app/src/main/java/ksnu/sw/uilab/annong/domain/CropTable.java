package ksnu.sw.uilab.annong.domain;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;

public class CropTable {
    private Context context;
    private List<CropRow> cropRows;

    public CropTable(Context context){
        this.context = context;
        cropRows = new ArrayList<>();
    }

    public void addNewRow(CropRow row){
        cropRows.add(row);
    }

    public void saveTableToCSV(){
        for(CropRow row: cropRows){
            row.saveRowInCSV(context);
        }
    }

}
