package model;

import java.util.Objects;

/** User class models a Person in the
 * Family Map. Mirrors User table in database **/
public class User {
    /** Username for a User. Primary key
     * on User table **/
    private String username;

    /** Password for a User **/
    private String password;

    /** Email for a User **/
    private String email;

    /** First name for a User **/
    private String firstName;

    /** Last name for a User **/
    private String lastName;

    /** Gender for a User
     * (can be 'm' or 'f') **/
    private String gender;

    /** Foreign key referencing Person Table
     * representing root Person associated with
     * each User **/
    private String personID;

    /** Empty default constructor that sets all
     * private fields to null **/
    public User() {}

    /**
     * Constructor that sets all
     * private fields to argument values
     * @param username
     * @param password
     * @param email
     * @param firstName
     * @param lastName
     * @param gender
     * @param personID
     */
    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.username = username;
        this.password = password;
        this. email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * Overriden equals method to determine
     * if 2 User objects are equal
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username)
                && Objects.equals(password, user.password)
                && Objects.equals(email, user.email)
                && Objects.equals(firstName, user.firstName)
                && Objects.equals(lastName, user.lastName)
                && Objects.equals(gender, user.gender)
                && Objects.equals(personID, user.personID);
    }
}
