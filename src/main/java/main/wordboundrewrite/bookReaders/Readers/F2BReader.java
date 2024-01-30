package main.wordboundrewrite.bookReaders.Readers;

import main.WordBound;
import main.wordboundrewrite.bookReaders.BookReaderAbstractClass;
import main.wordboundrewrite.utils.dataHandlers.local.DataHandler;
import main.wordboundrewrite.utils.path.PathHandler;

import java.io.*;

import org.json.simple.parser.ParseException;

public class F2BReader extends BookReaderAbstractClass {

    @Override
    public void addNewBook(String path) throws IOException, ParseException {
        createTempFiles();
        String tempTxtFilePath=PathHandler.bookFolderPath.resolve("temp").resolve("temp.txt").toString();
        copyFileUsingStream(new File(path), new File(tempTxtFilePath));
        String charset = detectCharset(tempTxtFilePath);
        String content = getContent(tempTxtFilePath,charset);

        String realBookName = getBookName(content);
        String storageBookName= realBookName.replaceAll(" ","");

        if(WordBound.bookset.isAlreadyAdded(storageBookName)){
            deleteTempFiles();
        }else{
            renameFiles(storageBookName);
            String author = getAuthor(content);
            File coverFile = createCover(content);
            File bookFie=PathHandler.bookFolderPath.resolve(storageBookName).resolve("book.txt").toFile();

            DataHandler.addNewBookToWordInBoundJson(storageBookName,realBookName);
            DataHandler.AddBookToBookSet(coverFile.toString(),storageBookName,realBookName,author,charset,bookFie.toString(),0);

            DataHandler.addNewBookToWordInBoundJson(storageBookName,realBookName);
            DataHandler.addNewDeckToWordInBoundJson("default",storageBookName,realBookName);
        }
    }
}
