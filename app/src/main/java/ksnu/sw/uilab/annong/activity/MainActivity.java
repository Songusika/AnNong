package ksnu.sw.uilab.annong.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import ksnu.sw.uilab.annong.R;


public class MainActivity extends AppCompatActivity {
    Button btn_table_setting, btn_input_data, btn_output_data, btn_app_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        btn_table_setting = (Button) findViewById(R.id.own_btn_table_setting);
        btn_input_data = (Button) findViewById(R.id.btn_input_data);
        btn_output_data = (Button) findViewById(R.id.btn_output_data);
        btn_app_setting = (Button) findViewById(R.id.btn_app_setting);

        btn_table_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CropSelectActivity.class);
                intent.putExtra("startPage", 1);
                startActivity(intent);
            }
        });

        btn_input_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CropSelectActivity.class);
                intent.putExtra("startPage", 2);
                startActivity(intent);
            }
        });

        btn_output_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CropSelectActivity.class);
                intent.putExtra("startPage", 3);
                startActivity(intent);
            }
        });

        btn_app_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CropSelectActivity.class);
                startActivity(intent);
            }
        });
    }
}