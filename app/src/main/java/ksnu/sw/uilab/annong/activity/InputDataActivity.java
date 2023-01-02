package ksnu.sw.uilab.annong.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class InputDataActivity extends AppCompatActivity {

    TextView time_progress;
    ProgressBar progressBar;
    TableLayout tableLayout;

    CropMeta cropMeta;

    long baseTime, pauseTime, setTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        time_progress = (TextView) findViewById(R.id.time_progress);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        cropMeta = JsonUtils.getInstanceFromJson(this, getIntent().getExtras().getString(Extras.CROP_NAME_KEY.getKey()), CropMeta.class);

        /* 중지버튼 -> 초기화 */
        time_progress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        /* 데이터 입력 시 add_table_data_row.xml 사용 */
//        tableLayout = (TableLayout) findViewById(R.id.table_layout);
//        @SuppressLint("ResourceType") TableRow tableRow = (TableRow) findViewById(R.layout.add_table_data_row);
//        tableLayout.addView(tableRow);
    }
}