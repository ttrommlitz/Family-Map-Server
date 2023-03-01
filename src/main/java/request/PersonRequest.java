package request;

/** Contains the HTTP GET Request
 * for getting a Person or Persons
 * associated with a User **/
public class PersonRequest {
    String authtoken;
    String personID;

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
