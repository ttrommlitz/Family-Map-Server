package result;

import model.Event;

/** Contains the Result of attempting to
 * get one Event or all Events associated
 * with a User **/
public class EventResult extends Result {
    /** Username of User associated with
     * returned Event **/
    private String associatedUsername;

    /** ID of returned Event **/
    private String eventID;

    /** ID of Person associated with
     * returned Event **/
    private String personID;

    /** Numerical latitude of the location of
     * returned Event **/
    private Float latitude;

    /** Numerical longitude of the location of
     * returned Event **/
    private Float longitude;

    /** Country where returned Event occurred **/
    private String country;

    /** City where returned Event occurred **/
    private String city;

    /** Type of returned Event **/
    private String eventType;

    /** Year in which returned Event occurred **/
    private Integer year;

    /** Array of all returned Events associated
     * with provided User **/
    private Event[] data;

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
