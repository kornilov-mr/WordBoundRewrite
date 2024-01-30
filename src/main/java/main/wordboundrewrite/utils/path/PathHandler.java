package main.wordboundrewrite.utils.path;


import java.io.File;
import java.nio.file.Path;

public class PathHandler {
    private static final Path resourceFolder=new File("").toPath().resolve("src").resolve("main").resolve("resources").resolve("main").resolve("wordboundrewrite");
    public static final Path userDataFolder = resourceFolder.resolve("userData");
    public static final Path bookFolderPath = userDataFolder.resolve("books");
    public static final Path tokenPath = userDataFolder.resolve("token.txt");
    public static final Path dailyReportPath = userDataFolder.resolve("dailyReport.json");
    public static final Path userDataFilePath = userDataFolder.resolve("userGenerallData.json");
    public static final Path wordEncounteredPath = userDataFolder.resolve("wordEncountered.json");
    public static final Path wordInBoundPath = userDataFolder.resolve("wordInBound.json");
    public static final Path bookInfoPath = bookFolderPath.resolve("bookInfo.json");

}
