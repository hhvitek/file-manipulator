package model;

import model.simplemodel.SimpleModelSuffixesCategoriesDb;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

public interface IModelStaticData {

    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setFileSuffixes(ISuffixesCollection newSuffixes);
    ISuffixesCollection getFileSuffixes();
    SimpleModelSuffixesCategoriesDb getPredefinedFileSuffixesCategories();
    void addNewPredefinedFileSuffixesCategory(ISuffixesCollection newPredefinedSuffixesCategory);
    Optional<ISuffixesCollection> getPredefinesFileSuffixesByCategoryName(String categoryName);
}
