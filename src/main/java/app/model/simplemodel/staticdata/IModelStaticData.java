package app.model.simplemodel.staticdata;

import app.model.ISuffixesCollection;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.file_operations.FileOperationEnum;
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

    CollectionOfSuffixesCollectionsStaticData getCollectionOfSuffixesCollectionsStaticData();

    void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection);
    void removePredefinedSuffixesCollection(@NotNull String name);

    Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(@NotNull String name);

    @NotNull FileOperationEnum getCurrentOperation();
    void setCurrentOperation(@NotNull FileOperationEnum newOperation);
}