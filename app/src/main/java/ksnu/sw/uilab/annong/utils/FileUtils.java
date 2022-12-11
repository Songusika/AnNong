package ksnu.sw.uilab.annong.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ksnu.sw.uilab.annong.utils.enums.AppResourceName;

public class FileUtils {

    /**
     * 파일을 열어 BufferedReader 반환 - 파일이 없을 경우 생성 후 반환
     * @param appResourceName - Open 할 파일 경로
     * @return BufferedReader
     */
    public static BufferedReader openInternalFileReader(Context context, AppResourceName appResourceName){
        try {
            return getInternalFileBufferedReader(context, appResourceName);
        }catch (FileNotFoundException e){
            e.printStackTrace();

            generateNewFile(context, appResourceName);
            return openInternalFileReader(context, appResourceName);
        }
    }

    private static BufferedReader getInternalFileBufferedReader(Context context, AppResourceName appResourceName) throws FileNotFoundException{
        FileInputStream fin = context.openFileInput(appResourceName.getResourceName());
        InputStreamReader in = new InputStreamReader(fin);

        BufferedReader br = new BufferedReader(in);
        return br;
    }

    /**
     * 빈 CSV 파일 생성
     * @param appResourceName - 생성할 CSV 경로
     */
    private static void generateNewFile(Context context, AppResourceName appResourceName){
        try{
            FileOutputStream fos = context.openFileOutput(appResourceName.getResourceName(), context.MODE_APPEND);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static BufferedWriter openInternalFileWriter(Context context, AppResourceName appResourceName){
        try{
            return getInternalFileBufferedWriter(context, appResourceName);
        }catch (FileNotFoundException e){
            e.printStackTrace();

            generateNewFile(context, appResourceName);
            return openInternalFileWriter(context, appResourceName);
        }
    }

    private static BufferedWriter getInternalFileBufferedWriter(Context context, AppResourceName appResourceName) throws FileNotFoundException{
        FileOutputStream fos = context.openFileOutput(appResourceName.getResourceName(), context.MODE_APPEND);
        OutputStreamWriter os = new OutputStreamWriter(fos);
        BufferedWriter br = new BufferedWriter(os);

        return br;
    }
}
