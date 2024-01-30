package main.wordboundrewrite.utils.dataHandlers.server.recieve;

import main.WordBound;
import main.wordboundrewrite.account.Account;
import main.wordboundrewrite.utils.file.FileHandler;
import main.wordboundrewrite.utils.path.PathHandler;
import main.wordboundrewrite.utils.path.UrlHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

public class DataReciever {
    public static JSONObject requestGeneralData() throws IOException, InterruptedException, ParseException {
        WordBound.logger.fine("sending generalldata receive request on"+ UrlHandler.generallDataURI.toString()+"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.generallDataURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONParser parser = new JSONParser();
        System.out.println(response.body());
        return (JSONObject) parser.parse(response.body());
    }
    public static JSONObject requestWordsEncountered() throws IOException, InterruptedException, ParseException{
        WordBound.logger.fine("sending wordEncountered receive request on"+ UrlHandler.wordEncounteredURI.toString()+"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.wordEncounteredURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }
    public static JSONObject requestWordInBound() throws IOException, InterruptedException, ParseException{
        WordBound.logger.fine("sending wordsInBound receive request on"+ UrlHandler.wordInBoundURI.toString()+"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.wordInBoundURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }
    public static JSONObject requestBookInfo() throws ParseException, IOException, InterruptedException {
        WordBound.logger.fine("sending bookInfo receive request on"+ UrlHandler.bookFileURI.toString()+"...");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.BookInfoURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }
    public static JSONObject requestDailyReports() throws ParseException, IOException, InterruptedException {
        WordBound.logger.fine("sending dailyReports receive request on"+ UrlHandler.dailyReportURI.toString()+"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.dailyReportURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(response.body());
    }

    public static JSONObject requestMissingBooks() throws ParseException, IOException, InterruptedException {
        JSONObject serverBookSet = requestBookInfo();
        JSONObject books = (JSONObject) serverBookSet.get("books");
        Iterator<String> bookIterator = books.keySet().iterator();
        while(bookIterator.hasNext()){
            String bookName = bookIterator.next();
            if(!WordBound.bookset.allbooks.containsKey(bookName)){
                FileHandler.createBookFolder(bookName);
                requestBookFile(bookName+"/"+"book.txt","text/plain");
                requestBookFile(bookName+"/"+"cover.jpg","image/jpeg ");
            }
        }
        serverBookSet.keySet().iterator();
        return serverBookSet;
    }
    private static void requestBookFile(String storageName,String contentType) throws IOException, InterruptedException {
        WordBound.logger.fine("sending bookFile receive request for book: "+storageName +" on"+ UrlHandler.dailyReportURI.toString()+"...");
        System.out.println(storageName+" "+ contentType);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.bookFileURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("contentType", contentType)
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .header("storagename",storageName)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        File bookfile = PathHandler.bookFolderPath.resolve(storageName).toFile();
        if (bookfile.createNewFile()) {
            WordBound.logger.fine("File created: " + bookfile.getName());
            FileWriter myWriter = new FileWriter(PathHandler.bookFolderPath.resolve(storageName).toString());
            myWriter.write(response.body());
            myWriter.close();
        } else {
            WordBound.logger.fine("File already exists " + bookfile.getName());
        }
    }
}
