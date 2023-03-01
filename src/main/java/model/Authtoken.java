package model;

import java.util.Objects;

/** Authtoken class models an Authtoken in the
 * Family Map. Mirrors Authtoken table in database **/
public class Authtoken {

    /** Primary key for Authtoken table.
     * Generated when a new User is created **/
    private String authtoken;

    /** Foreign key referencing User table in
     *  a one-to-one relationship. Represents
     *  the User associated with each Authtoken **/
    private String username;

    /** Empty default constructor that sets all
     * private fields to null **/
    public Authtoken() {}

    /**
     * Constructor that sets all
     * private fields to argument values
     * @param authtoken
     * @param username
     */
    public Authtoken(String authtoken, String username) {
        this.authtoken = authtoken;
        this.username = username;
    }

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Overriden equals method to determine
     * if 2 Authtoken objects are equal
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Authtoken authtoken1 = (Authtoken) o;
        return Objects.equals(authtoken, authtoken1.authtoken)
                && Objects.equals(username, authtoken1.username);
    }
}
