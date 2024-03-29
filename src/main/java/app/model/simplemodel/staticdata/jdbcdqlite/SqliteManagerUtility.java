package app.model.simplemodel.staticdata.jdbcdqlite;

import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.SuffixesImpl;
import app.model.simplemodel.staticdata.IModelStaticData;
import app.model.ISuffixes;
import app.model.file_operations.FileOperationEnum;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SqliteManagerUtility implements IModelStaticData {

    private static final Logger logger = LoggerFactory.getLogger(SqliteManagerUtility.class);

    private final Connection conn;

    public SqliteManagerUtility(Connection connection) {
        this.conn = connection;
    }

    @Override
    public void setInputFolder(@NotNull Path newInputFolder) {
        setCurrentVariable("input_folder", newInputFolder.toAbsolutePath().toString());
    }

    private void setCurrentVariable(String variableName, String variableValue) {
        String query = "UPDATE current\n"
                + " SET name = '?'\n"
                + "     value = '?'\n";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, variableName);
            preparedStatement.setString(2, variableValue);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated != 1) {
                logger.warn(
                        "Something went wrong during DB insert/update. Rows updated: {}, Expected 1 row inserted/updated. ",
                        rowsUpdated);
                throw new DbConnectionErrorException("Failed to update database with: " + query);
            }
        } catch (SQLException ex) {
            throw new DbConnectionErrorException("SqlException error during working with DB", ex);
        }
    }

    @Override
    public @NotNull Path getInputFolder() {
        String inputFolder = getCurrentVariable("input_folder");
        return convertExpectedFolderIntoPath(inputFolder);
    }

    private Path convertExpectedFolderIntoPath(String expectedFolder) {
        if (expectedFolder != null) {
            return Path.of(expectedFolder);
        } else {
            return Path.of("");
        }
    }

    private String getCurrentVariable(String name) {
        String query = "SELECT value\n"
                + " FROM current\n"
                + " WHERE name = '?'\n";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString("value");
            }
        } catch (SQLException ex) {
            throw new DbConnectionErrorException("SqlException error during working with DB", ex);
        }
        return null;
    }

    @Override
    public void setOutputFolder(@NotNull Path newOutputFolder) {
        setCurrentVariable("output_folder", newOutputFolder.toAbsolutePath().toString());
    }

    @Override
    public @NotNull Path getOutputFolder() {
        String outputFolder = getCurrentVariable("output_folder");
        return convertExpectedFolderIntoPath(outputFolder);
    }

    @Override
    public void setCurrentSuffixes(@NotNull ISuffixes newSuffixes) {

    }

    @Override
    public @NotNull ISuffixes getCurrentSuffixes() {
        String suffixes = getCurrentVariable("suffixes");
        return null;
    }


    @Override
    public CollectionOfSuffixesStaticData getCollectionOfSuffixesStaticData() {
        return null;
    }

    @Override
    public void addNewPredefinedSuffixes(@NotNull ISuffixes newPredefinedSuffixes) {

    }

    @Override
    public void removePredefinedSuffixes(@NotNull String name) {

    }

    private CollectionOfSuffixesStaticData getCollectionOfSuffixessFromResultSet(ResultSet rs) throws SQLException {
        CollectionOfSuffixesStaticData collectionOfSuffixesStaticData = new CollectionOfSuffixesStaticData();
        while (rs.next()) {
            ISuffixes suffixes = getSuffixesFromResultSet(rs);
            collectionOfSuffixesStaticData.addNewSuffixesIfAbsent(suffixes);
        }
        return collectionOfSuffixesStaticData;
    }

    @Override
    public Optional<ISuffixes> getPredefinedSuffixesByName(@NotNull String name) {
        String query = "SELECT name,suffixes\n"
                + " FROM suffixes\n"
                + " WHERE name = '?'\n";

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return Optional.of(getSuffixesFromResultSet(rs));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DbConnectionErrorException("SqlException error during working with DB", ex);
        }
    }

    @Override
    public @NotNull FileOperationEnum getCurrentOperation() {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    @Override
    public void setCurrentOperation(@NotNull FileOperationEnum newOperation) {
        throw new UnsupportedOperationException("This operation is not supported.");
    }

    private ISuffixes getSuffixesFromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        String suffixesString = rs.getString("suffixes");

        ISuffixes suffixes = new SuffixesImpl(name);
        suffixes.addSuffixes(suffixesString, ",");
        return suffixes;
    }
}