package main.wordboundrewrite.utils.dataHandlers.json;

import main.WordBound;
import main.wordboundrewrite.account.Config;
import main.wordboundrewrite.utils.books.Book;
import main.wordboundrewrite.utils.books.BookSet;
import main.wordboundrewrite.utils.path.PathHandler;
import main.wordboundrewrite.utils.path.UrlHandler;
import main.wordboundrewrite.utils.repetitions.DeckWords;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class JsonWritter {
    public static void saveToken(String token) throws IOException {
        WordBound.logger.info("saving json web token to: "+ PathHandler.tokenPath.toString()+"...");
        FileWriter myWriter = new FileWriter(PathHandler.tokenPath.toString());
        myWriter.write(token);
        myWriter.close();
    }
    public static void saveGeneralData(Config config) throws IOException {
        WordBound.logger.info("saving generall user data to: "+ PathHandler.userDataFilePath.toString()+"...");
        File json = new File(PathHandler.userDataFilePath.toString());
        json.delete();
        json.createNewFile();
        PrintWriter pw = new PrintWriter(PathHandler.userDataFilePath.toString());
        pw.write(config.createJson().toJSONString());
        pw.flush();
        pw.close();
    }
    public static void saveWordsEncountered(Vector<String> wordsIncountered) throws IOException, ParseException {
        WordBound.logger.info("saving wordEncountered to: "+ PathHandler.wordEncounteredPath.toString()+"...");
        File json = new File(PathHandler.wordEncounteredPath.toString());
        json.delete();
        json.createNewFile();
        JSONObject jo = new JSONObject();
        jo.put("bookCount",wordsIncountered.size());
        jo.put("wordsIncountered",wordsIncountered);
        PrintWriter pw = new PrintWriter(PathHandler.wordEncounteredPath.toString());
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
    public static void saveWordsInBound(Map<String, Map<String,DeckWords>> deckInTree) throws IOException, ParseException {
        WordBound.logger.info("saving wordInBound to: "+ PathHandler.wordInBoundPath.toString()+"...");

        File json = new File(PathHandler.wordInBoundPath.toString());
        json.delete();
        json.createNewFile();
        JSONObject jo = new JSONObject();
        JSONObject books= new JSONObject();
        Iterator<String> booksIterator = deckInTree.keySet().iterator();

        while(booksIterator.hasNext()){

            String bookName= booksIterator.next();

            JSONObject decksJson= new JSONObject();

            Map<String,DeckWords> decks = deckInTree.get(bookName);

            Iterator<String> deckIterator = decks.keySet().iterator();
            String deckName="";
            DeckWords currDeck = null;
            while(deckIterator.hasNext()){
                deckName = deckIterator.next();
                currDeck = decks.get(deckName);
                decksJson.put(deckName,currDeck.toJson());
            }
            decksJson.put("realBookName",currDeck.realBookName);
            books.put(bookName,decksJson);
        }
        jo.put("books",books);

        PrintWriter pw = new PrintWriter(PathHandler.wordInBoundPath.toString());
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
    public static void saveBookSet(BookSet bookSet) throws IOException, ParseException {
        WordBound.logger.info("saving bookSet to: "+ PathHandler.bookInfoPath.toString()+"...");

        File json = new File(PathHandler.bookInfoPath.toString());
        json.delete();
        json.createNewFile();
        JSONObject jo = new JSONObject();
        JSONObject books = new JSONObject();

        Iterator<Book> bookIterator = bookSet.allbooks.values().iterator();
        while(bookIterator.hasNext()){
            Book currBook= bookIterator.next();
            books.put(currBook.StorageBookName,currBook.toJson());
        }
        jo.put("books",books);
        PrintWriter pw = new PrintWriter(PathHandler.bookInfoPath.toString());
        pw.write(jo.toJSONString());
        pw.flush();
        pw.close();
    }
}
