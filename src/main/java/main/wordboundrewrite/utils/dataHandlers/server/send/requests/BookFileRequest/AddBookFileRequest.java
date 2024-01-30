package main.wordboundrewrite.utils.dataHandlers.server.send.requests.BookFileRequest;

import main.wordboundrewrite.utils.dataHandlers.server.send.requests.Request;

public class AddBookFileRequest implements Request {
    private final String bookName;

    public AddBookFileRequest(String bookName) {
        this.bookName = bookName;
    }

    @Override
    public String getRequest() {
        return bookName;
    }
}
