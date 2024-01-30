package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;
import org.json.simple.JSONObject;

public class AddDeckRequest implements Request {
    private String bookName;
    private String deck;
    public AddDeckRequest(String bookName, String deck){
        this.bookName=bookName;
        this.deck=deck;
    }
    @Override
    public String getRequest() {
        JSONObject data = new JSONObject();
        data.put("bookName",bookName);
        data.put("deck",deck);
        data.put("requestType","addDeckRequest");

        return data.toJSONString();
    }
}
