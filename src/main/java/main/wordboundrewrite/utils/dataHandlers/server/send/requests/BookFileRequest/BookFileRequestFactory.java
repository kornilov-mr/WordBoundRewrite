package main.wordboundrewrite.utils.dataHandlers.server.send.requests.BookFileRequest;

import main.WordBound;
import main.wordboundrewrite.account.Account;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.RequestFactoryAbstractClass;
import main.wordboundrewrite.utils.path.PathHandler;
import main.wordboundrewrite.utils.path.UrlHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BookFileRequestFactory extends RequestFactoryAbstractClass {
    public BookFileRequestFactory(URI url) {
        super(url);
    }
    public void sendRequest() throws IOException, InterruptedException {
        String requestJson = buildRequest();
        requestJson=requestJson.replace("[","");
        requestJson=requestJson.replace("]","");
        String[] bookNames= requestJson.split(",");
        for(String bookName: bookNames){
            WordBound.logger.info("sending book file send request for book:"+ bookName+" on"+ UrlHandler.wordInBoundURI.toString()+"...");

            File coverFile = PathHandler.bookFolderPath.resolve(bookName).resolve("cover.jpg").toFile();
            File textFile = PathHandler.bookFolderPath.resolve(bookName).resolve("book.txt").toFile();

            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("cover", coverFile, ContentType.create("application/octet-stream"), "cover.jpg")
                    .addBinaryBody("text", textFile, ContentType.create("application/octet-stream"), "book.txt")
                    .build();

            HttpPost request = new HttpPost(url);
            request.setEntity(entity);
            request.setHeader("Content-Type", entity.getContentType().getValue());
            request.setHeader("authorization", "Bearer "+ Account.authorizationToken);
            request.setHeader("bookName", bookName);

            CloseableHttpClient client = HttpClientBuilder.create().build();
            CloseableHttpResponse response =  client.execute(request);
            System.out.println("BookFiles send");
        }
    }
}
