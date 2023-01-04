package ksnu.sw.uilab.annong.domain;

import android.content.Context;
import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import java.util.LinkedHashMap;
import ksnu.sw.uilab.annong.utils.CsvUtils;
import ksnu.sw.uilab.annong.utils.enums.BasicCropColumn;
import ksnu.sw.uilab.annong.utils.enums.CustomDataType;
import ksnu.sw.uilab.annong.utils.validator.exception.NotCorrectDataTypeException;

public class CropRow {

    private CropMeta cropMeta;
    private LinkedHashMap<String, String> row;

    public CropRow(CropMeta cropRowMeta) {
        this.cropMeta = cropRowMeta;
        row = new LinkedHashMap<>();
        addBasicColumn();
        initCustomColumn();
    }

    private void initCustomColumn() {
        for (CropRowMeta rowMeta : cropMeta.getRows()) {
            row.put(rowMeta.getColumnName(), null);
        }
    }

    private void addBasicColumn() {
        for (BasicCropColumn column : BasicCropColumn.values()) {
            row.put(column.toString(), null);
        }
    }

    @RequiresApi(api = VERSION_CODES.N)
    public void fillBasicColumnData(String column, String data){
        row.replace(column, data);
    }

    @RequiresApi(api = VERSION_CODES.N)
    public void fillColumnData(String column, String data) throws NotCorrectDataTypeException {
        row.replace(column,CustomDataType.getCustomDataType(cropMeta.getDataTypeByColumn(column)).validate(data));
    }

    @RequiresApi(api = VERSION_CODES.N)
    public String getNextColumnName() {
        return row.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == null)
                .findFirst()
                .get()
                .getKey();
    }

    public boolean isFullRow() {
        return !row.containsValue(null);
    }

    public void saveRowInCSV(Context context){
        for(String columnData: this.row.values()){
            CsvUtils.writeCsvData(context, cropMeta.getCropName(), columnData);
        }
        CsvUtils.writeNewLine(context, cropMeta.getCropName());
    }
}
