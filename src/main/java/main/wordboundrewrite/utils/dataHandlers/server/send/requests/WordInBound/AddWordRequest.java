package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;
import main.wordboundrewrite.utils.repetitions.WordInBound;
import org.json.simple.JSONObject;

public class AddWordRequest implements Request {
    private WordInBound word;
    public AddWordRequest(WordInBound word){
        this.word=word;
    }

    @Override
    public String getRequest() {
        JSONObject data = new JSONObject();
        data.put("bookName",word.bookName);
        data.put("key", word.key);
        data.put("id", word.id);
        data.put("deck",word.deck);
        data.put("cardData",word.toJson().toJSONString());
        data.put("requestType","addWordRequest");

        return data.toJSONString();
    }
}
