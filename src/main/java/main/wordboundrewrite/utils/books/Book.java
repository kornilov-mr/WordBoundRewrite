package main.wordboundrewrite.utils.books;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import main.wordboundrewrite.utils.animations.ResizeAnimation;
import main.wordboundrewrite.utils.path.PathHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Book {
    public FlowPane content;
    public String coverPath;
    public String realBookName;
    protected long timeLastSeen;
    public String StorageBookName;
    protected String author;
    public int lastPage;
    public String lastDeck;
    public String charset;
    public String bookPath;
    public int pullContext;
    public Book(String coverPath,String StorageBookName,String realBookName,String author,String charset,String bookPath,int pullContext,long timeLastSeen){
        this.coverPath=coverPath;
        this.author=author;
        this.StorageBookName=StorageBookName;
        this.realBookName=realBookName;
        this.timeLastSeen=timeLastSeen;
        this.charset=charset;
        this.bookPath=bookPath;
        this.pullContext=pullContext;
    }
    public Book(String coverPath,String StorageBookName,String realBookName,String author,String charset,String bookPath,int pullContext){
        this.coverPath=coverPath;
        this.author=author;
        this.StorageBookName=StorageBookName;
        this.realBookName=realBookName;
        this.timeLastSeen=System.currentTimeMillis();
        this.charset=charset;
        this.bookPath=bookPath;
        this.pullContext=pullContext;

    }
    protected void upDateTime(){
        this.timeLastSeen=System.currentTimeMillis();
    }
    public JSONObject toJson(){
        JSONObject jo = new JSONObject();
        jo.put("timeLastSeen",timeLastSeen);
        jo.put("charset",charset);
        jo.put("coverPath",coverPath);
        jo.put("bookPath",bookPath);
        jo.put("lastPage",lastPage);
        jo.put("lastDeck",lastDeck);
        jo.put("author",author);
        jo.put("pullContext",pullContext);
        jo.put("name",StorageBookName);
        jo.put("realBookName",realBookName);
        return jo;
    }
    public FlowPane createFlowPane(){
        FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
        flow.setColumnHalignment(HPos.LEFT); // align labels on left
        flow.setPrefHeight(170);
        flow.setPrefWidth(100);
        flow.setMaxHeight(170);
        flow.setMaxWidth(100);
        flow.setVgap(10);
        FlowPane flowText = new FlowPane(Orientation.HORIZONTAL);
        flowText.setVgap(5);
        flowText.setMaxWidth(40);
        Label nameLabel = new Label(realBookName);
        nameLabel.setId("bookNameLabel");
        Label authorLabel = new Label(author);
        authorLabel.setId("authorLabel");
        flowText.getChildren().add(nameLabel);
        flowText.getChildren().add(authorLabel);

        flow.getChildren().add(createImageView(flow));
        flow.getChildren().add(flowText);
        return flow;

    }
    private FlowPane createImageView(FlowPane flow) {
        FlowPane container = new FlowPane();
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream(coverPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Image image = new Image(inputstream);

        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(160);
        imageView.setFitWidth(160);
        imageView.setId(StorageBookName);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(0);
        imageView.setEffect(colorAdjust);

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(15.0);
        dropShadow.setOffsetY(15.0);
        dropShadow.setColor(new javafx.scene.paint.Color(0, 0, 0, 1));
        imageView.setEffect(dropShadow);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String bookName = event.getPickResult().getIntersectedNode().getId();
//                try {
//                    switchToReaderScene(event, bookName);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ParseException e) {
//                    throw new RuntimeException(e);
//                }
            }
        });
        ResizeAnimation rht = new ResizeAnimation(Duration.millis(200), imageView, new Pair<Double, Double>(170.0, 170.0), new Pair<Double, Double>(190.0, 190.0));

        imageView.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rht.ChangeToBig();
                FadeTransition ft = new FadeTransition(Duration.millis(200), container);
                ft.setFromValue(0);
                ft.setToValue(1);
                SequentialTransition pt = new SequentialTransition(rht, ft);

                pt.play();
            }
        });
        imageView.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rht.ChangeToSmall();
                FadeTransition ft = new FadeTransition(Duration.millis(100), flow);
                ft.setFromValue(0);
                ft.setToValue(1);
                SequentialTransition pt = new SequentialTransition(rht, ft);
                pt.play();
            }
        });
        container.getChildren().add(imageView);
        return container;
    }
}
