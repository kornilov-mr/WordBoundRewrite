package main.wordboundrewrite.utils.file;

import main.WordBound;
import main.wordboundrewrite.utils.path.PathHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;

public class FileHandler {
    public static void checkAndCreateEncountered() throws IOException, ParseException {
        WordBound.logger.log(Level.INFO,"trying to create wordEncountered Json file...");
        if(!PathHandler.wordEncounteredPath.toFile().isFile()){
            File json = PathHandler.wordEncounteredPath.toFile();
            json.createNewFile();
            JSONObject jo = new JSONObject();
            jo.put("bookCount",0);
            jo.put("wordsIncountered",new JSONArray());
            PrintWriter pw = new PrintWriter(PathHandler.wordEncounteredPath.toString());
            pw.write(jo.toJSONString());
            pw.flush();
            pw.close();
            WordBound.logger.log(Level.INFO,"create wordEncountered Json file created");
        }else{
            WordBound.logger.log(Level.INFO,"create wordEncountered Json file already exists");
        }
    }
    public static void checkAndCreateUserData() throws IOException, ParseException {
        WordBound.logger.log(Level.INFO,"trying to create UserGenerallData Json file...");
        if(!PathHandler.userDataFilePath.toFile().isFile()){
            File json = PathHandler.userDataFilePath.toFile();
            json.createNewFile();
            JSONObject jo = new JSONObject();

            jo.put("fontSize",6);
            jo.put("lastId",0);
            PrintWriter pw = new PrintWriter(PathHandler.userDataFilePath.toString());
            pw.write(jo.toJSONString());
            pw.flush();
            pw.close();
            WordBound.logger.log(Level.INFO,"create UserGenerallData Json file created");
        }else{
            WordBound.logger.log(Level.INFO,"create UserGenerallData Json file already exists");

        }
    }
    public static void checkAndCreateBookJson() throws IOException, ParseException {
        WordBound.logger.log(Level.INFO,"trying to create BookInfo Json file...");
        if(!PathHandler.bookInfoPath.toFile().isFile()){
            File json = PathHandler.bookInfoPath.toFile();
            System.out.println(json.getAbsolutePath());
            json.createNewFile();

            JSONObject jo = new JSONObject();

            jo.put("bookCount",0);
            jo.put("books",new JSONObject());
            PrintWriter pw = new PrintWriter(PathHandler.bookInfoPath.toString());
            pw.write(jo.toJSONString());
            pw.flush();
            pw.close();
            WordBound.logger.log(Level.INFO,"create BookInfo Json file created");

        }else{
            WordBound.logger.log(Level.INFO,"create BookInfo Json file already exists");

        }
    }
    public static void checkAndCreateWordsInBound() throws IOException, ParseException {
        WordBound.logger.log(Level.INFO,"trying to create WordsInBound Json file...");

        if(!PathHandler.wordInBoundPath.toFile().isFile()){
            File json = PathHandler.wordInBoundPath.toFile();
            json.createNewFile();

            JSONObject jo = new JSONObject();
            jo.put("books",new JSONObject());
            PrintWriter pw = new PrintWriter(PathHandler.wordInBoundPath.toString());
            pw.write(jo.toJSONString());
            pw.flush();
            pw.close();
            WordBound.logger.log(Level.INFO,"create WordsInBound Json file created");
        }
        WordBound.logger.log(Level.INFO,"create WordsInBound Json file already exists");
    }
    public static void checkAndCreateDailyReport() throws IOException, ParseException {
        WordBound.logger.log(Level.INFO,"trying to create dailyReport Json file...");
        if(!PathHandler.dailyReportPath.toFile().isFile()){
            File json = PathHandler.dailyReportPath.toFile();
            json.createNewFile();

            JSONObject jo = new JSONObject();
            PrintWriter pw = new PrintWriter(PathHandler.dailyReportPath.toString());
            pw.write(jo.toJSONString());
            pw.flush();
            pw.close();
            WordBound.logger.log(Level.INFO,"create dailyReport Json file created");

        }
        WordBound.logger.log(Level.INFO,"create dailyReport Json file already exists");

    }
    public static void checkAndCreateTokenFile() throws IOException {
        WordBound.logger.log(Level.INFO,"trying to create token Txt file...");
        if(!PathHandler.tokenPath.toFile().isFile()){
            File tokenFile = PathHandler.tokenPath.toFile();
            tokenFile.createNewFile();
            FileWriter myWriter = new FileWriter(PathHandler.tokenPath.toString());
            myWriter.write("no Token");
            myWriter.close();
            WordBound.logger.log(Level.INFO,"create token Txt file created");
        }else{
            WordBound.logger.log(Level.INFO,"create token Txt file already exists");
        }
    }
    public static void createBookFolder(String bookName) throws IOException {
        File folderFile = PathHandler.bookFolderPath.resolve(bookName).toFile();
        WordBound.logger.log(Level.INFO,"trying to create book Folder with name "+bookName+" ...");
        if(!folderFile.isFile()){
            folderFile.mkdirs();
            WordBound.logger.log(Level.INFO,"create token book Folder created");
        }else{
            WordBound.logger.log(Level.INFO,"create token book Folder already exists");
        }
    }
}
