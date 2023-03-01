package result;

/** Contains the Result of attempting to
 * log in a User **/
public class LoginResult extends Result {
    /** Authtoken of logged-in User **/
    private String authtoken;

    /** Username of logged-in User **/
    private String username;

    /** Password of logged-in User **/
    private String personID;

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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
