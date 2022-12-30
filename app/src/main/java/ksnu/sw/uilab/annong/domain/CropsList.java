package ksnu.sw.uilab.annong.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ksnu.sw.uilab.annong.utils.CsvUtils;
import ksnu.sw.uilab.annong.utils.enums.AppResourceName;
import ksnu.sw.uilab.annong.utils.enums.ApplicationConstants;

public class CropsList {
    private static CropsList instance = new CropsList();
    private static final int NEW_CROP_SELECT_INDEX = 0;
    private List<String> cropsList;
    private Context context;

    private CropsList(){
    }

    public static CropsList getInstance(){
        return instance;
    }

    public CropsList initTableSettingItems(Context context){
        this.context = context;

        initCropsList();
        initFirstItem();
        return this;
    }

    public CropsList initItem(Context context){
        this.context = context;

        initCropsList();
        return this;
    }

    private void initCropsList(){
        try{
            this.cropsList = CsvUtils.getFullDataFromDir(context, AppResourceName.CROPS_LIST_FILE_NAME.getValue()).get(0);
        }catch (IndexOutOfBoundsException e){
            this.cropsList = new ArrayList<>();
        }
    }

    private void initFirstItem(){
        cropsList.add(NEW_CROP_SELECT_INDEX, ApplicationConstants.MAKE_NEW_CROP_SELECT_MESSAGE.toString());
    }

    /**
     * @return 작물 이름이 담긴 문자열 배열
     */
    public List<String> getCropsList(){
        return this.cropsList;
    }

    /**
     * 새로운 작물을 작물 목록에 추가,
     * 기존의 작물과 이름이 중복될 경우 exception 발생
     * @param cropsName 새로운 작물 이름
     */
    public void addNewCrops(String cropsName){
        CsvUtils.writeCsvData(context, AppResourceName.CROPS_LIST_FILE_NAME.getValue(), cropsName);
        updateCropsList();
    }

    private void updateCropsList(){
        this.cropsList = CsvUtils.getFullDataFromDir(context, AppResourceName.CROPS_LIST_FILE_NAME.getValue()).get(0);
        initFirstItem();
    }
}
