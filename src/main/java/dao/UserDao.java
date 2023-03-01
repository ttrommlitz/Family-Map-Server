package dao;

import com.google.gson.internal.bind.DateTypeAdapter;
import model.Event;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/** Dao class to interact with User table  **/
public class UserDao extends Dao <User> {
    /**
     * Constructor that sets database connection
     * @param conn
     */
    public UserDao(Connection conn) {
        super(conn);
    }

//    /** Update a User in database **/
//    public void updateUser(User user) throws DataAccessException {
//        String sql = "UPDATE User SET username = ?, password = ?, email = ?, firstName = ?, " +
//                "lastName = ?, gender = ?, personID = ? WHERE username = ?";
//        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//            prepareSQL(user, stmt);
//            stmt.setString(8, user.getUsername());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw new DataAccessException("Error encountered while updating a User in the database");
//        }
//    }

    /**
     * Insert a User into database
     * @param user
     * @throws DataAccessException
     */
    @Override
    public void insert(User user) throws DataAccessException {
        if (!Objects.equals(user.getGender(), "m") && !Objects.equals(user.getGender(), "f")) {
            throw new DataAccessException("Gender of Person must be \"m\" or \"f\".");
        }
        String sql = "INSERT INTO User (username, password, email, firstName, lastName, " +
                "gender, personID) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            prepareSQL(user, stmt);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e.getMessage());
        }
    }

    /**
     * Retrieve a User from the database
     * based off username
     * @param username
     * @return User
     * @throws DataAccessException
     */
    @Override
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs;
        String sql = "SELECT * FROM User WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstName"), rs.getString("lastName"),
                        rs.getString("gender"), rs.getString("personID"));
                return user;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding an event in the database");
        }
    }

    // helper
    private void prepareSQL(User user, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, user.getUsername());
        stmt.setString(2, user.getPassword());
        stmt.setString(3, user.getEmail());
        stmt.setString(4, user.getFirstName());
        stmt.setString(5, user.getLastName());
        stmt.setString(6, user.getGender());
        stmt.setString(7, user.getPersonID());
    }
}
