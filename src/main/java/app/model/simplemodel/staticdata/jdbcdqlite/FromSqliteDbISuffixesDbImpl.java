package app.model.simplemodel.staticdata.jdbcdqlite;

import app.model.ISuffixes;
import app.model.SuffixesDbException;
import app.model.simplemodel.CollectionOfSuffixesStaticData;
import app.model.simplemodel.SuffixesImpl;
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
    public CollectionOfSuffixesStaticData load() throws SuffixesDbException {
        initConnection(dbUrl);
        CollectionOfSuffixesStaticData collectionOfSuffixesStaticData = selectAll();
        closeConnection();
        return collectionOfSuffixesStaticData;
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

    private CollectionOfSuffixesStaticData selectAll() throws DbConnectionErrorException {
        String query = String.format(
                "SELECT name, suffixes FROM %s;",
                DB_TABLENAME
        );

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)){
            ResultSet rs = preparedStatement.executeQuery();
            return getCollectionOfSuffixessFromResultSet(rs);
        } catch (SQLException ex) {
            throw new DbConnectionErrorException("SqlException error during working with DB", ex);
        }
    }

    private CollectionOfSuffixesStaticData getCollectionOfSuffixessFromResultSet(ResultSet rs) throws SQLException {
        CollectionOfSuffixesStaticData collectionOfSuffixesStaticData = new CollectionOfSuffixesStaticData();
        while (rs.next()) {
            ISuffixes suffixes = getSuffixesFromResultSet(rs);
            collectionOfSuffixesStaticData.addNewSuffixesIfAbsent(suffixes);
        }
        return collectionOfSuffixesStaticData;
    }

    private ISuffixes getSuffixesFromResultSet(ResultSet rs) throws SQLException {
         String name = rs.getString("name");
         String suffixesString = rs.getString("suffixes");

         ISuffixes suffixes = new SuffixesImpl(name);
         suffixes.addSuffixes(suffixesString, ",");
         return suffixes;
    }

    @Override
    public void store(CollectionOfSuffixesStaticData collectionOfSuffixesStaticData) throws SuffixesDbException {
        initConnection(dbUrl);

        try {
            createTableIfNotExists();
            upsertTable(collectionOfSuffixesStaticData);
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

    private void upsertTable(CollectionOfSuffixesStaticData collectionOfSuffixesStaticData) throws SQLException {
        for(ISuffixes suffixes: collectionOfSuffixesStaticData) {
            upsertRow(suffixes);
        }
    }

    private void upsertRow(ISuffixes suffixes) throws SQLException {
        String query = String.format(
                "INSERT INTO %s %n" +
                    "(name, suffixes) %n" +
                    "VALUES (?, ?) %n" +
                "ON CONFLICT(name) DO UPDATE SET %n" +
                    "suffixes = excluded.suffixes",
                DB_TABLENAME
        );

        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            String name = suffixes.getName();
            String suffixesString = suffixes.getSuffixesAsDelimitedString(",");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, suffixesString);

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated != 1) {
                logger.warn(
                        "Something went wrong during DB insert/update. " +
                                "Rows updated: {}, Expected 1 row inserted/updated. " +
                                suffixesString,
                        rowsUpdated);
            }
        }
    }
}