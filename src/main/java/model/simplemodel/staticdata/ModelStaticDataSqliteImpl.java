package model.simplemodel.staticdata;

import model.ISuffixesCollection;
import model.file_operations.FileOperationEnum;
import model.simplemodel.CollectionOfSuffixesCollections;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Optional;

/**
 * Implementation of simplemodel's data.
 *
 * Does represent data persistence using local sqlite database.
 *
 * Loads predefined CollectionOfSuffixesCollection from sqlite db.
 */
public class ModelStaticDataSqliteImpl implements IModelStaticData {

    private SqliteManager sqliteManager;

    public ModelStaticDataSqliteImpl(Path dbUrl) {
        sqliteManager = SqliteManager.create(dbUrl);
    }


    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {

    }

    @Override
    public @NotNull Path getInputFolder() {
        return null;
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {

    }

    @Override
    public @NotNull Path getOutputFolder() {
        return null;
    }

    @Override
    public void setCurrentSuffixesCollection(@NotNull ISuffixesCollection newSuffixesCollection) {

    }

    @Override
    public @NotNull ISuffixesCollection getCurrentSuffixesCollection() {
        return null;
    }

    @Override
    public CollectionOfSuffixesCollections getSuffixesDb() {
        return null;
    }

    @Override
    public void addNewPredefinedSuffixesCollection(@NotNull ISuffixesCollection newPredefinedSuffixesCollection) {

    }

    @Override
    public void removePredefinedSuffixesCollection(@NotNull String name) {

    }

    @Override
    public Optional<ISuffixesCollection> getPredefinedSuffixesCollectionByName(@NotNull String name) {
        return Optional.empty();
    }

    @Override
    public @NotNull FileOperationEnum getCurrentOperation() {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    @Override
    public void setCurrentOperation(@NotNull FileOperationEnum newOperation) {
        throw new UnsupportedOperationException("This operation is not supported.");
    }
}
