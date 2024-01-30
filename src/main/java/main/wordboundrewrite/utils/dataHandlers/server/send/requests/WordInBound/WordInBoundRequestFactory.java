package main.wordboundrewrite.utils.dataHandlers.server.send.requests.WordInBound;

import main.WordBound;
import main.wordboundrewrite.account.Account;
import main.wordboundrewrite.utils.dataHandlers.server.send.requests.RequestFactoryAbstractClass;
import main.wordboundrewrite.utils.path.UrlHandler;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WordInBoundRequestFactory extends RequestFactoryAbstractClass {
    public WordInBoundRequestFactory(URI url) {
        super(url);
    }
    public void sendRequest() throws URISyntaxException, IOException, InterruptedException {
        if(!needToSend){
            return;
        }
        WordBound.logger.info("sending wordInBound send request on"+ UrlHandler.wordInBoundURI.toString()+"...");

        System.out.println("sendWordInBound"+buildRequest());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .header("authorization", "Bearer "+ Account.authorizationToken)
                .POST( HttpRequest.BodyPublishers.ofString(buildRequest()))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("WordInBound send");
    }
}
