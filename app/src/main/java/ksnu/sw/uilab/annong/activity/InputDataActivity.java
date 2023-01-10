package ksnu.sw.uilab.annong.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.domain.CropRow;
import ksnu.sw.uilab.annong.domain.CropTable;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.CustomUserAnswer;
import ksnu.sw.uilab.annong.utils.enums.Extras;
import ksnu.sw.uilab.annong.utils.enums.TextToSpeechMessage;
import ksnu.sw.uilab.annong.utils.validator.exception.NotCorrectDataTypeException;
import ksnu.sw.uilab.annong.utils.validator.exception.NotNullDataTypeException;

public class InputDataActivity extends AppCompatActivity {

    TextView textView_crop_name, time_progress;
    ProgressBar progressBar;
    TableLayout tableLayout;

    long baseTime, pauseTime, setTime;

    private final Activity activity = this;

    private CropMeta cropMeta;
    private CropTable cropTable;
    private CropRow currentCropRow;

    private Bundle textToSpeechParams;
    private TextToSpeech introduceCropDataTTS;
    private TextToSpeech checkCropDataTTS;
    private TextToSpeech receiveCropDataTTS;
    private TextToSpeech retryTTS;

    private Intent speechToTextIntent;
    private SpeechRecognizer recognizer;

    private RecognitionListener receiveCropDataRecognitionListener;
    private RecognitionListener receiveCheckRecognitionListener;

    private String currentData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        initCropMetaData();
        initCropTable();
        initTextToSpeechBundle();
        initTextToSpeech();
        initSpeechToTextIntent();
        initRecognitionListener();

        /* 시작 다이얼로그 하나 */
        Dialog dialog = new Dialog(InputDataActivity.this);
        dialog.setContentView(R.layout.dialog_start);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button start_button = (Button) dialog.findViewById(R.id.start_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                currentCropRow.fillBasicColumnData("측정일", "2023-01-06");
                askCropColumn();
            }
        });
        dialog.setCancelable(false);
        dialog.show();

        textView_crop_name = (TextView) findViewById(R.id.textview_crop_name);
        //textView_crop_name.setText(cropMeta.getCropName());

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

    private void addColumnInTable(String column, String data){
        TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.add_table_data_row_edit, null);
        ((TextView) tableRow.getChildAt(0)).setText(column);
        ((TextView) tableRow.getChildAt(1)).setText(data);
        tableLayout.addView(tableRow);
    }

    private void initCropMetaData() {
        cropMeta = JsonUtils.getInstanceFromJson(this, getIntent().getExtras().getString(Extras.CROP_NAME_KEY.getKey()),
                CropMeta.class);
    }

    private void initCropTable() {
        this.cropTable = new CropTable(this);
        this.currentCropRow = new CropRow(cropMeta);
    }

    private void initTextToSpeechBundle() {
        textToSpeechParams = new Bundle();
        textToSpeechParams.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, null);
    }

    private void initTextToSpeech() {
        initIntroduceCropDataTTS();
        initReceiveCropDataTTS();
        initCheckCropDataTTS();
        initRetryTTS();
    }

    private void settingTTSOnInit(TextToSpeech tts, UtteranceProgressListener ttsUtteranceListener) {
        tts.setLanguage(Locale.KOREAN);
        tts.setPitch(1);
        tts.setOnUtteranceProgressListener(ttsUtteranceListener);
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void askCropColumn() {
        String msg = currentCropRow.getNextColumnName() + TextToSpeechMessage.ASK_MEASURE.toString();
        introduceCropDataTTS.speak(msg, TextToSpeech.QUEUE_ADD, textToSpeechParams, msg);
        //TextView column = findViewById(R.id.textview_crop_name);
        //column.setText(currentCropRow.getNextColumnName());
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void askSayCropData() {
        String msg = currentCropRow.getNextColumnName() + TextToSpeechMessage.ASK_DATA.toString();
        receiveCropDataTTS.speak(msg, TextToSpeech.QUEUE_ADD, textToSpeechParams, msg);
    }

    private void askCheckData(String data) {
        String msg = data + TextToSpeechMessage.ASK_CHECK.toString();
        checkCropDataTTS.speak(msg, TextToSpeech.QUEUE_ADD, textToSpeechParams, msg);
    }

    private void askRetry(String msg){
        retryTTS.speak(msg, TextToSpeech.QUEUE_ADD, textToSpeechParams, msg);
    }

    private void standBy(UtteranceProgressListener listener, long second) {
        synchronized (listener) {
            try {
                listener.wait(second);
            } catch (InterruptedException e) {

            }
        }
    }

    private void initIntroduceCropDataTTS() {
        TextToSpeech.OnInitListener introduceCropDataTTSListener = state -> {
            if (state == TextToSpeech.SUCCESS) {
                settingTTSOnInit(introduceCropDataTTS, new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {

                    }

                    @RequiresApi(api = VERSION_CODES.N)
                    @Override
                    public void onDone(String s) {
                        standBy(this, 3000);
                        askSayCropData();
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

    private void initReceiveCropDataTTS() {
        TextToSpeech.OnInitListener receiveCropDataTTSListener = state -> {
            if (state == TextToSpeech.SUCCESS) {
                settingTTSOnInit(receiveCropDataTTS, new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {

                    }

                    @Override
                    public void onDone(String s) {
                        getCropDataFromSpeech();
                    }

                    @Override
                    public void onError(String s) {

                    }
                });
                return;
            }
            Log.e("TTS", "fail to init receive TTS");
        };
        this.receiveCropDataTTS = new TextToSpeech(this, receiveCropDataTTSListener);
    }

    private void initCheckCropDataTTS() {
        TextToSpeech.OnInitListener checkCropDataTTSListener = state -> {
            if (state == TextToSpeech.SUCCESS) {
                settingTTSOnInit(checkCropDataTTS, new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {

                    }

                    @Override
                    public void onDone(String s) {
                        getCheckFromSpeech();
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

    private void initRetryTTS(){
        TextToSpeech.OnInitListener retryTTSListener = state -> {
            if (state == TextToSpeech.SUCCESS) {
                settingTTSOnInit(retryTTS, new UtteranceProgressListener() {
                    @Override
                    public void onStart(String s) {

                    }

                    @RequiresApi(api = VERSION_CODES.N)
                    @Override
                    public void onDone(String s) {
                        askSayCropData();
                    }

                    @Override
                    public void onError(String s) {

                    }
                });
                return;
            }
            Log.e("TTS", "fail to init retry TTS");
        };
        this.retryTTS = new TextToSpeech(this, retryTTSListener);
    }

    private void initSpeechToTextIntent() {
        speechToTextIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechToTextIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
        speechToTextIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
    }

    private void getCropDataFromSpeech() {
        activity.runOnUiThread(
                () -> {
                    recognizer = SpeechRecognizer.createSpeechRecognizer(activity);
                    recognizer.setRecognitionListener(receiveCropDataRecognitionListener);
                    recognizer.startListening(speechToTextIntent);
                }
        );
    }

    private void getCheckFromSpeech() {
        activity.runOnUiThread(
                () -> {
                    recognizer = SpeechRecognizer.createSpeechRecognizer(activity);
                    recognizer.setRecognitionListener(receiveCheckRecognitionListener);
                    recognizer.startListening(speechToTextIntent);
                }
        );
    }

    private void initRecognitionListener() {
        initCropDataRecognitionListener();
        initCheckRecognitionListener();
    }

    @RequiresApi(api = VERSION_CODES.N)
    private void saveData(){
        checkCropDataTTS.speak("저장되었습니다.", TextToSpeech.QUEUE_FLUSH, null);
        try {
            addColumnInTable(currentCropRow.getNextColumnName(), currentData);
            currentCropRow.fillColumnData(currentCropRow.getNextColumnName(), currentData);
        } catch (NotCorrectDataTypeException e) {
            e.printStackTrace();
        } catch (NotNullDataTypeException e) {
            e.printStackTrace();
        }
        if(!currentCropRow.isFullRow()){
            askCropColumn();
            return;
        }
        cropTable.addNewRow(currentCropRow);
        cropTable.saveTableToCSV();
    }

    private void initCropDataRecognitionListener() {
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
                askRetry(TextToSpeechMessage.ASK_RETRY.toString());
            }

            @RequiresApi(api = VERSION_CODES.N)
            @Override
            public void onResults(Bundle bundle) {
                String data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                if(data.equals(CustomUserAnswer.RETRY.toString())){
                    askCropColumn();
                    return;
                }

                try {
                    currentData = currentCropRow.validate(currentCropRow.getNextColumnName(), data);
                } catch (NotCorrectDataTypeException e) {
                    askRetry(e.getMessage());
                    return;
                } catch (NotNullDataTypeException e) {
                    askRetry(e.getMessage());
                    return;
                }
                if(data.equals(CustomUserAnswer.NONE.toString())){
                    saveData();
                    return;
                }

                askCheckData(currentData);
            }

            @Override
            public void onPartialResults(Bundle bundle) {

            }

            @Override
            public void onEvent(int i, Bundle bundle) {

            }
        };
    }

    private void initCheckRecognitionListener() {
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
                askRetry(currentData);
            }

            @RequiresApi(api = VERSION_CODES.N)
            @Override
            public void onResults(Bundle bundle) {
                String answer = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                if(answer.equals(CustomUserAnswer.SAVE.toString())){
                    saveData();
                    return;
                }

                if(answer.equals(CustomUserAnswer.NO.toString())){
                    askSayCropData();
                    return;
                }
                askCheckData(currentData);
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