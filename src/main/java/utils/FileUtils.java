package utils;

import com.jdiai.tools.Timer;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.Objects;

public class FileUtils {

    public static final String RESOURCES_PATH = Paths.get("src", "test", "resources").toAbsolutePath().toString();
    public static String downloadsDir = Paths.get(RESOURCES_PATH, "downloads").toAbsolutePath().toString();

    public static String readFile(String filePath) throws FileNotFoundException {
        return readFile(filePath, false);
    }

    public static String readFileFromResources(String filePath) throws FileNotFoundException {
        return readFile(Paths.get(RESOURCES_PATH, filePath).toAbsolutePath().toString());
    }

    public static String getTestDataFilePath(String fileName) {
        return Paths.get(RESOURCES_PATH, "test", "data", fileName).toString();
    }

    /***
     * Read file from downloads dir
     * file content will be attached to the Allure report
     * @param filePath relative file path
     * @return file content
     */
    public static String readFileFromDownloads(String filePath) throws FileNotFoundException {
        String fileContent = readFile(Paths.get(downloadsDir, filePath).toString());
        if (filePath.endsWith("csv")) {
            attachCsv(filePath, fileContent);
        } else {
            attach(filePath, fileContent);
        }
        return fileContent;
    }

    @Attachment(value = "{0} content", type = "text/csv", fileExtension = ".csv")
    private static String attachCsv(String fileName, String content) {
        return content;
    }

    /***
     * Add attachment to the Allure report
     * @param fileName attachment name
     * @param content attachment content
     * @return original content
     */
    @Attachment(value = "{0} content")
    private static String attach(String fileName, String content) {
        return content;
    }

    public static void waitForFileExistInDownloadsDir(String fileName) {
        waitForFileExist(Paths.get(downloadsDir, fileName).toString());
    }

    public static void waitForFileExist(String filePath) {
        Timer timer = new Timer(60 * 1000, 100);
        final boolean exists = timer.wait(() -> new File(filePath).exists());
        System.out.println(filePath + " file exists = " + exists);

        if (!exists) throw new AssertionError(filePath + " file not exist, please check file path!");
    }

    public static int getDownloadedFilesCount() {
        return getDownloadedFilesCount(downloadsDir);
    }

    @Attachment("DownloadedFilesCount")
    public static int getDownloadedFilesCount(String downloadsDir) {
        File folder = new File(downloadsDir);
        File[] listOfFiles = getListOfFiles(folder);
        return listOfFiles != null ? listOfFiles.length : 0;
    }

    private static File[] getListOfFiles(File folder) {
        return folder.listFiles();
    }

    /***
     * Delete all files from download directory
     */
    public static void deleteAllDownloadedFiles() {
        File folder = new File(downloadsDir);
        File[] listOfFiles = getListOfFiles(folder);
        int size = listOfFiles != null ? listOfFiles.length : 0;
        for (int i = 0; i < size; i++) {
            if (listOfFiles[i].isDirectory()) {
                for (File file : Objects.requireNonNull(listOfFiles[i].listFiles())) {
                    file.delete();
                }
                listOfFiles[i].delete();
            } else if (!listOfFiles[i].getName().endsWith("gitkeep")) {
                listOfFiles[i].delete();
            }
        }

    }

    /***
     * Read file(csv, txt ...)
     * @param filePath filePath full path to file
     * @param deleteAfterRead deleteAfterRead set true to delete file after read operation completed
     * @return file content
     */
    public static String readFile(String filePath, boolean deleteAfterRead) throws FileNotFoundException {
        String s = "";
        waitForFileExist(filePath);
        try {

            s = IOUtils.toString(new FileInputStream(filePath), Charset.defaultCharset());
            String name = Paths.get(filePath).toFile().getName();
            Allure.addAttachment(name, s);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileNotFoundException(filePath + " file not exist, please check file path!");
        } finally {
            if (deleteAfterRead)
                new File(filePath).delete();
        }
        return s;
    }

    /***
     * Download file via URL
     * @param url - URL to file
     * @param fileName - downloaded file name
     * @return downloaded file
     */
    public static File downloadFile(String url, String fileName) {
        File file = new File(downloadsDir + fileName);
        try {
            InputStream in = new URL(url).openStream();
            Files.copy(in, Paths.get(file.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
            in.close();
        } catch (IOException e) {
            throw new RuntimeException("Did not get file!", e);
        }
        return file;
    }

    public static void unzipFolderZip4j(Path source) {
        try {
            new ZipFile(source.toFile())
                    .extractAll(source.toString().replace(".zip", ""));
        } catch (ZipException e) {
            throw new RuntimeException("An error occured while extracting " + source, e);
        }
    }

    /***
     * Write file
     * @param toFileName - file path
     * @param bytes - file content byte[]
     */
    public static void writeFile(String toFileName, byte[] bytes) {
        Path path = Paths.get(toFileName);
        try {
            Files.write(path, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /***
     * Write file
     * @param toFileName - file path
     * @param content - file content in text format
     */
    public static void writeFile(String toFileName, String content) {
        Path path = Paths.get(toFileName);
        try {
            Files.write(path, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void writeFileToDownloadsDir(String toFileName, String content) {
        Path path = Paths.get(downloadsDir, toFileName);
        try {
            Files.write(path, content.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
