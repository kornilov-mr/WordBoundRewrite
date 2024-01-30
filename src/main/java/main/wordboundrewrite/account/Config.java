package main.wordboundrewrite.account;

import org.json.simple.JSONObject;

public class Config {
    public  long fontSize;
    public long lastId;
    public Config() {

    }
    public JSONObject createJson(){
        JSONObject data = new JSONObject();
        data.put("fontSize",fontSize);
        data.put("lastId",lastId);
        return data;
    }
}
