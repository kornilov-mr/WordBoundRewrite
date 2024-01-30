package main.wordboundrewrite.utils.path;

import java.net.URI;
import java.net.URISyntaxException;

public class UrlHandler {
    public static URI wordInBoundURI;
    public static URI bookFileURI;
    public static URI BookInfoURI;
    public static URI generallDataURI;
    public static URI dailyReportURI;
    public static URI wordEncounteredURI;
    public static URI userCheckURI;
    public static URI userLoginURI;
    public static URI userRegistrationURI;


    static {
        try {
            wordInBoundURI = new URI("http://localhost:7000/api/wordInBound/");
            bookFileURI = new URI("http://localhost:7000/api/bookFiles/");
            BookInfoURI = new URI("http://localhost:7000/api/bookData/");
            generallDataURI =new URI("http://localhost:7000/api/generallData/");
            dailyReportURI = new URI("http://localhost:7000/api/dailyReport/");
            wordEncounteredURI = new URI("http://localhost:7000/api/wordEncountered/");
            userCheckURI = new URI("http://localhost:7000/api/user/check/");
            userLoginURI = new URI("http://localhost:7000/api/user/login/");
            userRegistrationURI = new URI("http://localhost:7000/api/user/registration/");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
