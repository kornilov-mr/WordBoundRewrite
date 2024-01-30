package main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;

public class runner extends Application {
    @Override
    public void start(Stage stage) throws IOException, ParseException, URISyntaxException, InterruptedException {
        WordBound wordBound = new WordBound();
    }

    public static void main(String[] args) {
        launch();
    }
}