package app.model.simplemodel.staticdata;

import app.model.ISuffixesCollection;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.simplemodel.SuffixesCollectionImpl;
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
    private CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData;
    private final ISuffixesDb fromSuffixesDb;

    private ISuffixesCollection currentSuffixesCollection;

    private FileOperationEnum currentFileOperation;

    protected AbstractIModelStaticData(@NotNull ISuffixesDb fromSuffixesDb) {
        inputFolder = getCurrentWorkingFolder();
        outputFolder = inputFolder.resolve(DEFAULT_OUTPUT_FOLDER_NAME);

        this.fromSuffixesDb = fromSuffixesDb;
        collectionOfSuffixesCollectionsStaticData = fromSuffixesDb.load();

        initializeCurrentSuffixes(collectionOfSuffixesCollectionsStaticData);

        this.currentFileOperation = FileOperationEnum.COPY;
    }

    private static Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    private void initializeCurrentSuffixes(CollectionOfSuffixesCollectionsStaticData suffixesDb) {
        if (!suffixesDb.isEmpty()) {
            currentSuffixesCollection = suffixesDb.getFirst();
        } else {
            currentSuffixesCollection = new SuffixesCollectionImpl(DEFAULT_SUFFIXES_COLLECTION_NAME);
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
    public void setCurrentSuffixesCollection(@NotNull ISuffixesCollection newSuffixesCollection) {
        currentSuffixesCollection = newSuffixesCollection;
    }

    @Override
    public @NotNull ISuffixesCollection getCurrentSuffixesCollection() {
        return currentSuffixesCollection;
    }

    @Override
    public CollectionOfSuffixesCollectionsStaticData getCollectionOfSuffixesCollectionsStaticData() {
        return collectionOfSuffixesCollectionsStaticData;
    }

    @Override
    public void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection) {
        collectionOfSuffixesCollectionsStaticData.updateSuffixesCollectionOrAddNewOne(newPredefinedSuffixesCollection);
        persistSuffixesDbIfSupported();
    }

    private void persistSuffixesDbIfSupported() {
        if (fromSuffixesDb.isPersistent()) {
            logger.debug("DB supports persistence - storing whole collection into db.");
            fromSuffixesDb.store(collectionOfSuffixesCollectionsStaticData);
        }
    }

    @Override
    public void removePredefinedSuffixesCollection(@NotNull String name) {
        collectionOfSuffixesCollectionsStaticData.removeSuffixesCollectionIfExists(name);

    }

    @Override
    public Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(@NotNull String name) {
        return collectionOfSuffixesCollectionsStaticData.getSuffixesCollectionByName(name);
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