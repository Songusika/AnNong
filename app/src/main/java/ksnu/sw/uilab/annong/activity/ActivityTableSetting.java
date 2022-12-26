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

public class ActivityTableSetting extends AppCompatActivity {

    TextView cropsName;
    TableLayout tableLayout;
    Button btnAddRow, btnDeleteRow;


    int count = 0;

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
    }

    private void initEvent(){
        initBtnAddEvent();
        initBtnDeleteEvent();
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void initCropMetaData(String cropName){
        CropMeta cropMetaData = getCropMetaData(cropName);
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

    private CropMeta getCropMetaData(String cropName){
        return JsonUtils.getInstanceFromJson(this, cropName, CropMeta.class);
    }

    private void initBtnAddEvent(){
        btnAddRow.setOnClickListener(view -> addTableRow());
    }

    private void initBtnDeleteEvent(){
        btnDeleteRow.setOnClickListener(view -> deleteTableRow());
    }

    /* 테이블 행 추가2 */
    void addTableRow() {
        TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_setting_add_row, null);
        Log.d("", ""+tableRow.getVirtualChildCount());
        EditText edit = (EditText)tableRow.getVirtualChildAt(1);

        edit.setText(""+count++);
        tableLayout.addView(tableRow);
    }

    /* 테이블 행 삭제 */
    void deleteTableRow() {
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
}