package main.wordboundrewrite.account;

import main.wordboundrewrite.utils.dataHandlers.json.JsonReader;
import main.wordboundrewrite.utils.dataHandlers.server.user.UserHandler;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Account {
    public static String authorizationToken;
    public static boolean isAuthorized= false;
    public Account() throws IOException, URISyntaxException, ParseException, InterruptedException {
        String token= JsonReader.readTokenFromFile();
        checkToken(token);
    }
    public void checkToken(String token) throws URISyntaxException, IOException, ParseException, InterruptedException {
        if(!Objects.equals(token,"")){
            String newToken = UserHandler.checkIfTokenValid(token);
            if(!Objects.equals(newToken,"")){
                isAuthorized=true;
            }
            authorizationToken=newToken;
        }
    }
    public void login(String email,String password) throws URISyntaxException, IOException, ParseException, InterruptedException {
        String token = UserHandler.sendLoginRequest(email,password);
        if(!Objects.equals(token,"")){
            isAuthorized=true;
        }else{
            isAuthorized=false;
        }
        this.authorizationToken=token;
    }
    public void registration(String email,String password) throws URISyntaxException, IOException, ParseException, InterruptedException {
        String token = UserHandler.sendRegistrationRequest(email,password);
        if(!Objects.equals(token,"")){
            isAuthorized=true;
        }else{
            isAuthorized=false;
        }
        this.authorizationToken=token;
    }

}
