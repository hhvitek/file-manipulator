package app.model.simplemodel.staticdata.jdbcdqlite;

import app.model.ISuffixesCollection;
import app.model.SuffixesDbException;
import app.model.simplemodel.CollectionOfSuffixesCollectionsStaticData;
import app.model.simplemodel.SuffixesCollectionImpl;
import app.model.simplemodel.suffixesdb.ISuffixesDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 *
 * JDBC impklementation NOT implemented actually!!!
 */
public class FromSqliteDbISuffixesDbImpl implements ISuffixesDb {

    private static final Logger logger = LoggerFactory.getLogger(FromSqliteDbISuffixesDbImpl.class);
    private static final String DB_TABLENAME = "suffixes_db";

    private Connection conn;
    private String dbUrl;

    public FromSqliteDbISuffixesDbImpl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

    @Override
    public CollectionOfSuffixesCollectionsStaticData load() throws SuffixesDbException {
        initConnection(dbUrl);
        CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData = selectAll();
        closeConnection();
        return collectionOfSuffixesCollectionsStaticData;
    }

    private void initConnection(String dbUrl) throws DbConnectionErrorException {
        if (this.conn != null) {
            logger.warn("The db connection has already been established. Closing the established connection.");
            closeConnection();
        }

        this.dbUrl = dbUrl;

        try {
            this.conn = DriverManager.getConnection(dbUrl, null, null);
        } catch (SQLException e) {
            String errorMessage = "Failed to create any connection to the DB: " + dbUrl;
            logger.error(errorMessage, e);
            throw new DbConnectionErrorException(errorMessage, e);
        }
    }

    private void closeConnection() throws DbConnectionErrorException {
        if (this.conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                String errorMessage = "Failed to close the connection to the DB: " + dbUrl;
                logger.error(errorMessage, e);
                throw new DbConnectionErrorException(errorMessage, e);
            }
        }
    }

    private CollectionOfSuffixesCollectionsStaticData selectAll() throws DbConnectionErrorException {
        String query = String.format(
                "SELECT name, suffixes FROM %s;",
                DB_TABLENAME
        );

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            ResultSet rs = preparedStatement.executeQuery();
            return getCollectionOfSuffixesCollectionsFromResultSet(rs);
        } catch (SQLException ex) {
            throw new DbConnectionErrorException("SqlException error during working with DB", ex);
        }
    }

    private CollectionOfSuffixesCollectionsStaticData getCollectionOfSuffixesCollectionsFromResultSet(ResultSet rs) throws SQLException {
        CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData = new CollectionOfSuffixesCollectionsStaticData();
        while (rs.next()) {
            ISuffixesCollection suffixesCollection = getSuffixesCollectionFromResultSet(rs);
            collectionOfSuffixesCollectionsStaticData.addNewSuffixesCollectionIfAbsent(suffixesCollection);
        }
        return collectionOfSuffixesCollectionsStaticData;
    }

    private ISuffixesCollection getSuffixesCollectionFromResultSet(ResultSet rs) throws SQLException {
         String name = rs.getString("name");
         String suffixes = rs.getString("suffixes");

         ISuffixesCollection suffixesCollection = new SuffixesCollectionImpl(name);
         suffixesCollection.addSuffixes(suffixes, ",");
         return suffixesCollection;
    }

    @Override
    public void store(CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData) throws SuffixesDbException {
        initConnection(dbUrl);

        try {
            createTableIfNotExists();
            upsertTable(collectionOfSuffixesCollectionsStaticData);
        } catch (SQLException throwables) {
            String errorMessage = throwables.getLocalizedMessage();
            logger.error(errorMessage);
            throw new DbConnectionErrorException(errorMessage);
        }

        closeConnection();
    }

    private void createTableIfNotExists() throws SQLException {
        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s ( %n" +
                    "name TEXT PRIMARY KEY, %n" +
                    "suffixes TEXT NOT NULL %n" +
                ");",
                DB_TABLENAME
        );

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.execute();
        }
    }

    private void upsertTable(CollectionOfSuffixesCollectionsStaticData collectionOfSuffixesCollectionsStaticData) throws SQLException {
        for(ISuffixesCollection suffixesCollection: collectionOfSuffixesCollectionsStaticData) {
            upsertRow(suffixesCollection);
        }
    }

    private void upsertRow(ISuffixesCollection suffixesCollection) throws SQLException {
        String query = String.format(
                "INSERT INTO %s %n" +
                    "(name, suffixes) %n" +
                    "VALUES (?, ?) %n" +
                "ON CONFLICT(name) DO UPDATE SET %n" +
                    "suffixes = excluded.suffixes",
                DB_TABLENAME
        );

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            String name = suffixesCollection.getName();
            String suffixes = suffixesCollection.getSuffixesAsDelimitedString(",");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, suffixes);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated != 1) {
                logger.warn(
                        "Something went wrong during DB insert/update. " +
                                "Rows updated: {}, Expected 1 row inserted/updated. " +
                                suffixesCollection,
                        rowsUpdated);
            }
        }
    }
}