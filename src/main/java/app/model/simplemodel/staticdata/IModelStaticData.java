package app.model.simplemodel.staticdata;

import app.model.ISuffixes;
import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.file_operations.FileOperationEnum;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

public interface IModelStaticData {

    void setInputFolder(@NotNull Path newInputFolder);
    @NotNull Path getInputFolder();

    void setOutputFolder(@NotNull Path newOutputFolder);
    @NotNull Path getOutputFolder();

    void setCurrentSuffixes(@NotNull ISuffixes newSuffixes);
    @NotNull ISuffixes getCurrentSuffixes();

    CollectionOfSuffixesStaticData getCollectionOfSuffixesStaticData();

    void addNewPredefinedSuffixes(@NotNull ISuffixes newPredefinedSuffixes);
    void removePredefinedSuffixes(@NotNull String name);

    Optional<ISuffixes> getPredefinedSuffixesByName(@NotNull String name);

    @NotNull FileOperationEnum getCurrentOperation();
    void setCurrentOperation(@NotNull FileOperationEnum newOperation);
}