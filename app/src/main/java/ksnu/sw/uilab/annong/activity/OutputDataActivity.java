package ksnu.sw.uilab.annong.activity;

import static ksnu.sw.uilab.annong.utils.CsvUtils.clearCSV;
import static ksnu.sw.uilab.annong.utils.CsvUtils.getFullDataFromDir;
import static ksnu.sw.uilab.annong.utils.CsvUtils.removeLine;

import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Output;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.utils.CsvUtils;
import ksnu.sw.uilab.annong.utils.enums.BasicCropColumn;

public class OutputDataActivity extends AppCompatActivity {

    String crop_name;
    List<List<String>> data;
    int index = 1, max = 0;

    TextView textView_crop_name, textView_min, textView_max;
    Button btn_prev, btn_next, btn_delete_table, btn_delete_data, btn_download;
    FrameLayout output_data_frame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_data);

        Intent intent = getIntent();
        crop_name = intent.getStringExtra("crop_name");

        /* 데이터 불러오기 */
        output_data_frame = (FrameLayout) findViewById(R.id.output_data_frame);
        data = getFullDataFromDir(getApplicationContext(), crop_name);
        max = data.size()-1;
        Log.e("TAG", "맥스값이 "+max);


        textView_crop_name = (TextView) findViewById(R.id.textview_crop_name);
        textView_crop_name.setText(crop_name);

        textView_min = (TextView) findViewById(R.id.textView_min);
        textView_min.setText(String.valueOf(index));
        textView_max = (TextView) findViewById(R.id.textView_max);
        textView_max.setText(String.valueOf(max));

        if(max <1) {
            Toast toast = Toast.makeText(this.getApplicationContext(),"데이터가 존재하지 않습니다.", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        } else {
            call_data(index);
        }


        Button btn_prev = (Button) findViewById(R.id.btn_prev);
        btn_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_frame_view(index, "prev");
            }
        });

        Button btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change_frame_view(index, "next");
            }
        });

        /* 테이블 삭제 */
        btn_delete_table = (Button) findViewById(R.id.btn_delete_table);
        btn_delete_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(OutputDataActivity.this);
                dialog.setContentView(R.layout.dialog_view);

                TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
                TextView dialog_msg = (TextView) dialog.findViewById(R.id.dialog_msg);
                EditText dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);
                Button dialog_btn_no = (Button) dialog.findViewById(R.id.dialog_btn_no);
                Button dialog_btn_ok = (Button) dialog.findViewById(R.id.dialog_btn_ok);

                dialog_title.setText("주의! 테이블 삭제");
                dialog_msg.setText("작물 정보, 데이터 \n전부 삭제합니다.");
                dialog_text.setVisibility(View.GONE);
                dialog_btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearCSV(getApplicationContext(), crop_name);
                        makeTableHeader(data.get(0));
                        Log.e("TAG", data.get(0).toString());
                        dialog.dismiss();
                        finish();
                    }
                });

                dialog.show();
            }
        });


        /* 데이터 삭제 */
        btn_delete_data = (Button) findViewById(R.id.btn_delete_data);
        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(OutputDataActivity.this);
                dialog.setContentView(R.layout.dialog_view);

                TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
                TextView dialog_msg = (TextView) dialog.findViewById(R.id.dialog_msg);
                EditText dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);
                Button dialog_btn_no = (Button) dialog.findViewById(R.id.dialog_btn_no);
                Button dialog_btn_ok = (Button) dialog.findViewById(R.id.dialog_btn_ok);

                dialog_title.setText("주의! 데이터 삭제");
                dialog_msg.setText("데이터를 삭제합니다.");
                dialog_text.setVisibility(View.GONE);
                dialog_btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("TAG", "index는?? " +index);
                        data.remove(index);
                        index--;
                        max--;
                        textView_min.setText(String.valueOf(index));
                        textView_max.setText(String.valueOf(max));
                        call_data(index);

                        /* 파일 쓰기 */
                        removeLine(getApplicationContext(), crop_name, index);

                        dialog.dismiss();
                    }
                });

                dialog.show();


            }
        });

        /* 데이터 내보내기 */
        btn_download = (Button) findViewById(R.id.btn_download);
        btn_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(OutputDataActivity.this, DownloadCsvActivity.class);
                intent1.putExtra("file_path", crop_name);
                startActivity(intent1);
                finish();
            }
        });

    }

    void change_frame_view(int index, String button) {
        if(button.equals("prev")) {
            if(index == 1) {
                index = max;
            } else {
                index--;
            }
        } else if(button.equals("next")) {
            if(index == max) {
                index = 1;
            } else {
                index++;
            }
        }

        textView_min.setText(String.valueOf(index));
        call_data(index);
    }


    /* 전체 불러오기 */
//    void call_data() {
//        for(int i=1; i<data.size(); i++) {
//            ScrollView scrollView = (ScrollView) LayoutInflater.from(this).inflate(R.layout.add_output_data_table, null);
//            TableLayout tableLayout = (TableLayout) scrollView.findViewById(R.id.tableLayout_output_data);
//
//            for(int j=0; j<data.get(0).size(); j++) {
//                TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.add_table_data_row, null);
//                ((TextView) tableRow.getChildAt(0)).setText(data.get(0).get(j));
//                ((TextView) tableRow.getChildAt(1)).setText(data.get(i).get(j));
//                tableLayout.addView(tableRow);
//            }
//
//            scrollView.setId(123456789 +i);
//            output_data_frame.addView(scrollView);
//        }
//    }


    /* 요청한 값만 */
    void call_data(int index) {
        this.index = index;
        output_data_frame.removeAllViews();

        ScrollView scrollView = (ScrollView) LayoutInflater.from(this).inflate(R.layout.add_output_data_table, null);
        TableLayout tableLayout = (TableLayout) scrollView.findViewById(R.id.tableLayout_output_data);

        for(int j=0; j<data.get(0).size(); j++) {
            TableRow tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.add_table_data_row, null);
            ((TextView) tableRow.getChildAt(0)).setText(data.get(0).get(j));
            ((TextView) tableRow.getChildAt(1)).setText(data.get(index).get(j));
            tableLayout.addView(tableRow);
        }

        output_data_frame.addView(scrollView);
    }

    private void makeTableHeader(List<String> column){
        appendTableHeader(BasicCropColumn.MEASUREMENT_DATE.toString());

        for(String rowMeta: column){
            appendTableHeader(rowMeta);
        }
        CsvUtils.writeNewLine(this,crop_name);
    }

    private void appendTableHeader(String data) {
        CsvUtils.writeCsvData(this, crop_name, data);
    }

}