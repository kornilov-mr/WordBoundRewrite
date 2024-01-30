package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;
import org.json.simple.JSONObject;

public class ChangeCardRequest implements Request {
    private final String bookName;
    private final String key;
    private final String deck;
    private final int id;
    private final String originalWord;
    private final String wordTranslation;

    public ChangeCardRequest(String bookName, String key, String deck, int id, String originalWord, String wordTranslation) {
        this.bookName = bookName;
        this.key = key;
        this.deck = deck;
        this.id = id;
        this.originalWord = originalWord;
        this.wordTranslation = wordTranslation;
    }
    @Override
    public String getRequest() {
        JSONObject data = new JSONObject();
        data.put("bookName",bookName);
        data.put("key", key);
        data.put("id", id);
        data.put("deck",deck);
        JSONObject cardData = new JSONObject();
        cardData.put("originalWord",originalWord);
        cardData.put("wordTranslation",wordTranslation);

        data.put("cardData",cardData);
        data.put("requestType","changeCardRequest");

        return data.toJSONString();
    }
}
