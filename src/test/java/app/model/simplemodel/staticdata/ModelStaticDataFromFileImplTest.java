package app.model.simplemodel.staticdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

class ModelStaticDataFromFileImplTest {

    private ModelStaticDataFromFileImpl modelStaticDataFromFile = new ModelStaticDataFromFileImpl();

    @Test
    public void defaultStaticPathToSuffixesDbExists() {
        Path defaultPath = ModelStaticDataFromFileImpl.DEFAULT_PATH;

        Assertions.assertTrue(Files.exists(defaultPath), "Default path not found in resources: " + defaultPath);
        Assertions.assertTrue(Files.isRegularFile(defaultPath), "Default path exists but it is not a regular file..." + defaultPath);
    }
}