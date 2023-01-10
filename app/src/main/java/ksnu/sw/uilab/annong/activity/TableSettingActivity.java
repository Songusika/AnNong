package ksnu.sw.uilab.annong.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
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
import ksnu.sw.uilab.annong.utils.CsvUtils;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.BasicCropColumn;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class TableSettingActivity extends AppCompatActivity {

    TextView textView_crop_name;
    TableLayout setting_table_layout;
    Button btn_add_row, btn_delete_row, btn_save_table;

    CropMeta cropMetaData;
    String crop_name;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_setting);

        Intent intent = getIntent();
        crop_name = intent.getStringExtra("crop_name");

        textView_crop_name = (TextView) findViewById(R.id.textView_crop_name);
        textView_crop_name.setText(crop_name);

        setting_table_layout = (TableLayout) findViewById(R.id.setting_table_layout);

        initCropMetaData(intent.getExtras().getString(Extras.CROP_NAME_KEY.getKey()));


        /* 테이블 로우 추가 */
        btn_add_row = (Button) findViewById(R.id.btn_add_row);
        btn_add_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                add_tableRow();
            }
        });

        /* 테이블 로우 삭제 */
        btn_delete_row = (Button) findViewById(R.id.btn_delete_row);
        btn_delete_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_tableRow();
            }
        });

        /* 테이블 저장 */
        btn_save_table = (Button) findViewById(R.id.btn_save_table);
        btn_save_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(TableSettingActivity.this);
                dialog.setContentView(R.layout.dialog_view);

                TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
                TextView dialog_msg = (TextView) dialog.findViewById(R.id.dialog_msg);
                EditText dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);
                Button dialog_btn_no = (Button) dialog.findViewById(R.id.dialog_btn_no);
                Button dialog_btn_ok = (Button) dialog.findViewById(R.id.dialog_btn_ok);

                dialog_title.setText("저장");
                dialog_msg.setText("메세지 입력 알아서 하기");
                dialog_text.setVisibility(View.GONE);
                dialog_btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        saveTable();
                        makeTableHeader();
                        finish();
                    }
                });

                dialog.show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initCropMetaData(String cropName){
        cropMetaData = getCropMetaData(cropName);
        textView_crop_name.setText(cropMetaData.getCropName());
        initCropRowMetaData(cropMetaData.getRows());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initCropRowMetaData(List<CropRowMeta> cropRowMetaData){
        for(CropRowMeta rowMeta: cropRowMetaData){
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.add_table_setting_row, null);
            ((EditText) tableRow.getVirtualChildAt(1)).setText(rowMeta.getColumnName());
            ((Spinner) tableRow.getVirtualChildAt(2)).setSelection(rowMeta.getDataTypeIndex());
            ((CheckBox) tableRow.getVirtualChildAt(3)).setChecked(rowMeta.isRequired());
            setting_table_layout.addView(tableRow);
        }

    }

    /**
     * @param cropName 불러올 Json 파일 (작물 이름)
     * @return CropMeta Json 파일로부터 객체 인스턴스화된 작물 메타데이터 객체
     */
    private CropMeta getCropMetaData(String cropName){
        return JsonUtils.getInstanceFromJson(this, cropName, CropMeta.class);
    }

    private void makeTableHeader(){
        appendTableHeader(BasicCropColumn.MEASUREMENT_DATE.toString());

        for(CropRowMeta rowMeta: this.cropMetaData.getRows()){
            appendTableHeader(rowMeta.getColumnName());
        }
        CsvUtils.writeNewLine(this, this.cropMetaData.getCropName());
    }

    private void appendTableHeader(String data) {
        CsvUtils.writeCsvData(this, this.cropMetaData.getCropName(), data);
    }

    /* 테이블 행 추가 */
    void add_tableRow() {
        TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.add_table_setting_row, null);
        setting_table_layout.addView(tableRow);
    }

    /* 테이블 행 삭제 */
    void delete_tableRow() {
        ArrayList<Integer> delete_list = new ArrayList<>();

        for(int i=0; i<setting_table_layout.getChildCount(); i++) {
            TableRow tableRow = (TableRow) setting_table_layout.getChildAt(i);
            CheckBox checkBox = (CheckBox) tableRow.getChildAt(0);
            if(checkBox.isChecked()) {
                delete_list.add(i);
            }
        }

        int count = 0;
        for(int i=0; i<delete_list.size(); i++) {
            setting_table_layout.removeViewAt(delete_list.get(i)-count);
            count++;
        }

        delete_list.clear();
    }

    private CropRowMeta makeNewCropRowMeta(TableRow row){
        String columnName = getColumnName(row);
        String dataType = getDataType(row);
        boolean isRequired = getRequireOption(row);

        return new CropRowMeta(columnName, dataType, isRequired);
    }

    private void saveTable(){
        List<CropRowMeta> cropRowMeta = new ArrayList<>();
        CropMeta cropMeta = new CropMeta(getCropName());

        for(int index =0; index<setting_table_layout.getChildCount(); index++){
            TableRow currentRow = (TableRow) setting_table_layout.getChildAt(index);
            cropRowMeta.add(makeNewCropRowMeta(currentRow));
        }
        cropMeta.setRows(cropRowMeta);
        this.cropMetaData = cropMeta;
        JsonUtils.writeJsonData(this, getCropName(), cropMeta);
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
        return crop_name;
    }
}