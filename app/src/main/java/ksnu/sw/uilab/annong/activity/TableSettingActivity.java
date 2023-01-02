package ksnu.sw.uilab.annong.activity;

import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.domain.CropRowMeta;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class TableSettingActivity extends AppCompatActivity {

    CropMeta cropMetaData;
    TextView cropsName;
    TableLayout tableLayout;
    Button btnAddRow, btnDeleteRow, btnSaveTable;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_setting);

        initComponents();
        initEvent();
        initCropMetaData(getIntent().getExtras().getString(Extras.CROP_NAME_KEY.getKey()));
    }

    private void initComponents(){
        cropsName = findViewById(R.id.crops_name);
        tableLayout = findViewById(R.id.table_layout);
        btnAddRow = findViewById(R.id.btn_add_row);
        btnDeleteRow = findViewById(R.id.btn_delete_row);
        btnSaveTable = findViewById(R.id.btn_save_table);
    }

    private void initEvent(){
        initBtnAddEvent();
        initBtnDeleteEvent();
        initBtnSaveTable();
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void initCropMetaData(String cropName){
        cropMetaData = getCropMetaData(cropName);
        cropsName.setText(cropMetaData.getCropName());
        initCropRowMetaData(cropMetaData.getRows());
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void initCropRowMetaData(List<CropRowMeta> cropRowMetaData){
        for(CropRowMeta rowMeta: cropRowMetaData){
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_setting_add_row, null);
            ((EditText) tableRow.getVirtualChildAt(1)).setText(rowMeta.getColumnName());
            ((Spinner) tableRow.getVirtualChildAt(2)).setSelection(rowMeta.getDataTypeIndex());
            ((CheckBox) tableRow.getVirtualChildAt(3)).setChecked(rowMeta.isRequired());
            tableLayout.addView(tableRow);
        }
    }

    /**
     * @param cropName 불러올 Json 파일 (작물 이름)
     * @return CropMeta Json 파일로부터 객체 인스턴스화된 작물 메타데이터 객체
     */
    private CropMeta getCropMetaData(String cropName){
        return JsonUtils.getInstanceFromJson(this, cropName, CropMeta.class);
    }

    private void initBtnAddEvent(){
        btnAddRow.setOnClickListener(view -> addTableRow());
    }

    private void initBtnDeleteEvent(){
        btnDeleteRow.setOnClickListener(view -> deleteTableRow());
    }

    private void initBtnSaveTable(){btnSaveTable.setOnClickListener(view -> saveTable());}

    /* 테이블 행 추가2 */
    private void addTableRow() {
        TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_setting_add_row, null);
        Log.d("", ""+tableRow.getVirtualChildCount());
        tableLayout.addView(tableRow);
    }

    /* 테이블 행 삭제 */
    private void deleteTableRow() {
        ArrayList<Integer> delete_list = new ArrayList<>();

        for(int i = 0; i< tableLayout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) tableLayout.getChildAt(i);
            CheckBox checkBox = (CheckBox) tableRow.getChildAt(0);
            if(checkBox.isChecked()) {
                delete_list.add(i);
                Log.e("TAG", i+"행 추가");
            }
        }

        int count = 0;
        for(int i=0; i<delete_list.size(); i++) {
            Log.e("TAG", delete_list.get(i) +"번째 행 삭제");
            tableLayout.removeViewAt(delete_list.get(i)-count);
            count++;
        }
        delete_list.clear();
    }

    private void saveTable(){
        List<CropRowMeta> cropRowMeta = new ArrayList<>();
        CropMeta cropMeta = new CropMeta(getCropName());

        for(int index =0; index<tableLayout.getChildCount(); index++){
            TableRow currentRow = (TableRow) tableLayout.getChildAt(index);
            cropRowMeta.add(makeNewCropRowMeta(currentRow));
        }
        cropMeta.setRows(cropRowMeta);
        JsonUtils.writeJsonData(this, getCropName(), cropMeta);
    }

    private CropRowMeta makeNewCropRowMeta(TableRow row){
        String columnName = getColumnName(row);
        String dataType = getDataType(row);
        boolean isRequired = getRequireOption(row);

        return new CropRowMeta(columnName, dataType, isRequired);
    }

    private String getColumnName(TableRow row){
        return ((EditText)(row.getChildAt(1))).getText().toString();
    }

    private String getDataType(TableRow row){
        return ((Spinner)(row.getChildAt(2))).getSelectedItem().toString();
    }

    private boolean getRequireOption(TableRow row){
        return ((CheckBox)(row.getChildAt(3))).isChecked();
    }

    private String getCropName(){
        return this.cropsName.getText().toString();
    }
}