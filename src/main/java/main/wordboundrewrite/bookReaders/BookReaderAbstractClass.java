package main.wordboundrewrite.bookReaders;

import main.wordboundrewrite.utils.path.PathHandler;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.apache.commons.io.FileUtils;
import org.mozilla.universalchardet.UniversalDetector;

import java.io.*;
import java.util.ArrayList;
import java.util.Base64;

public abstract class BookReaderAbstractClass {
    protected String detectCharset(String filePath) {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            byte[] bytes = new byte[4096];
            UniversalDetector detector = new UniversalDetector(null);
            int nread;
            while ((nread = inputStream.read(bytes)) > 0 && !detector.isDone()) {
                detector.handleData(bytes, 0, nread);
            }
            detector.dataEnd();
            return detector.getDetectedCharset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected void deleteTempFiles() throws IOException {
        File tempFolder= PathHandler.bookFolderPath.resolve("temp").toFile();
        FileUtils.deleteDirectory(tempFolder);
    }
    protected void createTempFiles() throws IOException {
        File tempFolder= PathHandler.bookFolderPath.resolve("temp").toFile();
        if(tempFolder.isFile()){
            FileUtils.deleteDirectory(tempFolder);
        }
        tempFolder.mkdirs();
        File tempTxtFile= PathHandler.bookFolderPath.resolve("temp").resolve("temp.txt").toFile();
        tempTxtFile.createNewFile();
    }
    protected void renameFiles(String storageName){
        File tempFolder= PathHandler.bookFolderPath.resolve("temp").toFile();
        File tempTxtFile= PathHandler.bookFolderPath.resolve("temp").resolve("temp.txt").toFile();
        tempTxtFile.renameTo(PathHandler.bookFolderPath.resolve("temp").resolve("book.txt").toFile());
        tempFolder.renameTo(PathHandler.bookFolderPath.resolve(storageName).toFile());
    }
    protected File createCover(String content) {
        File coverFile=null;
        int startBin = content.indexOf("<binary");
        int endBin = content.lastIndexOf("</binary>");

        if (startBin != -1 || endBin != -1) {

            endBin = endBin + 9;

            char[] dstb = new char[endBin - startBin];
            content.getChars(startBin, endBin, dstb, 0);
            String binContent = new String(dstb);

            ArrayList<String> binCode = new ArrayList<>();
            ArrayList<String> binImg = new ArrayList<>();

            do {
                int nextBin = binContent.indexOf("<binary");
                int lastBin = binContent.indexOf("</binary>");

                if (nextBin != -1) {
                    lastBin = lastBin + 9;

                    char[] dstBin = new char[lastBin - nextBin];
                    binContent.getChars(nextBin, lastBin, dstBin, 0);
                    String binaryEl = new String(dstBin);

                    binContent = binContent.replace(binaryEl, "");

                    binaryEl = binaryEl.replace("</binary>", "");

                    int findString = binaryEl.indexOf(">");
                    String tag = binaryEl.substring(0, findString + 1);

                    binaryEl = binaryEl.replace(tag, "");

                    int findId = tag.indexOf("id=");
                    findId = findId + 4;
                    String imageName = tag.substring(findId);
                    int newFindId = imageName.indexOf("\"");
                    String delText = imageName.substring(newFindId);
                    imageName = imageName.replace(delText, "");

                    binCode.add(binaryEl);
                    binImg.add(imageName);
                } else {
                    break;
                }

            } while (binContent.indexOf("<binary") != -1);

            for (int i = 0; i < binCode.size(); i++) {

                Base64.Decoder decoder = Base64.getMimeDecoder();
                byte[] decodedBytes = decoder.decode(binCode.get(i));

                try {
                    BufferedImage img = ImageIO.read(new ByteArrayInputStream(decodedBytes));

                    String extImage = "";
                    int extNum = 0;
                    extNum = binImg.get(i).indexOf(".");
                    extImage = binImg.get(i).substring(extNum + 1);
                    coverFile = PathHandler.bookFolderPath.resolve(binImg.get(i)).toFile();
                    if (extImage.equals("jpg")) {
                        File outputfile = coverFile;
                        ImageIO.write(img, "jpg", outputfile);
                    } else if (extImage.equals("png")) {
                        File outputfile = coverFile;
                        ImageIO.write(img, "png", outputfile);
                    } else if (extImage.equals("gif")) {
                        File outputfile = coverFile;
                        ImageIO.write(img, "gif", outputfile);
                    }
                } catch (javax.imageio.IIOException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return coverFile;
    }
    protected void copyFileUsingStream(File source, File dest) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            is.close();
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    protected String getContent(String filePath, String charset) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(filePath), charset))) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
            return builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    protected String getAuthor(String content) {

        int start = content.indexOf("<first-name>");
        int end = content.lastIndexOf("</first-name>");

        start = start + 12;

        char[] dest = new char[end - start];
        content.getChars(start, end, dest, 0);
        String firstName = new String(dest);

        start = content.indexOf("<middle-name>");
        end = content.lastIndexOf("</middle-name>");
        String middleName="";
        if(start!=-1&&end!=-1){
            start = start + 13;

            dest = new char[end - start];
            content.getChars(start, end, dest, 0);
            middleName = new String(dest);
        }


        start = content.indexOf("<last-name>");
        end = content.lastIndexOf("</last-name>");

        start = start + 11;

        dest = new char[end - start];
        content.getChars(start, end, dest, 0);
        String lastName = new String(dest);

        return firstName + " " + middleName + " " + lastName;
    }
    protected String getBookName(String content) { int start = content.indexOf("<book-title>");
        int end = content.lastIndexOf("</book-title>");

        start += 12;
        char[] dest = new char[end - start];
        content.getChars(start, end, dest, 0);
        String bookname = new String(dest);

        return bookname;
    }
    public abstract void addNewBook(String path) throws IOException, ParseException;

}
