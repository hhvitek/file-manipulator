package app.utilities.file_locators;

import app.model.ISuffixes;
import app.model.simplemodel.AllSuffixes;
import app.model.simplemodel.SuffixesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

abstract class FileLocatorTest {

    IFileLocator locator;
    static Path rootFolder;

    @BeforeAll
    static void locateAndSetRootFolder() {
        URL rootFolderUrl = FileLocatorTest.class.getResource("root_folder_name");
        try {
            rootFolder = Paths.get(rootFolderUrl.toURI());
        } catch (URISyntaxException e) {
            Assertions.fail(e);
        }
    }

    abstract void listAllFilesTest();

    @Test
    void listFilesUsingSpecificSuffixes() {
        int expectedFoundFiles = 6;

        ISuffixes suffixesCollection = new SuffixesImpl();
        suffixesCollection.addSuffixes("jpg,txt,avi", ",");

        List<Path> foundFiles = locator.findUsingSuffixes(rootFolder, suffixesCollection);
        int actualFoundFiles = foundFiles.size();

        Assertions.assertEquals(expectedFoundFiles, actualFoundFiles);
    }

    @Test
    void listAllRegularFilesTest()
    {
        int expectedFoundFiles = 8;

        List<Path> foundFiles = locator.listAllFiles(rootFolder);
        int actualFoundFiles = foundFiles.size();

        Assertions.assertEquals(expectedFoundFiles, actualFoundFiles);
    }

    @Test
    void allSuffixesCollection_behavesLikeListAllFilesTest() {
        int expectedFoundFiles = 8;

        ISuffixes emptyCollection = new AllSuffixes();
        List<Path> foundFiles = locator.findUsingSuffixes(rootFolder, emptyCollection);
        int actualFoundFiles = foundFiles.size();

        Assertions.assertEquals(expectedFoundFiles, actualFoundFiles);
    }

}