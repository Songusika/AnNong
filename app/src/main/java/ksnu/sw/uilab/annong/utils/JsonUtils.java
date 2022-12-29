package ksnu.sw.uilab.annong.utils;

import android.content.Context;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class JsonUtils  {
    private static final Gson gson = new Gson();
    private static final String jsonFIleExtension = ".json";

    public static <T> void writeJsonData(Context context, String fileName, T instance){
        BufferedWriter jsonBw = FileUtils.openInternalFileWriter(context, fileName+jsonFIleExtension, context.MODE_PRIVATE);
        try{
            jsonBw.write(gson.toJson(instance));
            jsonBw.flush();
            jsonBw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static <T> T getInstanceFromJson(Context context, String fileName, Class<T> parsingClass){
        BufferedReader jsonBr = FileUtils.openInternalFileReader(context, fileName+jsonFIleExtension);
        try{
            return gson.fromJson(jsonBr.readLine(), parsingClass);
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
