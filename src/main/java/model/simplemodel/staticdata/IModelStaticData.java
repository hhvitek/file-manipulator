package model.simplemodel.staticdata;

import model.ISuffixesCollection;
import model.simplemodel.CollectionOfSuffixesCollections;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

public interface IModelStaticData {

    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setCurrentSuffixesCollection(@NotNull ISuffixesCollection newSuffixesCollection);
    @NotNull ISuffixesCollection getCurrentSuffixesCollection();
    CollectionOfSuffixesCollections getSuffixesDb();
    void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection);
    void removePredefinedSuffixesCollection(@NotNull String name);
    Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(@NotNull String name);
}
