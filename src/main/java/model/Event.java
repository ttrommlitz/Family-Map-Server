package model;

import java.util.Objects;

/** Event class models an Event in the
 * Family Map. Mirrors Event table in database **/
public class Event {
    /** Primary key for Event table **/
    private String eventID;

    /** Foreign key referencing User table in
     * a one-to-many relationship. Represents
     * the User associated with each Event **/
    private String associatedUsername;

    /** Foreign key referencing Person table in
     * a one-to-many relationship. Represents
     * the Person associated with each Event **/
    private String personID;

    /** Numerical latitude of the location of an Event **/
    private Float latitude;

    /** Numerical longitude of the location of an Event **/
    private Float longitude;

    /** Country where an Event occurred **/
    private String country;

    /** City where an Event occurred **/
    private String city;

    /** Type of Event **/
    private String eventType;

    /** Year in which an Event occurred **/
    private Integer year;

    /** Empty default constructor that sets all
     * private fields to null **/
    public Event() {}

    /**
     * Constructor that sets all
     * private fields to argument values
     * @param eventID
     * @param associatedUsername
     * @param personID
     * @param latitude
     * @param longitude
     * @param country
     * @param city
     * @param eventType
     * @param year
     */
    public Event(String eventID, String associatedUsername, String personID, Float latitude, Float longitude, String country, String city, String eventType, Integer year) {
        this.eventID = eventID;
        this.associatedUsername = associatedUsername;
        this.personID = personID;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city  = city;
        this.eventType = eventType;
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
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

    public Float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getEventType() {
        return eventType;
    }

    public Integer getYear() {
        return year;
    }

    /**
     * Overriden equals method to determine
     * if 2 Event objects are equal
     * @param o
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventID, event.eventID)
                && Objects.equals(associatedUsername, event.associatedUsername)
                && Objects.equals(personID, event.personID)
                && Objects.equals(latitude, event.latitude)
                && Objects.equals(longitude, event.longitude)
                && Objects.equals(country, event.country)
                && Objects.equals(city, event.city)
                && Objects.equals(eventType, event.eventType)
                && Objects.equals(year, event.year);
    }
}
