package main.wordboundrewrite.utils.books;

import javafx.util.Pair;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class BookSet {
    public Map<String,Book> allbooks = new HashMap<>();
    public BookSet(){
    }
    public void addBook(String storageBooknName,Book book){
        allbooks.put(storageBooknName,book);
    }
    public void UpdateLastTime(String storageBooknName) throws IOException, ParseException {
        allbooks.get(storageBooknName).upDateTime();
    }
    public boolean isAlreadyAdded(String bookName){
        return allbooks.containsKey(bookName);
    }
    public Book getBook(String storageBooknName){
        return allbooks.get(storageBooknName);
    }
    public Vector<Book> getSortedBytime(){
        Vector<Pair<Long,Book>> booksOrder=new Vector<>();
        Iterator<String> booknamesIterator= allbooks.keySet().iterator();
        while(booknamesIterator.hasNext()){
            String key= booknamesIterator.next();
            Book currBook= allbooks.get(key);
            booksOrder.add(new Pair<>(currBook.timeLastSeen,currBook));
        }
        Collections.sort(booksOrder,new Sortbytime());
        Vector<Book> books= new Vector<>();
        for(int i=0;i<booksOrder.size();i++){
            books.add(booksOrder.get(i).getValue());
        }
        return books;
    }
    private boolean compareStrings(String s, String s2){
        int sz=s.length();
        for(int i=0;i<sz;i++){
            if(i>=s2.length()){
                return false;
            }
            if(!Objects.equals(s.charAt(i),s2.charAt(i))){
                return false;
            }
        }
        return true;
    }
    public Vector<Book> sortBooksByString(String s){
        Vector<Book> books = new Vector<>();
        Vector<Boolean> alreadyAdded= new Vector<>();

        Iterator<String> booknamesIterator= allbooks.keySet().iterator();
        while(booknamesIterator.hasNext()){
            String key= booknamesIterator.next();
            Book currBook= allbooks.get(key);
            if(compareStrings(s.toLowerCase(),currBook.author.toLowerCase())){
                alreadyAdded.add(true);
                books.add(currBook);
            }else{
                alreadyAdded.add(false);
            }
        }
        int i=0;
        booknamesIterator= allbooks.keySet().iterator();
        while(booknamesIterator.hasNext()){
            String key= booknamesIterator.next();
            Book currBook= allbooks.get(key);
            if(compareStrings(s.toLowerCase(),currBook.realBookName.toLowerCase())){
                if(alreadyAdded.get(i)==false){
                    books.add(currBook);
                }
            }
            i++;
        }
        return books;
    }
}
class Sortbytime implements Comparator<Pair<Long,Book>> {
    public int compare(Pair<Long,Book> p1, Pair<Long,Book> p2) {
        if(p1.getKey()<p2.getKey()){
            return 1;
        }else if(p1.getKey()>p2.getKey()){
            return -1;
        }else{
            return 0;
        }
    }
}