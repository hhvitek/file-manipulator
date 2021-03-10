package app.model.simplemodel.staticdata.jdbcdqlite;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

public class SqliteManager {

    private static final Logger logger = LoggerFactory.getLogger(SqliteManager.class);

    private Connection conn;
    private Path dbPath;

    private SqliteManager() {
    }

    private SqliteManager(@NotNull Path dbPath) {
        this.dbPath = dbPath;
    }

    public static SqliteManager create(@NotNull Path dbPath) throws DbConnectionErrorException {
        return new SqliteManager(dbPath);
    }

    private void checkDbFileExist() throws DbConnectionErrorException {
        if (dbPath != null && Files.isRegularFile(dbPath)) {
            return;
        } else {
            throw new DbConnectionErrorException("Database file does not exist." + dbPath);
        }
    }

    private void createDbStructureIfNotExist() {
        conn = initConnection(dbPath);

        String createSqlQuery = null;
        try {
            createSqlQuery = getCreateSqliteScriptFromResource();
        } catch (IOException e) {
            throw new DbConnectionErrorException("Failed to retrieve sql script to create db.", e);
        }

        try (PreparedStatement preparedStatement = conn.prepareStatement(createSqlQuery)){
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DbConnectionErrorException("SqlException error during working with DB", ex);
        }
    }

    private String getCreateSqliteScriptFromResource() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream is = classLoader.getResourceAsStream("app/model/simplemodel/suffixesdb/create_sqlite_db.sql");

        String createDbSql = convertInputStreamIntoString(is);
        return createDbSql;
    }

    private String convertInputStreamIntoString(InputStream is) throws IOException {
        try(BufferedReader bufferedReader =
                    new BufferedReader(
                        new InputStreamReader(is
                        )
                    )
        ) {
            return convertBufferedReaderIntoString(bufferedReader);
        }
    }

    private String convertBufferedReaderIntoString(BufferedReader bf) throws IOException {
        StringBuilder builder = new StringBuilder();

        String line;
        while((line = bf.readLine()) != null) {
            builder.append(line);
        }

        return builder.toString();
    }

    private Connection initConnection(@NotNull Path dbPath) throws DbConnectionErrorException {
        if (this.conn != null) {
            logger.warn("The db connection has already been established. Closing the established connection.");
            closeConnection();
        }

        String dbUrl = "jdbc:sqlite" + dbPath.toAbsolutePath();

        try {
            this.conn = DriverManager.getConnection(dbUrl, null, null);
            return conn;
        } catch (SQLException e) {
            String errorMessage = "Failed to create any connection to the DB: " + dbPath;
            logger.error(errorMessage, e);
            throw new DbConnectionErrorException(errorMessage, e);
        }
    }

    private void closeConnection() throws DbConnectionErrorException {
        if (this.conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                String errorMessage = "Failed to close the connection to the DB: " + dbPath;
                logger.error(errorMessage, e);
                throw new DbConnectionErrorException(errorMessage, e);
            }
        }
    }


}