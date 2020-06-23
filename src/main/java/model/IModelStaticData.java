package model;

import model.simplemodel.CollectionOfSuffixesCollections;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.Optional;

public interface IModelStaticData {

    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setCurrentSuffixesCollection(@NotNull ISuffixesCollection newSuffixesCollection);
    @Nullable ISuffixesCollection getCurrentSuffixesCollection();
    CollectionOfSuffixesCollections getSuffixesDb();
    void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection);
    Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(String name);
}
