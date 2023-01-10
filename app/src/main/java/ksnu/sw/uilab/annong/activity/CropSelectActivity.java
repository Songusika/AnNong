package ksnu.sw.uilab.annong.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import ksnu.sw.uilab.annong.R;
import ksnu.sw.uilab.annong.domain.CropMeta;
import ksnu.sw.uilab.annong.domain.CropsList;
import ksnu.sw.uilab.annong.utils.JsonUtils;
import ksnu.sw.uilab.annong.utils.enums.Extras;

public class CropSelectActivity extends AppCompatActivity {

    int startPage;
    ListView listView_crop_name;
    Button btn_add_new_crop;

    CropsList cropItems;
    String crop_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_crop_name);

        Intent intent = getIntent();
        startPage = intent.getIntExtra("startPage", 1);
        Log.e("TAG", "start page>> " +startPage);

        /* 작물 리스트 불러오기 */
        listView_crop_name = (ListView) findViewById(R.id.listView_crop_name);
        List<String> crop_name_list = new ArrayList<>();

        cropItems = CropsList.getInstance().initItem(this);
        crop_name_list = cropItems.getCropsList();

        ArrayAdapter arrayAdapter = new ArrayAdapter(CropSelectActivity.this, R.layout.listview_style, crop_name_list);
        listView_crop_name.setAdapter(arrayAdapter);


        /* 작물 추가 */
        btn_add_new_crop = (Button) findViewById(R.id.btn_add_new_crop);
        if (startPage == 1) {
            btn_add_new_crop.setVisibility(View.VISIBLE);
        } else if (startPage == 2){
            btn_add_new_crop.setVisibility(View.GONE);
        }else if (startPage == 3){
            btn_add_new_crop.setVisibility(View.GONE);
        }
        btn_add_new_crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(CropSelectActivity.this);
                dialog.setContentView(R.layout.dialog_view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView dialog_title = (TextView) dialog.findViewById(R.id.dialog_title);
                TextView dialog_msg = (TextView) dialog.findViewById(R.id.dialog_msg);
                EditText dialog_text = (EditText) dialog.findViewById(R.id.dialog_text);
                Button dialog_btn_no = (Button) dialog.findViewById(R.id.dialog_btn_no);
                Button dialog_btn_ok = (Button) dialog.findViewById(R.id.dialog_btn_ok);

                dialog_title.setText("작물 추가");
                dialog_msg.setText("추가하실 작물명을 입력하세요");
                dialog_text.setText("");
                dialog_btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog_btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String new_crop_name = dialog_text.getText().toString();
                        if(!new_crop_name.equals("")) {
                            addNewCropItem(new_crop_name);
                            initNewCropMeta(new_crop_name);

                            goNextPage(new_crop_name);
                        }
                    }
                });

                dialog.show();
            }
        });

        /* 작물 선택 */
        listView_crop_name.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                crop_name = adapterView.getItemAtPosition(i).toString();
                Log.e("TAG", "작물명>> " +crop_name);

                goNextPage(crop_name);
            }
        });

    }

    private void addNewCropItem(String newCropItem){
        cropItems.addNewCrops(newCropItem);
    }

    private void initNewCropMeta(String newCropName){
        JsonUtils.writeJsonData(this, newCropName, new CropMeta(newCropName));
    }

    private void goNextPage(String crop_name) {
        this.crop_name = crop_name;
        Log.e("TAG", "goNextPage");

        Class nextPage = null;
        if(startPage == 1) {
            nextPage = TableSettingActivity.class;
        } else if(startPage == 2) {
            nextPage = InputDataActivity.class;
        } else if(startPage == 3) {
            nextPage = OutputDataActivity.class;
        }

        Intent intent = new Intent(CropSelectActivity.this, nextPage);
        intent.putExtra("crop_name", crop_name);
        intent.putExtra(Extras.CROP_NAME_KEY.getKey(), crop_name);
        startActivity(intent);
        finish();
    }
}