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
    public static BufferedReader openInternalFileReader(Context context, String appResourceName){
        try {
            return getInternalFileBufferedReader(context, appResourceName);
        }catch (FileNotFoundException e){
            e.printStackTrace();

            generateNewFile(context, appResourceName);
            return openInternalFileReader(context, appResourceName);
        }
    }

    private static BufferedReader getInternalFileBufferedReader(Context context, String appResourceName) throws FileNotFoundException{
        FileInputStream fin = context.openFileInput(appResourceName);
        InputStreamReader in = new InputStreamReader(fin);

        BufferedReader br = new BufferedReader(in);
        return br;
    }

    /**
     * 빈 파일 생성
     * @param appResourceName - 생성할 파일 이름
     */
    private static void generateNewFile(Context context, String appResourceName){
        try{
            FileOutputStream fos = context.openFileOutput(appResourceName, context.MODE_APPEND);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static BufferedWriter openInternalFileWriter(Context context, String appResourceName, int mode){
        try{
            return getInternalFileBufferedWriter(context, appResourceName, mode);
        }catch (FileNotFoundException e){
            e.printStackTrace();

            generateNewFile(context, appResourceName);
            return openInternalFileWriter(context, appResourceName, mode);
        }
    }

    private static BufferedWriter getInternalFileBufferedWriter(Context context, String appResourceName, int mode) throws FileNotFoundException{
        FileOutputStream fos = context.openFileOutput(appResourceName, mode);
        OutputStreamWriter os = new OutputStreamWriter(fos);
        BufferedWriter br = new BufferedWriter(os);

        return br;
    }
}
