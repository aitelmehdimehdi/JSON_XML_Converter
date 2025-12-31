package com.example.javafx.Files;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileOpener {
    public static JSONObject readJsonFile(FileReader file) throws IOException {
        StringBuffer stringBuffer=new StringBuffer();
        String str;
        BufferedReader bufferedReader = new BufferedReader(file);
        while ((str=bufferedReader.readLine())!=null)
            stringBuffer.append(str);
        return new JSONObject(stringBuffer.toString());
    }
    public static String readXMLFile(FileReader file) throws IOException {
        StringBuffer stringBuffer=new StringBuffer();
        String str;
        BufferedReader bufferedReader = new BufferedReader(file);
        while ((str=bufferedReader.readLine())!=null)
            stringBuffer.append(str);
        return stringBuffer.toString();
    }
}
