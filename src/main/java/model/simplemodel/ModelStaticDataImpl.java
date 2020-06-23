package model.simplemodel;

import model.IModelStaticData;
import model.ISuffixesDb;
import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ModelStaticDataImpl implements IModelStaticData {

    private static final String DEFAULT_OUTPUT_FOLDER_NAME = "OUTPUT";
    private static final String DEFAULT_SUFFIXES_COLLECTION_NAME = "DEFAULT_SUFFIXES_COLLECTION";

    private Path inputFolder;
    private Path outputFolder;
    private CollectionOfSuffixesCollections suffixesDb;
    private ISuffixesCollection currentSuffixesCollection;

    public ModelStaticDataImpl() {
        inputFolder = getCurrentWorkingFolder();
        outputFolder = inputFolder.resolve(DEFAULT_OUTPUT_FOLDER_NAME);
        initializeSuffixesDb();
        initializeCurrentSuffixes(suffixesDb);
    }

    private static Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    private void initializeSuffixesDb() {
        ISuffixesDb loader = new SuffixesDbImpl();
        suffixesDb = loader.load();
    }

    private void initializeCurrentSuffixes(CollectionOfSuffixesCollections suffixesDb) {
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
    public @Nullable ISuffixesCollection getCurrentSuffixesCollection() {
        return currentSuffixesCollection;
    }

    @Override
    public CollectionOfSuffixesCollections getSuffixesDb() {
        return suffixesDb;
    }

    @Override
    public void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection) {
        suffixesDb.addNewSuffixesCollection(newPredefinedSuffixesCollection);
    }

    @Override
    public Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(String name) {
        return suffixesDb.getSuffixesCollectionByName(name);
    }
}
