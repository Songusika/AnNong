package ksnu.sw.uilab.annong.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.domain.CropRow;
import ksnu.sw.uilab.annong.domain.CropTable;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class InputDataActivity extends AppCompatActivity {

    TextView textView_crop_name, time_progress;
    ProgressBar progressBar;
    TableLayout tableLayout;

    long baseTime, pauseTime, setTime;

    private CropMeta cropMeta;
    private CropTable cropTable;
    private CropRow currentCropRow;

    private TextToSpeech introduceCropDataTTS;
    private TextToSpeech checkCropDataTTS;

    private Intent speechToTextIntent;
    private SpeechRecognizer recognizer;

    private RecognitionListener receiveCropDataRecognitionListener;
    private RecognitionListener receiveCheckRecognitionListener;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        initCropMetaData();
        initCropTable();
        initTextToSpeech();
        initSpeechToTextIntent();
        initRecognitionListener();

        /* 시작 다이얼로그 하나 */
        Dialog dialog = new Dialog(InputDataActivity.this);
        dialog.setContentView(R.layout.dialog_start);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button start_button = (Button) dialog.findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

        textView_crop_name = (TextView) findViewById(R.id.textview_crop_name);
        textView_crop_name.setText(cropMeta.getCropName());

        time_progress = (TextView) findViewById(R.id.time_progress);
//        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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

        /* 데이터가 들어오면 사용 */
        tableLayout = (TableLayout) findViewById(R.id.table_layout);

    }

    private void initCropMetaData(){
        cropMeta = JsonUtils.getInstanceFromJson(this, getIntent().getExtras().getString(Extras.CROP_NAME_KEY.getKey()), CropMeta.class);
    }

    private void initCropTable(){
        this.cropTable = new CropTable(this);
        this.currentCropRow = new CropRow(cropMeta);
    }

    private void initTextToSpeech(){
        initIntroduceCropDataTTS();
        initCheckCropDataTTS();
    }

    private void settingTTSOnInit(TextToSpeech tts, UtteranceProgressListener ttsUtteranceListener){
        tts.setLanguage(Locale.KOREAN);
        tts.setPitch(1);
        tts.setOnUtteranceProgressListener(ttsUtteranceListener);
    }

    private void initIntroduceCropDataTTS(){
        TextToSpeech.OnInitListener introduceCropDataTTSListener = state -> {
            if (state == TextToSpeech.SUCCESS) {
                settingTTSOnInit(introduceCropDataTTS, new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {

                    }

                    @Override
                    public void onDone(String s) {

                    }

                    @Override
                    public void onError(String s) {

                    }
                });
                return;
            }
            Log.e("TTS", "fail to init Introduce TTS");
        };
        this.introduceCropDataTTS = new TextToSpeech(this, introduceCropDataTTSListener);
    }

    private void initCheckCropDataTTS(){
        TextToSpeech.OnInitListener checkCropDataTTSListener = state -> {
            if (state == TextToSpeech.SUCCESS) {
                settingTTSOnInit(checkCropDataTTS, new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {

                    }

                    @Override
                    public void onDone(String s) {

                    }

                    @Override
                    public void onError(String s) {

                    }
                });
                return;
            }
            Log.e("TTS", "fail to init check TTS");
        };
        this.checkCropDataTTS = new TextToSpeech(this, checkCropDataTTSListener);
    }

    private void initSpeechToTextIntent(){
        speechToTextIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechToTextIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        speechToTextIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
    }

    private void getCropDataFromSpeech(){
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this.receiveCropDataRecognitionListener);
        recognizer.startListening(speechToTextIntent);
    }

    private void getCheckFromSpeech(){
        recognizer = SpeechRecognizer.createSpeechRecognizer(this);
        recognizer.setRecognitionListener(this.receiveCheckRecognitionListener);
        recognizer.startListening(speechToTextIntent);
    }

    private void initRecognitionListener(){
        initCropDataRecognitionListener();
        initCheckRecognitionListener();
    }

    private void initCropDataRecognitionListener(){
        this.receiveCropDataRecognitionListener = new RecognitionListener() {
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

    private void initCheckRecognitionListener(){
        this.receiveCheckRecognitionListener = new RecognitionListener() {
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
}