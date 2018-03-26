package indi.zhuyst.wxrun_demo.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Template implements Serializable{

    @JsonProperty("touser")
    private String toUser;

    @JsonProperty("template_id")
    private String templateId;

    @JsonProperty("form_id")
    private String formId;

    private Map<String,Value> data;

    public void putData(String keyword,String value){
        if(data == null){
            data = new HashMap<>();
        }
        Value val = new Value();
        val.setValue(value);
        data.put(keyword,val);
    }

    @Data
    private class Value implements Serializable{
        private String value;

        private String color;
    }
}
