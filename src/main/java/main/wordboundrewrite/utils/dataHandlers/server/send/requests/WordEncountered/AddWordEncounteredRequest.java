package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordEncountered;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;

public class AddWordEncounteredRequest implements Request {
    private final String word;

    public AddWordEncounteredRequest(String word) {
        this.word = word;
    }

    @Override
    public String getRequest() {
        return word;
    }
}
