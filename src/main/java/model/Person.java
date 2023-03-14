package model;

import java.util.Objects;

/** Person class models a Person in the
 * Family Map. Mirrors Person table in database **/
public class Person {
    /** Primary key for Person table **/
    private String personID;

    /** Foreign key referencing User table in
     *  a one-to-many relationship. Represents
     *  the User associated with each Person **/
    private String associatedUsername;

    /** First name for a Person **/
    private String firstName;

    /** Last name for a Person **/
    private String lastName;

    /** The specific gender for a Person
     * (can be 'm' or 'f') **/
    private String gender;

    /** Foreign key referencing father Person
     * in database **/
    private String fatherID;

    /** Foreign key referencing Mother Person
     * in database **/
    private String motherID;

    /** Foreign key referencing spouse Person
     * in database **/
    private String spouseID;

    /** Empty default constructor that sets all
     * private fields to null **/
    public Person() {}

    /**
     * Constructor that sets all
     * private fields to argument values
     * @param personID
     * @param associatedUsername
     * @param firstName
     * @param lastName
     * @param gender
     * @param fatherID
     * @param motherID
     * @param spouseID
     */
    public Person(String personID, String associatedUsername, String firstName, String lastName, String gender, String fatherID, String motherID, String spouseID) {
        this.personID = personID;
        this.associatedUsername = associatedUsername;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.fatherID = fatherID;
        this.motherID = motherID;
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
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

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    /**
     * Overriden equals method to determine
     * if 2 Person objects are equal
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(personID, person.personID)
                && Objects.equals(associatedUsername, person.associatedUsername)
                && Objects.equals(firstName, person.firstName)
                && Objects.equals(lastName, person.lastName)
                && Objects.equals(gender, person.gender)
                && Objects.equals(fatherID, person.fatherID)
                && Objects.equals(motherID, person.motherID)
                && Objects.equals(spouseID, person.spouseID);
    }
}
