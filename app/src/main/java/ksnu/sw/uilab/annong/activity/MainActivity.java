package ksnu.sw.uilab.annong.activity;

import android.content.Intent;
import android.os.Build.VERSION_CODES;
import android.view.View;
import android.widget.Button;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.domain.CropRow;
import ksnu.sw.uilab.annong.domain.CropTable;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.validator.exception.NotCorrectDataTypeException;


public class MainActivity extends AppCompatActivity {
    Button btn_table_setting, btn_input_data, btn_output_data, btn_app_setting;

    @RequiresApi(api = VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_table_setting = (Button) findViewById(R.id.btn_table_setting);
        btn_input_data = (Button) findViewById(R.id.btn_input_data);
        btn_output_data = (Button) findViewById(R.id.btn_output_data);
        btn_app_setting = (Button) findViewById(R.id.btn_app_setting);

        btn_table_setting.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CropMetaSettingActivity.class);
            startActivity(intent);
        });

        btn_input_data.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CropSelectActivity.class);
            startActivity(intent);
        });
//
//        btn_output_data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Activity_OutputData.class);
//                startActivity(intent);
//            }
//        });
//
//        btn_app_setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, Activity_AppSetting.class);
//                startActivity(intent);
//            }
//        });
    }
}