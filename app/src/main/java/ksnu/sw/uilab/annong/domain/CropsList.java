package ksnu.sw.uilab.annong.domain;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ksnu.sw.uilab.annong.utils.CSVUtils;
import ksnu.sw.uilab.annong.utils.enums.AppResourceName;
import ksnu.sw.uilab.annong.utils.enums.ApplicationMessage;

public class CropsList {
    private static CropsList instance = new CropsList();
    private List<String> cropsList;
    private Context context;

    private CropsList(){
    }

    public static CropsList getInstance(){
        return instance;
    }

    public CropsList initItems(Context context){
        this.context = context;

        initCropsList();
        initFirstItem();
        return this;
    }

    private void initCropsList(){
        try{
            this.cropsList = CSVUtils.getFullDataFromDir(context, AppResourceName.CROPS_LIST_DIRECTORY).get(0);
        }catch (IndexOutOfBoundsException e){
            this.cropsList = new ArrayList<>();
        }
    }

    private void initFirstItem(){
        cropsList.add(0, ApplicationMessage.MAKE_NEW_CROP_SELECT_MESSAGE.getMessage());
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
        CSVUtils.writeCsvData(context, AppResourceName.CROPS_LIST_DIRECTORY, cropsName);
        updateCropsList();
    }

    private void updateCropsList(){
        this.cropsList = CSVUtils.getFullDataFromDir(context, AppResourceName.CROPS_LIST_DIRECTORY).get(0);
        initFirstItem();
    }
}
