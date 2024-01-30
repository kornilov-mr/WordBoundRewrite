package main.wordboundrewrite.utils.dataHandlers.local;

import main.WordBound;
import main.wordboundrewrite.utils.books.Book;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordEncountered.AddWordEncounteredRequest;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordEncountered.WordEncounteredRequestFactory;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound.*;
import main.wordboundrewrite.utils.repetitions.DeckWords;
import main.wordboundrewrite.utils.repetitions.WordInBound;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataHandler {
    //wordEncountered
    public static void addWordsEncountered(Vector<String> words) {
        for(int i=0;i<words.size();i++){
            boolean isIn=false;
            for(int j=0;j<WordBound.wordsIncountered.size();j++){
                if(Objects.equals(WordBound.wordsIncountered.get(j),words.get(i))){
                    isIn=true;
                }
            }
            if(!isIn) {
                WordBound.wordsIncountered.add(words.get(i));
                WordEncounteredRequestFactory.addRequest(new AddWordEncounteredRequest(words.get(i)));
            }
        }
    }
    //wordInBound
    public static WordInBound createWord(String originalWord, String wordTranslation, String bookName, String deckName, String context, String key) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime data = LocalDateTime.now();
        return new WordInBound(
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                deckName,
                originalWord,
                dtf.format(data),
                wordTranslation,
                -1,
                WordBound.generalldata.lastId,
                key,
                context,
                bookName);
    }
    public static int addNewWordToWordInBound(String originalWord, String wordTranslation,String bookName,String deckName,String context){
        WordInBound wordInBound1 = createWord(originalWord,wordTranslation,bookName,deckName,context,"firstCard");
        WordInBound wordInBound2 = createWord(wordTranslation,originalWord,bookName,deckName,context,"secondCard");
        WordBound.deckInTree.get(bookName).get(deckName).deck.add(wordInBound1);
        WordBound.deckInTree.get(bookName).get(deckName).deck.add(wordInBound2);

        WordBound.generalldata.lastId+=1;
        WordInBoundRequestFactory.addRequest(new AddWordRequest(wordInBound1));
        WordInBoundRequestFactory.addRequest(new AddWordRequest(wordInBound2));

        return (int)WordBound.generalldata.lastId-1;
    }
    public static void addNewBookToWordInBoundJson(String bookName,String realBookName) throws IOException, ParseException {
        Map<String, DeckWords> decks= new HashMap<>();
        decks.put("default", new DeckWords(new Vector<>(),"default",bookName,realBookName));
        WordBound.deckInTree.put(bookName,decks);
        WordInBoundRequestFactory.addRequest(new AddBookRequest(bookName,realBookName));
    }
    public static void addNewDeckToWordInBoundJson(String deckName,String bookName,String realBookName) throws IOException, ParseException {
        WordBound.deckInTree.get(bookName).put(deckName,new DeckWords(new Vector<>(),"default",bookName,realBookName));
        WordInBoundRequestFactory.addRequest( new AddDeckRequest(bookName,deckName));
    }
    public static void changeWordInbound(String originalWord, String wordTranslation,String bookName,String deckName,int idWordInBound) {
        DeckWords currDeck = WordBound.deckInTree.get(bookName).get(deckName);
        Iterator<WordInBound> wordIterator = currDeck.deck.iterator();
        while (wordIterator.hasNext()) {
            WordInBound currWord = wordIterator.next();
            if (currWord.id == idWordInBound) {
                if (Objects.equals(currWord.key, "firstCard")) {
                    currWord.originalWord = originalWord;
                    currWord.wordTranslation = wordTranslation;
                    WordInBoundRequestFactory.addRequest(new ChangeCardRequest(bookName, "firstCard", deckName, idWordInBound, originalWord, wordTranslation));
                }
                if (Objects.equals(currWord.key, "secondCard")) {
                    currWord.wordTranslation = originalWord;
                    currWord.originalWord = wordTranslation;
                    WordInBoundRequestFactory.addRequest(new ChangeCardRequest(bookName, "secondCard", deckName, idWordInBound, originalWord, wordTranslation));
                }
            }
        }
    }
    //BookSet
    public static void AddBookToBookSet(String coverPath,String storageBookName,String realBookName,String author,String charset,String bookPath,int pullContext) throws IOException, ParseException {
        Book book = new Book(coverPath,storageBookName,realBookName,author,charset,bookPath,pullContext);
        WordBound.bookset.addBook(storageBookName,book);
    }
    public static void updateBookData(String bookName, int lastPage, String lastDeck) throws IOException, ParseException {
        WordBound.bookset.getBook(bookName).lastDeck=lastDeck;
        WordBound.bookset.getBook(bookName).lastPage=lastPage;
    }
}
