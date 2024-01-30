package main;

import main.wordboundrewrite.account.Account;
import main.wordboundrewrite.account.Config;
import main.wordboundrewrite.utils.books.BookSet;
import main.wordboundrewrite.utils.dataHandlers.json.JsonToDataLoader;
import main.wordboundrewrite.utils.dataHandlers.json.JsonReader;
import main.wordboundrewrite.utils.dataHandlers.json.JsonWritter;
import main.wordboundrewrite.utils.dataHandlers.server.recieve.DataReciever;
import main.wordboundrewrite.utils.dataHandlers.server.send.DataSender;
import main.wordboundrewrite.utils.file.FileHandler;
import main.wordboundrewrite.utils.repetitions.DeckWords;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Vector;

public class WordBound {
    public static Logger logger = Logger.getLogger(WordBound.class.getName());
    public static Config generalldata;
    public static Vector<String> wordsIncountered = new Vector<>();
    public static Map<String, Map<String, DeckWords>> deckInTree = new HashMap<>();
    public static BookSet bookset;
    public Account account;
    public WordBound() throws IOException, ParseException, URISyntaxException, InterruptedException {
        configurateLogger();

        checkAndCreateDataFiles();
        loadFromFiles();

        account= new Account();
    }
    private void loadFromFiles() throws IOException, ParseException {
        generalldata= JsonToDataLoader.createGenerallData(JsonReader.loadGenerallData());
        wordsIncountered= JsonToDataLoader.createWordsIncoutered(JsonReader.loadWordsEncountered());
        deckInTree= JsonToDataLoader.createWordsInbound(JsonReader.loadWordsInbound());
        bookset= JsonToDataLoader.createBookSet(JsonReader.loadWordsBooks());
    }
    public void loadFromServer() throws IOException, ParseException, InterruptedException {
        generalldata= JsonToDataLoader.createGenerallData(DataReciever.requestGeneralData());
        wordsIncountered= JsonToDataLoader.createWordsIncoutered(DataReciever.requestWordsEncountered());
        deckInTree= JsonToDataLoader.createWordsInbound(DataReciever.requestWordInBound());
        bookset= JsonToDataLoader.createBookSet(DataReciever.requestBookInfo());
    }
    public void sendDataToServer() throws URISyntaxException, IOException, InterruptedException {
        DataSender.sendAllData();
    }
    public void saveDataToFiles() throws IOException, ParseException {
        JsonWritter.saveGeneralData(generalldata);
        JsonWritter.saveWordsInBound(deckInTree);
        JsonWritter.saveBookSet(bookset);
        JsonWritter.saveWordsEncountered(wordsIncountered);
        JsonWritter.saveToken(Account.authorizationToken);
    }
    private void checkAndCreateDataFiles() throws IOException, ParseException {
        FileHandler.checkAndCreateBookJson();
        FileHandler.checkAndCreateTokenFile();
        FileHandler.checkAndCreateEncountered();
        FileHandler.checkAndCreateWordsInBound();
        FileHandler.checkAndCreateEncountered();
        FileHandler.checkAndCreateUserData();
        FileHandler.checkAndCreateDailyReport();
    }
    private void configurateLogger() throws IOException {
        logger.addHandler(new ConsoleHandler());
        logger.addHandler(new java.util.logging.FileHandler());
        logger.log(Level.INFO, "logger was configurated");
    }
}
