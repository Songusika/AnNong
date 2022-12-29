package ksnu.sw.uilab.annong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.domain.CropsList;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.ApplicationConstants;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class TableSettingActivity extends AppCompatActivity {
    CropsList cropItems;

    private Spinner selectCropsDropDown;
    private Button goNextBtn;
    private EditText getCropItemEditText;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_meta_select);

        cropItems = CropsList.getInstance().initTableSettingItems(this);
        initComponents();
        initAdapter();
        initEvent();
    }

    private void initComponents(){
        selectCropsDropDown= findViewById(R.id.cropsSelectSpinner);
        goNextBtn = findViewById(R.id.goNextBtn);
        getCropItemEditText = findViewById(R.id.newCropItemEditText);
    }

    private void initAdapter(){
        adapter = new ArrayAdapter<>(getBaseContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cropItems.getCropsList());
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        selectCropsDropDown.setAdapter(adapter);
    }

    private void initEvent(){
        initSpinnerEvent();
        initNextBtnEvent();
    }

    private void initSpinnerEvent() {
        selectCropsDropDown.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                changeVisibleCropEditText(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void changeVisibleCropEditText(int position){
        if(position == 0){
            getCropItemEditText.setVisibility(View.VISIBLE);
        }
    }

    private void initNextBtnEvent(){
        goNextBtn.setOnClickListener(view -> {
            String selectedItem = (String)selectCropsDropDown.getSelectedItem();
            if(selectedItem.equals(ApplicationConstants.MAKE_NEW_CROP_SELECT_MESSAGE.toString())){
                selectedItem = getCropItemEditText.getText().toString();
                //cropList.csv 에 새로운 작물 이름 추가
                addNewCropItem(selectedItem);
                // 새로운 작물에 대해서 json 파일 생성
                initNewCropMeta(selectedItem);
            }
            startTableSettingActivity(selectedItem);
        });
    }

    private void startTableSettingActivity(String cropName){
        Intent tableSettingActivity = new Intent(getApplicationContext(), ActivityTableSetting.class);
        tableSettingActivity.putExtra(Extras.CROP_NAME_KEY.getKey(), cropName);
        startActivity(tableSettingActivity);
    }

    private void addNewCropItem(String newCropItem){
        cropItems.addNewCrops(newCropItem);
    }

    private void initNewCropMeta(String newCropName){
        JsonUtils.writeJsonData(this, newCropName, new CropMeta(newCropName));
    }

}
