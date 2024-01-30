package main.wordboundrewrite.utils.dataHandlers.server.send.requests.DailyReportRequest;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;
import org.json.simple.JSONObject;

public class AddDailyReportRequest implements Request {
    private final String dtfTime;
    private final int totalWordRead;
    private final int totalWordRepeat;
    private final int totalNewWord;

    public AddDailyReportRequest(String dtfTime, int totalWordRead, int totalWordRepeat, int totalNewWord) {
        this.dtfTime = dtfTime;
        this.totalWordRead = totalWordRead;
        this.totalWordRepeat = totalWordRepeat;
        this.totalNewWord = totalNewWord;
    }

    @Override
    public String getRequest() {
        JSONObject reportdata= new JSONObject();
        reportdata.put("dtfTime",dtfTime);
        reportdata.put("totalWordRead",totalWordRead);
        reportdata.put("totalWordRepeat",totalWordRepeat);
        reportdata.put("totalNewWord",totalNewWord);
        return reportdata.toJSONString();
    }
}
