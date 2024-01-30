package main.wordboundrewrite.bookReaders;

import main.wordboundrewrite.bookReaders.Readers.BookData;
import main.wordboundrewrite.utils.path.PathHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class BookReaderFromLocal {
    public BookData loadBook(String name) throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader(PathHandler.bookInfoPath.toString()));
        JSONObject jo = (JSONObject) obj;
        JSONObject books = (JSONObject) jo.get("books");
        JSONObject curr_book= (JSONObject) books.get(name);
        BookData bookData = new BookData((String) curr_book.get("realBookName"),
                (String) curr_book.get("author"),
                (String) curr_book.get("lastDeck"),
                (Integer) curr_book.get("lastPage"));
        String text = readMainText((String) curr_book.get("bookPath"),
                (String) curr_book.get("charset"));
        bookData.setText(text);
        return bookData;
    }
    private String readMainText(String pathToBook,String charset){
        String content = getContent(pathToBook,charset);
        int startBody = content.indexOf("<body>");
        int endBody = content.lastIndexOf("</body>");

        endBody = endBody + 7;

        char[] dst = new char[endBody - startBody];
        content.getChars(startBody, endBody, dst, 0);
        String body = new String(dst);
        body = body.replace("image l:href=\"#", "img src=\"file://" + new File(pathToBook).toString().replace("\\", "/") + "/");
        return body;
    }
    private String getContent(String filePath, String charset) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), charset))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
