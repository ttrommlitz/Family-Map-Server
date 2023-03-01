package result;

import model.Person;
/** Contains the Result of attempting to
 * get one Person or all Persons associated
 * with a User **/
public class PersonResult extends Result {
    /** Username of User associated with
     * returned Person **/
    private String associatedUsername;

    /** ID of returned Person **/
    private String personID;

    /** First name of returned Person **/
    private String firstName;

    /** Last name of returned Person **/
    private String lastName;

    /** Gender of returned Person **/
    private String gender;

    /** ID of the father of returned Person.
     * Optional: can be null **/
    private String fatherID;

    /** ID of the mother of returned Person.
     * Optional: can be null **/
    private String motherID;

    /** ID of the spouse of returned Person.
     * Optional: can be null **/
    private String spouseID;

    private Person[] data;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getFatherID() {
        return fatherID;
    }

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }
}
