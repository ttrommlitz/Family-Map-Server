package dao;

import model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

/** Dao class to interact with Person table  **/
public class PersonDao extends Dao <Person> {
    /**
     * Constructor that sets database connection
     * @param conn
     */
    public PersonDao(Connection conn) {
        super(conn);
    }

    /**
     * Inserts a Person into database
     * @param person
     * @throws DataAccessException
     */
    @Override
    public void insert(Person person) throws DataAccessException {
        if (!Objects.equals(person.getGender(), "m") && !Objects.equals(person.getGender(), "f")) {
            throw new DataAccessException("Gender of Person must be \"m\" or \"f\".");
        }
        String sql = "INSERT INTO Person (personID, associatedUsername, firstName, lastName, gender, " +
                "fatherID, motherID, spouseID) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting a Person into the database");
        }
    }

    /**
     * Retrieve a Person from the database
     * based off personID
     * @param personID
     * @return Person
     * @throws DataAccessException
     */
    @Override
    public Person find(String personID) throws DataAccessException {
        Person person;
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE personID = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                return person;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding a Person in the database");
        }
    }

    /**
     * Retrieve all Persons associated with a User
     * @param associatedUsername
     * @return Person[]
     * @throws DataAccessException
     */
    public Person[] findAll(String associatedUsername) throws DataAccessException {
        Person person;
        ArrayList<Person> persons = new ArrayList<>();
        ResultSet rs;
        String sql = "SELECT * FROM Person WHERE associatedUsername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedUsername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personID"), rs.getString("associatedUsername"),
                        rs.getString("firstName"), rs.getString("lastName"), rs.getString("gender"),
                        rs.getString("fatherID"), rs.getString("motherID"), rs.getString("spouseID"));
                persons.add(person);
            }
            if (persons.isEmpty()) { return null; }
            Person[] result = new Person[persons.size()];
            for (int i = 0; i < persons.size(); i++) {
                result[i] = persons.get(i);
            }
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding all Persons associated with a username in the database");
        }
    }
}
