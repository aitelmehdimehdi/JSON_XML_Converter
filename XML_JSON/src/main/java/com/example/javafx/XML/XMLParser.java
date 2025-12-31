package com.example.javafx.XML;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import java.lang.*;
import java.util.Objects;


public class XMLParser {

    static JSONObject jsonFormat;
    static String XMLFormat;


    public static String toXML(String jsonObject){
        jsonFormat = new JSONObject(jsonObject);
        XMLFormat = XML.toString(jsonFormat);
        return XMLFormat;
    }

    public static String toXMLv2(JSONObject jsonObject ){
        return XMLParser.toXMLv2(new StringBuffer(""),jsonObject);
    }

    public static String toXMLv2(StringBuffer xmlFormat , JSONObject jsonObject ){
        for(Object key : jsonObject.keySet()){
            if (jsonObject.get(key.toString()).toString().startsWith("{") && jsonObject.get(key.toString()).toString().endsWith("}")){
                xmlFormat.append("<").append(key.toString()).append(">");
                XMLParser.toXMLv2(xmlFormat,new JSONObject(jsonObject.get(key.toString()).toString()));
                xmlFormat.append("</").append(key.toString()).append(">");
            }
            else if (jsonObject.get(key.toString()).toString().startsWith("[") && jsonObject.get(key.toString()).toString().endsWith("]")){
                JSONArray array = new JSONArray(jsonObject.get(key.toString()).toString());
                for (int i=0 ; i< array.length() ; i++){
                    if(array.get(i).toString().startsWith("{") && array.get(i).toString().endsWith("}")){
                        xmlFormat.append("<").append(key.toString()).append(">");
                        XMLParser.toXMLv2(xmlFormat,new JSONObject(array.get(i).toString()));
                        xmlFormat.append("</").append(key.toString()).append(">");
                    }
                    else {
//                        xmlFormat.append("<").append(key.toString()).append(">");
//                        xmlFormat.append(array.get(i).toString());
//                        xmlFormat.append("</").append(key.toString()).append(">");
                        simpleReplacement(key.toString(), jsonObject, xmlFormat);
                    }
                }
            }
            else {
//                xmlFormat.append("<").append(key.toString()).append(">");
//                xmlFormat.append(jsonObject.get(key.toString()));
//                xmlFormat.append("</").append(key.toString()).append(">");
                simpleReplacement(key.toString(), jsonObject, xmlFormat);
            }
        }
        XMLFormat = xmlFormat.toString();
        return XMLFormat;
    }

    public static void simpleReplacement(String key, JSONObject jsonObject, StringBuffer xmlFormat){
        if (!Objects.equals(jsonObject.get(key).toString().toLowerCase(), "null")){
            xmlFormat.append("<").append(key).append(">");
            xmlFormat.append(jsonObject.get(key));
            xmlFormat.append("</").append(key).append(">");
        }
        else {
            xmlFormat.append("<").append(key).append(">");
            xmlFormat.append("</").append(key).append(">");
        }
    }

}

