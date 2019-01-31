package edu.neu.coe.csye6225.util;

import org.json.JSONObject;

public class ResultJson {

    private JSONObject jsonObject;

    public ResultJson(){
        jsonObject = new JSONObject();
    }

    public void put (String key, String value){
        jsonObject.put(key, value);
    }

    @Override
    public String toString() {

//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("{");
//
//        for (Map.Entry<String, String> entry : pairs.entrySet()) {
//            stringBuilder.append("\"").append(entry.getKey()).append("\"")
//                    .append(":").append("\"").append(entry.getValue()).append("\"");
//
//        }
//        stringBuilder.append("}");

        return jsonObject.toString();
    }
}
