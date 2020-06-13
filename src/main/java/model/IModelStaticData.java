package model;

import model.simplemodel.SimpleModelSuffixesDb;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

public interface IModelStaticData {

    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setCurrentSuffixesCollection(ISuffixesCollection newSuffixesCollection);
    ISuffixesCollection getCurrentSuffixesCollection();
    SimpleModelSuffixesDb getSuffixesDb();
    void addNewPredefinedSuffixesCollection(ISuffixesCollection newPredefinedSuffixesCollection);
    Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(String name);
}
