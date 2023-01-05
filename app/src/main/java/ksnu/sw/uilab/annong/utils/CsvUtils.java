package ksnu.sw.uilab.annong.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ksnu.sw.uilab.annong.utils.enums.AppResourceExtensions;
import ksnu.sw.uilab.annong.utils.enums.AppResourceName;

public class CsvUtils {
    private static final String CSV_SPLIT_COMMA = ",";


    public static List<List<String>> getFullDataFromDir(Context context, String fileName){
        BufferedReader csvBr = FileUtils.openInternalFileReader(context, fileName+ AppResourceExtensions.CSV.getFileExtension());

        return readAllCsvLine(csvBr);
    }

    public static void writeCsvData(Context context, String fileName, String data){
        BufferedWriter csvWr = FileUtils.openInternalFileWriter(context, fileName+AppResourceExtensions.CSV.getFileExtension(), context.MODE_APPEND);
        try{
            writeAndCloseBufferedWriter(csvWr, data+CSV_SPLIT_COMMA);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void writeNewLine(Context context, String fileName){
        BufferedWriter csvWr = FileUtils.openInternalFileWriter(context, fileName+AppResourceExtensions.CSV.getFileExtension(), context.MODE_APPEND);
        try{
            csvWr.newLine();
            csvWr.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void removeLine(Context context, String fileName, int lineIndex){
        List<List<String>> fileContents = getFullDataFromDir(context, fileName);

        fileContents.remove(lineIndex);

        for(List<String> row: fileContents){
            for(String data: row){
                writeCsvData(context, fileName, data);
            }
            writeNewLine(context, fileName);
        }
    }

    private static void writeAndCloseBufferedWriter(BufferedWriter csvWr, String data) throws IOException{
        csvWr.write(data);
        csvWr.flush();
        csvWr.close();
    }

    private static List<List<String>> readAllCsvLine(BufferedReader br){
        List<List<String>> allLine = new ArrayList<>();

        try{
            String line;
            while((line=br.readLine()) != null){
                allLine.add(readCsvLine(line));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return allLine;
    }

    private static List<String> readCsvLine(String csvLine){
        List<String> line = new ArrayList<>();
        String[] dataSplitByComma = csvLine.split(CSV_SPLIT_COMMA);

        Collections.addAll(line, dataSplitByComma);

        return line;
    }
}
