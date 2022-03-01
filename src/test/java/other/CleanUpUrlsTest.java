package other;

import org.testng.annotations.Test;
import utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CleanUpUrlsTest {
    @Test
    public void filterFileToHaveUniqueValues() throws FileNotFoundException {
        Set<String> uniqueValues = getUniqueLinesOnly("placesUrls.txt");
        if (FileUtils.isFileExistOnDownloadsDir("placesUrlsUnique.txt")) {
            uniqueValues.addAll(getUniqueLinesOnly("placesUrlsUnique.txt"));
        }
        String finalText = String.join("\n", uniqueValues);
        FileUtils.writeNewFileToDownloadsDir("placesUrlsUnique.txt", finalText);
    }

    private Set<String> getUniqueLinesOnly(String fileName) throws FileNotFoundException {
        String text = FileUtils.readFileFromDownloads(fileName);
        String[] split = text.split("\n");
        return new HashSet<>(Arrays.asList(split));
    }
}
