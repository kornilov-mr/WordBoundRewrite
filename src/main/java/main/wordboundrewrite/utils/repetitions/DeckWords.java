package main.wordboundrewrite.utils.repetitions;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.util.Pair;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound.UpdateTimeRequest;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound.WordInBoundRequestFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class DeckWords {
    public String deckName;
    public String bookName;
    public String realBookName;
    public Vector<WordInBound> deck;
    public DeckIndicator indicator;
    public DeckWords(Vector<WordInBound> deck,String deckName,String bookName, String realBookName){
        this.realBookName=realBookName;
        this.deck=deck;
        this.deckName=deckName;
        this.bookName=bookName;
        ShowOrder();

    }
    public void ShowOrder(){
        Collections.sort(deck,new SortByNextRepeat());
    }
    public void saveOrder(){ Collections.sort(deck,new SortById());}
    public WordInBound getNextWord(){

        long time= System.currentTimeMillis();
        WordInBound firstWord=deck.getFirst();
        if(time<firstWord.nextrepeat){
            return new WordInBound(-1,-1,firstWord.deck,"-1","-1","-1",0,-1,"first","null","null");
        }else{
            return deck.getFirst();
        }
    }
    public void getGoodWord(){
        deck.getFirst().getGood();
        WordInBoundRequestFactory.addRequest(new UpdateTimeRequest(deck.getFirst()));
        ShowOrder();
    }
    public void setAgainWord(){
        deck.getFirst().setAgain();
        WordInBoundRequestFactory.addRequest(new UpdateTimeRequest(deck.getFirst()));
        ShowOrder();
    }

    public DeckIndicator getIndicator(){
        int red=0;
        int blue=0;
        int green=0;
        for(int i=0;i<deck.size();i++){
            WordInBound currWord= deck.get(i);
            if(currWord.repeatCount==-1){
                red++;
            }else if(currWord.nextrepeat<System.currentTimeMillis()){
                blue++;
            }else{
                green++;
            }
        }
        DeckIndicator indicator= new DeckIndicator(red,blue,green);
        return indicator;
    }
    public FlowPane createFlowPaneForDeck(DeckIndicator indicator){
        FlowPane flow = new FlowPane(Orientation.VERTICAL);
        this.indicator=indicator;
        flow.setHgap(200);
        flow.setMaxHeight(45);
        flow.setMaxWidth(800);
        Label deckNameLabel= new Label(this.deckName);
        deckNameLabel.setId("deckNameLabel");
        flow.getChildren().add(deckNameLabel);
        flow.getChildren().add(indicator.createIndicator());
        flow.setId("deckFlow");
        return flow;
    }
    public void add(DeckWords other){
        this.deck.addAll(other.deck);
    }
    public JSONArray toJson(){
        saveOrder();
        JSONArray words = new JSONArray();
        for(int i=0;i<deck.size();i++){
            words.add(deck.get(i).toJson());
        }
        return words;
    }

}
class SortByNextRepeat implements Comparator<WordInBound> {
    public int compare(WordInBound p1, WordInBound p2) {
        if(p1.nextrepeat>p2.nextrepeat){
            return 1;
        }else if(p1.nextrepeat<p2.nextrepeat){
            return -1;
        }else{
            return 0;
        }
    }
}
class SortById implements Comparator<WordInBound> {
    public int compare(WordInBound p1, WordInBound p2) {
        if(p1.id>p2.id){
            return 1;
        }else if(p1.id<p2.id){
            return -1;
        }else{
            return 0;
        }
    }
}
