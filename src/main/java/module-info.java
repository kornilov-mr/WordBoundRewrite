module main.wordboundrewrite {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires json.simple;
    requires java.net.http;
    requires org.apache.httpcomponents.httpmime;
    
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires java.logging;
    requires java.desktop;
    requires org.apache.commons.io;
    requires juniversalchardet;
    opens main.wordboundrewrite to javafx.fxml;
    exports main;
    opens main to javafx.fxml;
}