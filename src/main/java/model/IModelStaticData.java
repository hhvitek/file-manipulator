package model;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

public interface IModelStaticData {

    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setFileSuffixes(ISuffixesCategory newSuffixes);
    ISuffixesCategory getFileSuffixes();
    SimpleModelSuffixesCategoriesDb getPredefinedFileSuffixesCategories();
    void addNewPredefinedFileSuffixesCategory(ISuffixesCategory newPredefinedSuffixesCategory);
    Optional<ISuffixesCategory> getPredefinesFileSuffixesByCategoryName(String categoryName);
}
