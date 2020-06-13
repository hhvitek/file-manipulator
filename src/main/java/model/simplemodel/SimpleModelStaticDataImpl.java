package model.simplemodel;

import model.IModelStaticData;
import model.IPredefinedSuffixesLoader;
import model.ISuffixesCollection;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class SimpleModelStaticDataImpl implements IModelStaticData {

    private static final String DEFAULT_OUTPUT_FOLDER_NAME = "OUTPUT";

    private Path inputFolder;
    private Path outputFolder;
    private SimpleModelSuffixesDb suffixesDb;
    private ISuffixesCollection currentSuffixesCollection;

    public SimpleModelStaticDataImpl() {
        inputFolder = getCurrentWorkingFolder();
        outputFolder = inputFolder.resolve(DEFAULT_OUTPUT_FOLDER_NAME);
        initializeSuffixesDb();
        initializeCurrentSuffixes();
    }

    private static Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    private void initializeSuffixesDb() {
        IPredefinedSuffixesLoader loader = new SimpleModelPredefinesSuffixesLoaderImpl();
        suffixesDb = loader.load();
    }

    private void initializeCurrentSuffixes() {
        currentSuffixesCollection = suffixesDb.getFirstSuffixesCollection();
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
    public void setCurrentSuffixesCollection(ISuffixesCollection newSuffixesCollection) {
        currentSuffixesCollection = newSuffixesCollection;
    }

    @Override
    public ISuffixesCollection getCurrentSuffixesCollection() {
        return currentSuffixesCollection;
    }

    @Override
    public SimpleModelSuffixesDb getSuffixesDb() {
        return suffixesDb;
    }

    @Override
    public void addNewPredefinedSuffixesCollection(ISuffixesCollection newPredefinedSuffixesCollection) {
        suffixesDb.addNewSuffixesCollection(newPredefinedSuffixesCollection);
    }

    @Override
    public Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(String name) {
        return suffixesDb.getSuffixesCollectionByName(name);
    }
}
