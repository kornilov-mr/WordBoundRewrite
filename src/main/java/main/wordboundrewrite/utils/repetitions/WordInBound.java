package main.wordboundrewrite.utils.repetitions;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.json.simple.JSONObject;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordInBound {
    private final Map<Long, Long> repeats= Stream.of(new long[][]{
            {-1, 20*60*1000},
            {0, 20*60*1000},
            {1, 24*60*60*1000},
            {2, 3*24*60*60*1000},
            {3, 7*24*60*60*1000},
            {4, 3*7*24*60*60*1000},
            {5, 2*4*7*24*60*60*1000},
            {6, 8*4*7*24*60*60*1000},
            {7, 12*4*7*24*60*60*1000},
    }).collect(Collectors.toMap(data -> data[0], data -> data[1]));
    public long realTime;
    public long nextrepeat;
    public String deck;
    public String originalWord;
    public String timeInyyyymmdd;
    public String wordTranslation;
    public String context;
    public long id;
    public String key;
    public String bookName;
    protected long repeatCount;
    public WordInBound(long realTime, long nextrepeat, String deck, String originalWord,String timeInyyyymmdd,String wordTranslation,long repeatCount,long id,String key,String context, String bookName){
        this.realTime=realTime;
        this.nextrepeat=nextrepeat;
        this.deck=deck;
        this.originalWord=originalWord;
        this.timeInyyyymmdd=timeInyyyymmdd;
        this.wordTranslation=wordTranslation;
        this.repeatCount=repeatCount;
        this.id=id;
        this.key=key;
        this.context=context;
        this.bookName= bookName;
    }
    public void getGood(){
        repeatCount+=1;
        nextrepeat=System.currentTimeMillis()+repeats.get(repeatCount);
    }
    public void setAgain(){
        if(repeatCount!=-1){
            repeatCount=0;
        }
        nextrepeat=System.currentTimeMillis()+repeats.get(repeatCount);
    }
    public String createTimeUpdateRequest(){
        JSONObject data = new JSONObject();
        data.put("bookName",bookName);
        data.put("key", key);
        data.put("id", id);
        data.put("deck",deck);
        JSONObject cardData = new JSONObject();
        cardData.put("repeatCount",repeatCount);
        cardData.put("nextrepeat",nextrepeat);

        data.put("cardData",cardData);
        data.put("requestType","updataTimeRequest");

        return data.toJSONString();
    }
    public JSONObject toJson(){
        org.json.simple.JSONObject wordInfo= new org.json.simple.JSONObject();
        wordInfo.put("originalWord",originalWord);
        wordInfo.put("wordTranslation",wordTranslation);

        wordInfo.put("time",timeInyyyymmdd);
        wordInfo.put("realTime",realTime);
        wordInfo.put("nextrepeat",nextrepeat);
        wordInfo.put("id",id);
        wordInfo.put("deck",deck);
        wordInfo.put("key",key);

        wordInfo.put("repeatCount",repeatCount);
        return wordInfo;
    }
    public StringProperty originalWordProperty() {
        return new SimpleStringProperty(originalWord);
    }
    public StringProperty wordTranslationProperty() {
        return  new SimpleStringProperty(wordTranslation);
    }
    public StringProperty idProperty(){
        return new SimpleStringProperty(String.valueOf( id));
    }
    public StringProperty contextProperty(){
        return new SimpleStringProperty(context);
    }
    public StringProperty keyProperty(){
        return new SimpleStringProperty(key);
    }
    public StringProperty deckProperty(){
        return new SimpleStringProperty(deck);
    }
}
