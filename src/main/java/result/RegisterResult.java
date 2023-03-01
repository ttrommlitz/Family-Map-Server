package result;

/** Contains the Result of attempting to
 * register a new User **/
public class RegisterResult extends Result {
    /** Authtoken of registered User **/
    private String authtoken;

    /** Username of registered User **/
    private String username;

    /** ID of root Person associated with
     *  registered User **/
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
