package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;
import org.json.simple.JSONObject;

public class AddBookRequest implements Request {
    private final String bookName;
    private final String realName;

    public AddBookRequest(String bookName, String realName) {
        this.bookName = bookName;
        this.realName = realName;
    }

    @Override
    public String getRequest() {
        JSONObject data = new JSONObject();
        data.put("bookName",bookName);
        data.put("requestType","addBookRequest");
        data.put("realName",realName);
        return data.toJSONString();
    }
}
