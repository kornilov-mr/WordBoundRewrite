package main.wordboundrewrite.utils.dataHandlers.server.user;

import main.WordBound;
import main.wordboundrewrite.utils.path.UrlHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserHandler {
    public static String checkIfTokenValid(String authorizationToken) throws IOException, InterruptedException, ParseException {
        WordBound.logger.info("sending user check http request on " +UrlHandler.userCheckURI.toString() +"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.userCheckURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("authorization", "Bearer "+ authorizationToken)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(response.body());
        if(body.keySet().contains("message")){
            WordBound.logger.info("receiving response from "+UrlHandler.userCheckURI.toString() +": user token isn't valid");
            return "";
        }
        WordBound.logger.info("receiving response from "+UrlHandler.userCheckURI.toString() +": user token is valid");

        return (String) body.get("token");
    }
    public static String sendLoginRequest(String email,String password) throws  IOException, InterruptedException, ParseException {
        WordBound.logger.info("sending login http request on " +UrlHandler.userLoginURI.toString() +"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.userLoginURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":"+"\""+email+"\""+",\"password\":"+"\""+password+"\""+"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(response.body());
        if(body.keySet().contains("message")){
            WordBound.logger.info("receiving response from "+UrlHandler.userLoginURI.toString() +": user isn't login ");
            return "";
        }
        WordBound.logger.info("receiving response from "+UrlHandler.userLoginURI.toString() +": user is login");

        return (String) body.get("token");
    }
    public static String sendRegistrationRequest(String email, String password) throws  IOException, InterruptedException, ParseException {
        WordBound.logger.info("sending registration  http request on " +UrlHandler.userRegistrationURI.toString() +"...");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(UrlHandler.userRegistrationURI)
                .version(HttpClient.Version.HTTP_1_1)
                .header("content-type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"email\":"+"\""+email+"\""+",\"password\":"+"\""+password+"\""+"}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONParser parser = new JSONParser();
        JSONObject body = (JSONObject) parser.parse(response.body());
        if(body.keySet().contains("message")){
            WordBound.logger.info("receiving response from "+UrlHandler.userLoginURI.toString() +": user isn't registered ");
            return "";
        }
        WordBound.logger.info("receiving response from "+UrlHandler.userLoginURI.toString() +": user is registered ");
        return (String) body.get("token");
    }
}
