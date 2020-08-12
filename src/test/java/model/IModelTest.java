package model;

import model.jobs.Job;
import model.simplemodel.PredefinedSuffixesEnum;
import model.simplemodel.SuffixesCollectionImpl;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class IModelTest {

    protected Model model;

    static Path INPUT_FOLDER =
            Paths.get("src", "test", "resources", "model", "jobs", "test_input_folder");

    static Path OUTPUT_FOLDER =
            INPUT_FOLDER.resolve("OUTPUT");

    static void recreateTestingInputFolder() throws IOException {
        deleteDirectoryIfExistsWithAllFiles(INPUT_FOLDER);
        Files.createDirectory(INPUT_FOLDER);
        createFileInInputFolder("file1.mp3");
        createFileInInputFolder("file2.txt");
        createFileInInputFolder("file3.mp4");
        createFileInInputFolder("file4.ogg");
    }

    static void deleteDirectoryIfExistsWithAllFiles(Path folder) throws IOException {
        FileUtils.deleteDirectory(folder.toFile());
    }

    static void createFileInInputFolder(String newFileName) throws IOException {
        Files.createFile(INPUT_FOLDER.resolve(newFileName));
    }

    @Test
    void defaultInputAndOutputFolders_DefaultsToCurrentFolderTest() {
        Path inputFile = getCurrentWorkingFolder();
        Path outputFile = inputFile.resolve("OUTPUT");

        Assertions.assertEquals(inputFile, model.getInputFolder());
        Assertions.assertEquals(outputFile, model.getOutputFolder());
    }

    private Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    @Test
    void numberOfPredefinedSuffixes_EqualsFourTest() {
        Assertions.assertEquals(4, model.getPredefinedFileSuffixesDb().size());
    }

    @Test
    void defaultPredefinedSuffixes_EqualsFirstPredefinedSuffixesTest() {
        ISuffixesCollection expectedSuffixes = PredefinedSuffixesEnum.AUDIO.getSuffixes();
        Assertions.assertEquals(expectedSuffixes, model.getSuffixes());
    }

    @Test
    void createJobWithDefaultParameters_isNotRunning() throws InterruptedException, IOException {
        recreateTestingInputFolder();

        model.setInputFolder(INPUT_FOLDER);
        model.setOutputFolder(OUTPUT_FOLDER);

        ISuffixesCollection suffixes = new SuffixesCollectionImpl();
        suffixes.addSuffixes("mp3, ogg", ",");
        model.setSuffixes(suffixes);

        Job createdJob = model.createJobAsyncWithDefaultParameters();

        Thread.sleep(2000);

        //Assertions.assertTrue(createdJob.isStarted());
        Assertions.assertTrue(createdJob.isFinished());
        Assertions.assertFalse(createdJob.isRunning());

        Path expectedFileExists = model.getOutputFolder().resolve("file1.mp3").toAbsolutePath();
        Path expectedSecondFileExists = model.getOutputFolder().resolve("file4.ogg").toAbsolutePath();
        Assertions.assertTrue(Files.exists(expectedFileExists));
        Assertions.assertTrue(Files.exists(expectedSecondFileExists));

        deleteDirectoryIfExistsWithAllFiles(OUTPUT_FOLDER);
    }

}
