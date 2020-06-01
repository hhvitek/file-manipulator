package model;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class SimpleModelStaticDataImpl implements IModelStaticData {

    private static final String DEFAULT_OUTPUT_FOLDER_NAME = "OUTPUT";

    private Path inputFolder;
    private Path outputFolder;
    private SimpleModelSuffixesCategoriesDb categoriesDb;
    private ISuffixesCategory currentSuffixes;

    public SimpleModelStaticDataImpl() {
        inputFolder = getCurrentWorkingFolder();
        outputFolder = inputFolder.resolve(DEFAULT_OUTPUT_FOLDER_NAME);
        initializePredefinedSuffixes();
        initializeCurrentSuffixes();
    }

    private Path getCurrentWorkingFolder() {
        String userDirProperty = System.getProperty("user.dir");
        return Paths.get(userDirProperty);
    }

    private void initializePredefinedSuffixes() {
        IPredefinedSuffixesLoader loader = new SimpleModelPredefinesSuffixesLoaderImpl();
        categoriesDb = loader.load();
    }

    private void initializeCurrentSuffixes() {
        if (categoriesDb.size() > 0) {
            currentSuffixes = categoriesDb.iterator().next();
        } else {
            currentSuffixes = new SimpleModelSuffixesCategoryImpl();
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
    public void setFileSuffixes(ISuffixesCategory newSuffixes) {
        currentSuffixes = newSuffixes;
    }

    @Override
    public ISuffixesCategory getFileSuffixes() {
        return currentSuffixes;
    }

    @Override
    public SimpleModelSuffixesCategoriesDb getPredefinedFileSuffixesCategories() {
        return categoriesDb;
    }

    @Override
    public void addNewPredefinedFileSuffixesCategory(ISuffixesCategory newPredefinedSuffixesCategories) {
        categoriesDb.addNewPredefinedFileSuffixesCategory(newPredefinedSuffixesCategories);
    }

    @Override
    public Optional<ISuffixesCategory> getPredefinesFileSuffixesByCategoryName(String categoryName) {
        return categoriesDb.getPredefinesFileSuffixesByCategoryName(categoryName);
    }
}
