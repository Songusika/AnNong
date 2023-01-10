package ksnu.sw.uilab.annong.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.utils.FileUtils;
import ksnu.sw.uilab.annong.utils.enums.AppResourceExtensions;

public class DownloadCsvActivity extends AppCompatActivity {

    String file_name;

    TextView textView_path;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_csv);

        Intent intent = getIntent();
        file_name = intent.getStringExtra("file_path")+ AppResourceExtensions.CSV.getFileExtension();
        FileUtils.moveFileToDocument(this, file_name);
        textView_path = (TextView) findViewById(R.id.textView_path);
        textView_path.setText(file_name);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}