package ksnu.sw.uilab.annong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropsList;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class CropSelectActivity extends AppCompatActivity {

    private Spinner selectCropsDropDown;
    private Button goNextBtn;
    private CropsList cropsList;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_select);

        initCropList();
        initComponents();
        initAdapter();
        initEvent();
    }

    private void initCropList(){
        cropsList = CropsList.getInstance().initItem(this);
    }

    private void initComponents(){
        selectCropsDropDown= findViewById(R.id.cropsSelectSpinner);
        goNextBtn = findViewById(R.id.goNextBtn);
    }

    private void initEvent(){
        initNextBtnEvent();
    }

    private void initAdapter(){
        adapter = new ArrayAdapter(getBaseContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, cropsList.getCropsList());
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        selectCropsDropDown.setAdapter(adapter);
    }

    private void initNextBtnEvent(){
        goNextBtn.setOnClickListener(view -> {
            Intent inputDataActivity = new Intent(getApplicationContext(), InputDataActivityTest.class);
            inputDataActivity.putExtra(Extras.CROP_NAME_KEY.getKey(), selectCropsDropDown.getSelectedItem().toString());
            startActivity(inputDataActivity);
        });
    }
}