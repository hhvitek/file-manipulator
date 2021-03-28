package app.model.simplemodel.staticdata;

import app.model.ISuffixes;
import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.SuffixesImpl;
import app.model.simplemodel.suffixesdb.ISuffixesDb;
import app.model.file_operations.FileOperationEnum;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class AbstractIModelStaticData implements IModelStaticData {

    private static final Logger logger = LoggerFactory.getLogger(AbstractIModelStaticData.class);

    private static final String DEFAULT_OUTPUT_FOLDER_NAME = "OUTPUT";
    private static final String DEFAULT_SUFFIXES_COLLECTION_NAME = "DEFAULT_SUFFIXES_COLLECTION";

    private Path inputFolder;
    private Path outputFolder;
    private CollectionOfSuffixesStaticData collectionOfSuffixesStaticData;
    private final ISuffixesDb fromSuffixesDb;

    private ISuffixes currentSuffixes;

    private FileOperationEnum currentFileOperation;

    protected AbstractIModelStaticData(@NotNull ISuffixesDb fromSuffixesDb) {
        inputFolder = getCurrentWorkingFolder();
        outputFolder = inputFolder.resolve(DEFAULT_OUTPUT_FOLDER_NAME);

        this.fromSuffixesDb = fromSuffixesDb;
        collectionOfSuffixesStaticData = fromSuffixesDb.load();

        initializeCurrentSuffixes(collectionOfSuffixesStaticData);

        this.currentFileOperation = FileOperationEnum.COPY;
    }

    private static Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    private void initializeCurrentSuffixes(CollectionOfSuffixesStaticData suffixesDb) {
        if (!suffixesDb.isEmpty()) {
            currentSuffixes = suffixesDb.getFirst();
        } else {
            currentSuffixes = new SuffixesImpl(DEFAULT_SUFFIXES_COLLECTION_NAME);
        }
    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {
        inputFolder = newInputFolder;
    }

    @Override
    public @NotNull Path getInputFolder() {
        return inputFolder;
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {
        outputFolder = newOutputFolder;
    }

    @Override
    public @NotNull Path getOutputFolder() {
        return outputFolder;
    }

    @Override
    public void setCurrentSuffixes(@NotNull ISuffixes newSuffixes) {
        currentSuffixes = newSuffixes;
    }

    @Override
    public @NotNull ISuffixes getCurrentSuffixes() {
        return currentSuffixes;
    }

    @Override
    public CollectionOfSuffixesStaticData getCollectionOfSuffixesStaticData() {
        return collectionOfSuffixesStaticData;
    }

    @Override
    public void addNewPredefinedSuffixes(@NotNull ISuffixes newPredefinedSuffixes) {
        collectionOfSuffixesStaticData.updateSuffixesOrAddNewOne(newPredefinedSuffixes);
        persistSuffixesDbIfSupported();
    }

    private void persistSuffixesDbIfSupported() {
        if (fromSuffixesDb.isPersistent()) {
            logger.debug("DB supports persistence - storing whole collection into db.");
            fromSuffixesDb.store(collectionOfSuffixesStaticData);
        }
    }

    @Override
    public void removePredefinedSuffixes(@NotNull String name) {
        collectionOfSuffixesStaticData.removeSuffixesIfExists(name);

    }

    @Override
    public Optional<ISuffixes> getPredefinedSuffixesByName(@NotNull String name) {
        return collectionOfSuffixesStaticData.getSuffixesByName(name);
    }

    @Override
    public @NotNull FileOperationEnum getCurrentOperation() {
        return currentFileOperation;
    }

    @Override
    public void setCurrentOperation(@NotNull FileOperationEnum newOperation) {
        currentFileOperation = newOperation;
    }
}