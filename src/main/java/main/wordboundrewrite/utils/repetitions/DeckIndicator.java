package main.wordboundrewrite.utils.repetitions;

import javafx.geometry.Orientation;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;

public class DeckIndicator {
    public int red;
    public int blue;
    public int green;
    public DeckIndicator(int red, int blue, int green){
        this.red = red;
        this.blue = blue;
        this.green = green;
    }
    protected FlowPane createIndicator(){
        FlowPane flow = new FlowPane(Orientation.HORIZONTAL);
        flow.setHgap(10);
        Label red = new Label(String.valueOf(this.red));
        red.getStyleClass().add("red");
        flow.getChildren().add(red);
        Label blue = new Label(String.valueOf(this.blue));
        blue.getStyleClass().add("blue");
        flow.getChildren().add(blue);
        Label green = new Label(String.valueOf(this.green));
        green.getStyleClass().add("green");
        flow.getChildren().add(green);
        return flow;
    }
}
