package model.jobs.file_locators;

import model.ISuffixesCollection;
import model.simplemodel.SimpleModelSuffixesCollectionImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

abstract class FileLocatorTest {

    IFileLocator locator;
    Path rootFolder;

    @BeforeEach
    void locateAndSetRootFolder() {
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

        ISuffixesCollection suffixes = new SimpleModelSuffixesCollectionImpl();
        suffixes.addSuffixes("jpg,txt,avi", ",");

        List<Path> foundFiles = locator.findUsingSuffixes(rootFolder, suffixes);
        int actualFoundFiles = foundFiles.size();

        Assertions.assertEquals(expectedFoundFiles, actualFoundFiles);


    }

}