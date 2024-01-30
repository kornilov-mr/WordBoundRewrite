package main.wordboundrewrite.bookReaders.Readers;

public class BookData {
    private final String realBookName;
    private final String author;
    private final String lastDeck;
    private final int lastPage;
    private String text;

    public BookData(String realBookName, String author, String lastDeck, int lastPage) {
        this.realBookName = realBookName;
        this.author = author;
        this.lastDeck = lastDeck;
        this.lastPage = lastPage;
    }


    public String getRealBookName() {
        return realBookName;
    }

    public String getAuthor() {
        return author;
    }

    public String getLastDeck() {
        return lastDeck;
    }

    public int getLastPage() {
        return lastPage;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
