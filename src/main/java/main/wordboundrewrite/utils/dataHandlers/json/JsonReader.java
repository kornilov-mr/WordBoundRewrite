package main.wordboundrewrite.utils.dataHandlers.json;

import main.WordBound;
import main.wordboundrewrite.utils.path.PathHandler;
import main.wordboundrewrite.utils.path.UrlHandler;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JsonReader {
    public static JSONObject loadWordsEncountered() throws IOException, ParseException {
        WordBound.logger.info("reading WordEncountered form "+PathHandler.wordEncounteredPath.toString()+"...");
        Object obj = new JSONParser().parse(new FileReader(PathHandler.wordEncounteredPath.toString()));
        JSONObject jo = (JSONObject) obj;

        return jo;
    }
    public static JSONObject loadGenerallData() throws IOException, ParseException {
        WordBound.logger.info("reading generallData form "+PathHandler.userDataFilePath.toString()+"...");

        Object obj = new JSONParser().parse(new FileReader(PathHandler.userDataFilePath.toString()));
        JSONObject jo = (JSONObject) obj;
        return jo;
    }
    public static JSONObject loadWordsBooks() throws IOException, ParseException{
        WordBound.logger.info("reading bookInfo form "+PathHandler.bookInfoPath.toString()+"...");

        Object obj = new JSONParser().parse(new FileReader(PathHandler.bookInfoPath.toString()));
        JSONObject jo = (JSONObject) obj;
        return jo;
    }
    public static JSONObject loadWordsInbound() throws IOException, ParseException {
        WordBound.logger.info("reading wordInbound form "+PathHandler.wordInBoundPath.toString()+"...");

        Object obj = new JSONParser().parse(new FileReader(PathHandler.wordInBoundPath.toString()));
        JSONObject jo = (JSONObject) obj;
        return jo;
    }
    public static String readTokenFromFile() throws IOException {
        WordBound.logger.info("reading token form "+PathHandler.tokenPath.toString()+"...");

        File file = PathHandler.tokenPath.toFile();
        Scanner sc = new Scanner(file);
        String token=sc.nextLine();
        WordBound.logger.info("token read:"+token+"...");
        return token;
    }
}
