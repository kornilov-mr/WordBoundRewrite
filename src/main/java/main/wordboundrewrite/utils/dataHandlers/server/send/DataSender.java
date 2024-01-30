package main.wordboundrewrite.utils.dataHandlers.server.send;

import main.WordBound;
import main.wordboundrewrite.account.Account;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.BookFileRequest.BookFileRequestFactory;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.DailyReportRequest.DailyReportRequestFactory;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordEncountered.WordEncounteredRequestFactory;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound.WordInBoundRequestFactory;
import main.wordboundrewrite.utils.path.PathHandler;
import main.wordboundrewrite.utils.path.UrlHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

public class DataSender {
    public static WordInBoundRequestFactory wordInBoundRequestFactory= new WordInBoundRequestFactory(UrlHandler.wordInBoundURI);
    public static WordEncounteredRequestFactory wordEncounteredRequestFactory= new WordEncounteredRequestFactory(UrlHandler.wordEncounteredURI);
    public static DailyReportRequestFactory dailyReportRequestFactory= new DailyReportRequestFactory(UrlHandler.dailyReportURI);
    public static BookFileRequestFactory bookFileRequestFactory= new BookFileRequestFactory(UrlHandler.bookFileURI);
    public static void sendAllData() throws URISyntaxException, IOException, InterruptedException {
        wordInBoundRequestFactory.sendRequest();
        wordEncounteredRequestFactory.sendRequest();
        dailyReportRequestFactory.sendRequest();
        bookFileRequestFactory.sendRequest();
        sendBookInfo();
        sendGenerallData();
    }
    private static void sendBookInfo() throws IOException, InterruptedException {
        WordBound.logger.info("sending bookInfo send request on"+UrlHandler.userLoginURI.toString()+"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.BookInfoURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .POST( HttpRequest.BodyPublishers.ofFile(PathHandler.bookInfoPath))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("BookInfo send");
    }
    private static void sendGenerallData() throws IOException, InterruptedException {
        WordBound.logger.info("sending generallData send request on"+UrlHandler.generallDataURI.toString()+"...");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.generallDataURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .POST( HttpRequest.BodyPublishers.ofFile(PathHandler.bookInfoPath))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("BookInfo send");
    }
}
