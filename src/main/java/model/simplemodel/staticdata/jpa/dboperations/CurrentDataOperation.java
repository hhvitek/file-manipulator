package model.simplemodel.staticdata.jpa.dboperations;

import model.simplemodel.staticdata.jpa.entities.CurrentDataEntity;
import model.simplemodel.staticdata.jpa.entities.CurrentDataRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CurrentDataOperation {

    private static final Logger logger = LoggerFactory.getLogger(CurrentDataOperation.class);

    private static final String DEFAULT_OUTPUT_FOLDER_NAME = "OUTPUT";

    private final CurrentDataRepository currentDataRepository;

    public CurrentDataOperation(@NotNull EntityManager entityManager) {
        currentDataRepository = new CurrentDataRepository(entityManager);
    }

    public void setCurrentDataEntity(@NotNull String name, @NotNull String value) {
        CurrentDataEntity currentDataEntity = new CurrentDataEntity(name, value);
        currentDataRepository.update(currentDataEntity);
    }

    public CurrentDataEntity getCurrentDataEntity(@NotNull String name) throws ElemNotFoundException {
        return currentDataRepository.findOneById(name);
    }

    public Path getPath(@NotNull String name, @Nullable Path returnIfNotFound) {
        try {
            CurrentDataEntity entity = getCurrentDataEntity(name);
            String value = entity.getValue();
            return convertStringPathIntoPathIfFailedReturnDefaultPath(value, returnIfNotFound);
        } catch (ElemNotFoundException e) {
            return returnIfNotFound;
        }
    }

    private Path convertStringPathIntoPathIfFailedReturnDefaultPath(@NotNull String stringPath, @Nullable Path defaultPath) {
        try {
            return Path.of(stringPath);
        } catch (InvalidPathException ex) {
            return defaultPath;
        }
    }

    public void storePath(@NotNull String name, @NotNull Path path) {
        setCurrentDataEntity(name, path.toAbsolutePath().toString());
    }

    public Path getDefaultInputFolder() {
        return getCurrentWorkingFolder();
    }

    private Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    public Path getDefaultOutputFolder() {
        return getDefaultInputFolder().resolve(DEFAULT_OUTPUT_FOLDER_NAME);
    }

    public String getSuffixesCollectionName() throws ElemNotFoundException {
        CurrentDataEntity entity = getCurrentDataEntity("suffixes_collection");
        return entity.getValue();
    }

    public String getCollectionId() throws ElemNotFoundException {
        CurrentDataEntity entity = getCurrentDataEntity("collection_id");
        return entity.getValue();
    }

    public void setSuffixesCollection(@NotNull String name) {
        setCurrentDataEntity("suffixes_collection", name);
    }

    public void setCollection(@NotNull String id) {
        setCurrentDataEntity("collection_id", id);
    }
}
