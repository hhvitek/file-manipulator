package app.model.simplemodel.staticdata.jpa.dboperations;

import app.model.file_operations.FileOperationEnum;
import app.model.simplemodel.staticdata.jpa.entities.CurrentDataEntity;
import app.model.simplemodel.staticdata.jpa.entities.CurrentDataRepository;
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

    private void setCurrentDataEntity(@NotNull String name, @NotNull String value) {
        CurrentDataEntity currentDataEntity = new CurrentDataEntity(name, value);
        currentDataRepository.merge(currentDataEntity);
    }

    private String getCurrentDataEntityValue(@NotNull String name) throws ElemNotFoundException {
        CurrentDataEntity entity = currentDataRepository.findOneById(name);
        return entity.getValue();
    }

    public Path getPath(@NotNull String name, @Nullable Path returnIfNotFound) {
        try {
            String value = getCurrentDataEntityValue(name);
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

    public String getSuffixesCollectionNameId() throws ElemNotFoundException {
        return getCurrentDataEntityValue("suffixes_collection");
    }

    public Integer getCollectionId() throws ElemNotFoundException {
        String value = getCurrentDataEntityValue("collection_id");
        try {
            Integer parsedId = Integer.valueOf(value);
            return parsedId;
        } catch (NumberFormatException e) {
            throw new ElemNotFoundException(e);
        }
    }

    public void setSuffixesCollection(@NotNull String name) {
        setCurrentDataEntity("suffixes_collection", name);
    }

    public void setCollection(@NotNull String id) {
        setCurrentDataEntity("collection_id", id);
    }

    public void setCurrentFileOperation(FileOperationEnum newFileOperation) {
        String stringifiedFileOperation = newFileOperation.name();
        setCurrentDataEntity("file_operation_name", stringifiedFileOperation);
    }

    public FileOperationEnum getCurrentFileOperation() {
        try {
            String value = getCurrentDataEntityValue("file_operation_name");
            return FileOperationEnum.valueOf(value);
        } catch (ElemNotFoundException | IllegalArgumentException e) {
            logger.error("No fileOperation has beet set. Initializing with default value.");
            setCurrentFileOperation(FileOperationEnum.COPY);
            return FileOperationEnum.COPY;
        }
    }
}