package dao;

import model.Authtoken;
import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Dao class to interact with Authtoken table  **/
public class AuthtokenDao extends Dao <Authtoken> {
    /**
     * Constructor that sets database connection
     * @param conn
     */
    public AuthtokenDao(Connection conn) {
        super(conn);
    }

    /**
     * Inserts an Authtoken into database
     * @param authtoken
     * @throws DataAccessException
     */
    @Override
    public void insert(Authtoken authtoken) throws DataAccessException {
        String sql = "INSERT INTO Authtoken (authtoken, username) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authtoken.getAuthtoken());
            stmt.setString(2, authtoken.getUsername());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting an Authtoken into the database");
        }
    }

    /**
     * Retrieve an Authtoken from the database
     * based off username
     * @param token
     * @return Authtoken
     * @throws DataAccessException
     */
    @Override
    public Authtoken find(String token) throws DataAccessException {
        Authtoken authtoken;
        ResultSet rs;
        String sql = "SELECT * FROM Authtoken WHERE authtoken = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, token);
            rs = stmt.executeQuery();
            if (rs.next()) {
                authtoken = new Authtoken(rs.getString("authtoken"), rs.getString("username"));
                return authtoken;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an Authtoken in the database");
        }
    }
}
