package com.example.javafx.JSON;

import org.json.JSONObject;
import org.json.XML;
import org.json.XMLTokener;

import java.util.Objects;
import java.util.Stack;

public class JSONParser{

    static JSONObject jsonFormat;
    static String XMLFormat;
    static XMLTokener xmlTokener;

    public static JSONObject toJSON(String xmlText){
        XMLFormat = xmlText;
        jsonFormat = XML.toJSONObject(xmlText);
        return jsonFormat;
    }

    public static JSONObject toJSONv2(String xmlText){
        xmlTokener=new XMLTokener(xmlText);
        XMLFormat=xmlText;
        System.out.println(xmlTokener.nextContent());
        return toJSONv2(new StringBuffer("{"));
    }

    public static JSONObject toJSONv2(StringBuffer jsonText){
        Object tmp;
        String[] tmp3,tmp4;
        Stack<String> lastIndex =new Stack<>();
        while (xmlTokener.more()) {
            //System.out.println("i");
            if((tmp=xmlTokener.nextContent())!=null) {
                if (Objects.equals(tmp.toString(), "<")) {
                    if(lastIndex.isEmpty()){
                        jsonText.append("{");
                        lastIndex.addLast("<");
                    }
                    else if(Objects.equals(lastIndex.getLast(), "</")){
                        if(jsonText.charAt(jsonText.length()-1)==','){
                            lastIndex.addLast("<");
                        }
                        else {
                            jsonText.append(",");
                            lastIndex.addLast("<");
                        }
                    } else{
                        jsonText.append("{");
                        lastIndex.addLast("<");
                    }
                }
                else{
                    if (tmp.toString().startsWith("?")){
                        jsonText.deleteCharAt(jsonText.length()-1);
                        continue;
                    }
                    else if(tmp.toString().startsWith("/")){
                        if(lastIndex.getLast()=="</" || lastIndex.get(lastIndex.size()-2)=="</"){
                            if(jsonText.charAt(jsonText.length()-1)==','){
                                jsonText.deleteCharAt(jsonText.length()-1);
                                jsonText.append("}");
                                lastIndex.addLast("</");
                            }
                            else {
                                jsonText.append("}");
                                lastIndex.addLast("</");
                            }
                        }
                        else if (lastIndex.getLast()=="<") {
                            if(jsonText.charAt(jsonText.length()-1)=='{'){
                                jsonText.deleteCharAt(jsonText.length()-1);
                                jsonText.append(",");
                                lastIndex.removeLast();
                                lastIndex.addLast("</");
                                System.out.println("---");
                            }
                        }
                    }
                    else{
                        tmp3 = tmp.toString().split(">");
                        if(tmp3.length<2){
                            if(tmp3[0].split(" ").length < 2){
                                jsonText.append('"'+tmp3[0]+'"').append(":");
                            }
                            else{
                                tmp4 = tmp3[0].split(" ");
                                jsonText.append('"'+tmp4[0]+'"'+" : {");
                                for(int i=1 ; i<tmp4.length ; i++){
                                    String[] tmp5 = tmp4[i].split("=");
                                    jsonText.append("\"_").append(tmp5[0]).append('"').append(":").append(tmp5[1]).append(',');
                                }
                                lastIndex.addLast("</");
                            }
                        }
                        else{
                            if(tmp3[0].split(" ").length < 2){
                                jsonText.append('"'+tmp3[0]+'"').append(":").append('"'+tmp3[1]+'"');
                            }
                            else{
                                tmp4 = tmp3[0].split(" ");
                                jsonText.append('"'+tmp4[0]+'"'+" : {");
                                for(int i=1 ; i<tmp4.length ; i++){
                                    String[] tmp5 = tmp4[i].split("=");
                                    jsonText.append("\"_").append(tmp5[0]).append('"').append(":").append(tmp5[1]).append(',');
                                }
                                jsonText.append("\"_text\" : \"").append(tmp3[1]).append("\"}");
                            }

                        }
                    }
                }
            }
        }
        jsonText.append("}");
        System.out.println(lastIndex);
        System.out.println("json : "+jsonText);
        jsonFormat=new JSONObject(jsonText.toString());
        return jsonFormat;
    }
}

