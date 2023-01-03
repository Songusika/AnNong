package ksnu.sw.uilab.annong.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class InputDataActivity extends AppCompatActivity {

    TextView time_progress;
    ProgressBar progressBar;
    TableLayout tableLayout;

    CropMeta cropMeta;

    TextToSpeech tts;

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

    private void initSpeechToText(){
        tts = new TextToSpeech(this, new OnInitListener() {
            @Override
            public void onInit(int state) {
                if(state == TextToSpeech.SUCCESS){
                    tts.setLanguage(Locale.KOREAN);
                    tts.setPitch(1);
                    return;
                }
                Log.e("TTS", "fail to init TTS");
            }
        });
    }

    private void initTextToSpeech(){

    }

    private RecognitionListener CropDataRecognitionListener = new RecognitionListener(){
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {

        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };

    private RecognitionListener DataCheckRecognitionListener = new RecognitionListener(){

        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }

        @Override
        public void onBeginningOfSpeech() {

        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {

        }

        @Override
        public void onError(int i) {

        }

        @Override
        public void onResults(Bundle bundle) {

        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
}