package model.simplemodel.suffixesdb;

import model.ISuffixesCollection;
import model.simplemodel.CollectionOfSuffixesCollections;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

class FromFileSuffixesDbImplTest {

    ISuffixesDb db;
    static Path rootFolder;
    static Path inputSuffixesDb;

    @BeforeAll
    static void initTestRootFolderTestFiles() {
        testRootFolder();
        createInputSuffixesDbFile();
    }

    static void testRootFolder() {
        Path outputFilePath =
                Paths.get("src", "test", "resources", "model", "simplemodel", "suffixesdb");
        if (Files.isDirectory(outputFilePath)) {
            rootFolder = outputFilePath;
        } else {
            Assertions.fail("Cannot find test root folder: " + outputFilePath);
        }
    }

    static void createInputSuffixesDbFile() {
        inputSuffixesDb = rootFolder.resolve("suffixesdb.txt");

        String inputSuffixesDbText = "# This file represents file suffixes collections\n"
                + "# name|suffix1,suffix2,suffix3\n"
                + "# COLUMN_SEPARATOR = \"||\"\n"
                + "# SUFFIX_SEPARATOR = \",\"\n"
                + "\n"
                + "AUDIO||mp3,mpa,ogg\n"
                + "VIDEO||mp4,avi,webm\n"
                + "MY_LIST||txt,out\n";

        try {
            Files.writeString(inputSuffixesDb, inputSuffixesDbText);
        } catch (IOException e) {
            Assertions.fail("Cannot create test text db file: " + inputSuffixesDb);
        }
    }

    @AfterAll
    static void cleanTestFolder() {
        if (inputSuffixesDb != null && Files.isRegularFile(inputSuffixesDb)) {

        }
    }

    @Test
    void standardFile_LoadTest() {
        db = new FromFileSuffixesDbImpl(inputSuffixesDb);

        CollectionOfSuffixesCollections suffixesCollections = db.load();

        Assertions.assertTrue(suffixesCollections.size() == 3);

        Optional<ISuffixesCollection> audios = suffixesCollections.getSuffixesCollectionByName("AUDIO");
        Assertions.assertTrue(audios.isPresent());

        if (audios.isPresent()) {
            String actualSuffixes = audios.get().getSuffixesAsDelimitedString(", ");
            String expectedSuffixes = "mp3, mpa, ogg";
            Assertions.assertEquals(expectedSuffixes, actualSuffixes);
        } else {
            Assertions.fail();
        }
    }

    @Test
    void standardFile_StoreTest() throws IOException {
        db = new FromFileSuffixesDbImpl(inputSuffixesDb);
        CollectionOfSuffixesCollections suffixesCollections = db.load();

        Path outputFilePath = rootFolder.resolve("suffixesdb_store.txt");

        db = new FromFileSuffixesDbImpl(outputFilePath);
        db.store(suffixesCollections);

        Assertions.assertTrue(FileUtils.contentEqualsIgnoreEOL(inputSuffixesDb.toFile(), outputFilePath.toFile(), null));

        Files.deleteIfExists(outputFilePath);
    }


}
