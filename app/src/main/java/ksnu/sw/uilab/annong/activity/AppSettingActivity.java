package ksnu.sw.uilab.annong.activity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ksnu.sw.uilab.annong.R;

public class AppSettingActivity extends AppCompatActivity {

    TextView textView_measure;
    EditText editText_measure;
    Button btn_measure, btn_back;

    String file_name, measure_time, wait_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);

        measure_time = "10";
        wait_time = "3";
        file_name = "setting_time.txt";
        read_setting_time();

        textView_measure = (TextView) findViewById(R.id.textView_measure);
        editText_measure = (EditText) findViewById(R.id.editText_measure);
        textView_measure.setText(measure_time);

        btn_measure = (Button) findViewById(R.id.btn_measure);
        btn_measure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                measure_time = String.valueOf(editText_measure.getText());

                textView_measure.setText(measure_time);
                editText_measure.setText("");

                write_setting_time();
            }
        });

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        /* 만든이 링크 */
        Linkify.TransformFilter transformFilter = new Linkify.TransformFilter() {
            @Override
            public String transformUrl(Matcher matcher, String s) {
                return "";
            }
        };

        TextView textView1 = (TextView) findViewById(R.id.textView1);
        Pattern pattern1 = Pattern.compile(textView1.getText().toString());
        Linkify.addLinks(textView1, pattern1, "https://github.com/song-wooseok/", null, transformFilter);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        Pattern pattern2 = Pattern.compile(textView2.getText().toString());
        Linkify.addLinks(textView2, pattern2, "https://github.com/dev-seonmi/", null, transformFilter);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        Pattern pattern3 = Pattern.compile(textView3.getText().toString());
        Linkify.addLinks(textView3, pattern3, "https://github.tistory.com/", null, transformFilter);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        Pattern pattern4 = Pattern.compile(textView4.getText().toString());
        Linkify.addLinks(textView4, pattern4, "https://sogogi1000inbun.tistory.com/", null, transformFilter);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        Pattern pattern5 = Pattern.compile(textView5.getText().toString());
        Linkify.addLinks(textView5, pattern5, "https://tpdbs1004.tistory.com/", null, transformFilter);
        TextView textView6 = (TextView) findViewById(R.id.textView6);
        Pattern pattern6 = Pattern.compile(textView6.getText().toString());
        Linkify.addLinks(textView6, pattern6, "https://ksm2853305.tistory.com/", null, transformFilter);


    }

    void read_setting_time() {
        try {
            File file = new File(getFilesDir(), file_name);

            if(file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                measure_time = bufferedReader.readLine();
                wait_time = bufferedReader.readLine();

                bufferedReader.close();
                fileReader.close();
            } else {
                write_setting_time();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void write_setting_time() {
        try {
            File file = new File(getFilesDir(), file_name);
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(measure_time + "\n" + wait_time);

            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}