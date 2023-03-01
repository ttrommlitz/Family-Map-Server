package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/** Base DAO class. Holds general insert, find,
 * and clear methods for children. **/
public abstract class Dao <T> {
    public Dao(Connection conn) {
        this.conn = conn;
    }
    // connect to database
    /** Shared database connection utilized by
     * child classes **/
    protected Connection conn;

    /**
     * Method to insert into database. Accepts a
     * templated object (User, Person, Event, Authtoken)
     * @param obj
     * @throws DataAccessException
     */
    public abstract void insert(T obj) throws DataAccessException;

    /**
     * Method to retrieve a templated object from
     * database (User, Person, Event, Authtoken)
     * @param primaryKey
     * @return T
     * @throws DataAccessException
     */
    public abstract T find(String primaryKey) throws DataAccessException;

    /**
     * Method to clear table in database
     * @param tableType
     * @throws DataAccessException
     */
    public void clear(String tableType) throws DataAccessException {
        String sql = "DELETE FROM " + tableType;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Method to clear rows by username in table
     * @param tableType
     * @param associatedUsername
     * @throws DataAccessException
     */
    public void clearByUsername(String tableType, String associatedUsername) throws DataAccessException {
        String param = "associatedUsername";
        if (tableType.equals("User") || tableType.equals("Authtoken")) { param = "username"; }
        String sql = "DELETE FROM " + tableType + " WHERE " + param + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.getMessage());
        }
    }
}
