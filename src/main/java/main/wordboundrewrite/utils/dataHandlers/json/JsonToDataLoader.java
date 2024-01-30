package main.wordboundrewrite.utils.dataHandlers.json;

import main.wordboundrewrite.account.Config;
import main.wordboundrewrite.utils.books.Book;
import main.wordboundrewrite.utils.books.BookSet;
import main.wordboundrewrite.utils.repetitions.DeckWords;
import main.wordboundrewrite.utils.repetitions.WordInBound;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

public class JsonToDataLoader {
    public static Config createGenerallData(JSONObject jo){
        Map<String,Object> generalldata= new HashMap<>();
        Config config= new Config();
        config.fontSize=(long) jo.get("fontSize");
        config.lastId=(long) jo.get("lastId");
        return config;
    }
    public static Vector<String> createWordsIncoutered(JSONObject jo){
        Vector<String>  wordsIncountered= new Vector<>();
        long bookCount = (long) jo.get("bookCount");
        if (bookCount == 0) {
            return wordsIncountered;
        }
        JSONArray books = (JSONArray) jo.get("wordsIncountered");
        for (int i = 0; i < bookCount; i++) {
            wordsIncountered.add((String) books.get(i));
        }
        return wordsIncountered;
    }
    public static BookSet createBookSet(JSONObject jo) {
        BookSet bookSet = new BookSet();
        long bookCount = (long) jo.get("bookCount");
        if (bookCount == 0) {
            return bookSet;
        }
        JSONObject booksInfo = (JSONObject) jo.get("books");
        Iterator<String> booksIterator = booksInfo.keySet().iterator();


        while (booksIterator.hasNext()) {
            String bookname = booksIterator.next();
            JSONObject data = (JSONObject) booksInfo.get(bookname);
            Book currBook = new Book((String) data.get("coverPath"), (String) data.get("name"), (String) data.get("realBookName"), (String) data.get("author"),(String) data.get("charset"),(String) data.get("bookPath"),(Integer) data.get("pullContext"),(Long) data.get("timeLastSeen"));
            bookSet.addBook((String) data.get("name"), currBook);
        }
        return bookSet;
    }
    public static Map<String, Map<String, DeckWords>> createWordsInbound(JSONObject jo){
        Map<String, Map<String, DeckWords>> deckInTree = new HashMap<>();

        JSONObject books = (JSONObject) jo.get("books");
        Iterator<String> booksIterator = books.keySet().iterator();

        while (booksIterator.hasNext()) {
            String bookname = booksIterator.next();
            JSONObject decks = (JSONObject) books.get(bookname);
            String realBookName = (String) decks.get("realBookName");
            Iterator<String> decksIterator = decks.keySet().iterator();
            Map<String, DeckWords> decksInBook = new HashMap<>();

            while (decksIterator.hasNext()) {
                String deckName = decksIterator.next();
                if (Objects.equals(deckName, "realBookName")) {
                    continue;
                }
                Vector<WordInBound> wordInBound = new Vector<>();

                JSONArray words = (JSONArray) decks.get(deckName);
                for(Object word : words){
                    wordInBound.add(CreateWordInBoundFromJson((JSONObject) word));
                }
                DeckWords currDeck = new DeckWords(wordInBound, deckName, bookname, realBookName);
                decksInBook.put(deckName, currDeck);
            }
            deckInTree.put(bookname, decksInBook);
        }
        return deckInTree;
    }
    private static WordInBound CreateWordInBoundFromJson(JSONObject wordInfo){
        WordInBound word = new WordInBound((Long) wordInfo.get("realTime"),
                (Long) wordInfo.get("nextrepeat"),
                (String) wordInfo.get("deck"),
                (String) wordInfo.get("originalWord"),
                (String) wordInfo.get("time"),
                (String) wordInfo.get("wordTranslation"),
                (Long) wordInfo.get("repeatCount"),
                (Long) wordInfo.get("id"),
                (String) wordInfo.get("key"),
                (String) wordInfo.get("context"),
                (String) wordInfo.get("bookName"));
        return word;
    }
}
