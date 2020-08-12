package model.simplemodel.suffixesdb;

import model.ISuffixesCollection;
import model.simplemodel.CollectionOfSuffixesCollections;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Disabled
class FromSqliteDbSuffixesDbImplTest {

    ISuffixesDb db;
    static Path rootFolder;
    static Path inputSuffixesDb;
    static String sqlUrl;

    @BeforeAll
    static void initTestRootFolderTestFiles() {
        testRootFolder();
        checkInputSuffixesDbFile();
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

    static void checkInputSuffixesDbFile() {
        inputSuffixesDb = rootFolder.resolve("suffixesdb_load.sqlite3");
        sqlUrl = "jdbc:sqlite:" + inputSuffixesDb.toAbsolutePath();

        if (!Files.isRegularFile(inputSuffixesDb)) {
            Assertions.fail("The test sqlite database doesnt exist. Please create one! There should be .sql script.");
        }
    }

    @AfterAll
    static void cleanTestFolder() {
        if (inputSuffixesDb != null && Files.isRegularFile(inputSuffixesDb)) {

        }
    }


    @Test
    void sqliteStoreAndLoadTest() throws IOException {

        db = new FromSqliteDbSuffixesDbImpl(sqlUrl + ".out.sqlite3");

        ISuffixesDb enumDb = new FromPredefinedSuffixesEnumSuffixesDbImpl();
        CollectionOfSuffixesCollections collectionOfSuffixesCollections = enumDb.load();

        db.store(collectionOfSuffixesCollections);

        CollectionOfSuffixesCollections collectionOfSuffixesCollections1 = db.load();
        Assertions.assertTrue(collectionOfSuffixesCollections1.size() == 4);

        Files.deleteIfExists(inputSuffixesDb.resolveSibling("suffixesdb_load.sqlite3.out.sqlite3"));
    }

    @Test
    void sqliteLoadTest() {
        db = new FromSqliteDbSuffixesDbImpl(sqlUrl);

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
}
