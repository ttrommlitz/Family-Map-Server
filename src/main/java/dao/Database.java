package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Database class to establash and close database connections **/
public class Database {
    /** Connection to database to be utilized by Data Access Objects **/
    private Connection conn;

    // Whenever we want to make a change to our database we will have to open a connection and use
    // Statements created by that connection to initiate transactions

    /**
     * Open a connection to database
     * @return Connection
     * @throws DataAccessException
     */
    public Connection openConnection() throws DataAccessException {
        try {
            System.out.println("Connection opened!");
            // The Structure for this Connection is driver:language:path
            // The path assumes you start in the root of your project unless given a full file path
            final String CONNECTION_URL = "jdbc:sqlite:Database/database.db";

            // Open a database connection to the file given in the path
            conn = DriverManager.getConnection(CONNECTION_URL);

            // Start a transaction
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open connection to database");
        }

        return conn;
    }

    /**
     * Retrieve the connection to the database
     * @return Connection
     * @throws DataAccessException
     */
    public Connection getConnection() throws DataAccessException {
        if (conn == null) {
            return openConnection();
        } else {
            return conn;
        }
    }

    /**
     * Close the connection to database
     * @param commit
     */
    public void closeConnection(boolean commit) {
        try {
            if (commit) {
                // This will commit the changes to the database
                conn.commit();
            } else {
                // If we find out something went wrong, pass a false into closeConnection and this
                // will rollback any changes we made during this connection
                conn.rollback();
            }
            conn.close();
            conn = null;
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
