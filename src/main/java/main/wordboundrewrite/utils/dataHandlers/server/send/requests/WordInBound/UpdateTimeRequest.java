package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;
import main.wordboundrewrite.utils.repetitions.WordInBound;

public class UpdateTimeRequest implements Request {
    private final WordInBound word;
    public UpdateTimeRequest(WordInBound word){
        this.word=word;
    }
    @Override
    public String getRequest() {
        return word.createTimeUpdateRequest();
    }
}
