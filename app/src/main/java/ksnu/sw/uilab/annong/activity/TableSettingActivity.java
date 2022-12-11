package ksnu.sw.uilab.annong.activity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropsList;

public class TableSettingActivity extends AppCompatActivity {
    CropsList items;

    private Spinner selectCropsDropDown;
    private Button goNextBtn;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_setting);

        items = CropsList.getInstance().initItems(this);
        initComponents();
        initAdapter();
    }

    private void initComponents(){
        selectCropsDropDown= findViewById(R.id.cropsSelectSpinner);
        goNextBtn = findViewById(R.id.goNextBtn);
    }

    private void initAdapter(){
        adapter = new ArrayAdapter<>(getBaseContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, items.getCropsList());
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);

        selectCropsDropDown.setAdapter(adapter);
    }


}
