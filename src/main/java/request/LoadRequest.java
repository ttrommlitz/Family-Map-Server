package request;

import model.Event;
import model.Person;
import model.User;

/** Contains the HTTP POST Request
 * for loading User, Person, and Event
 * data into database **/
public class LoadRequest {
    /** Array of User objects meant to
     * be loaded into database **/
    private User[] users;

    /** Array of Person objects meant to
     * be loaded into database **/
    private Person[] persons;

    /** Array of Event objects meant to
     * be loaded into database **/
    private Event[] events;

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
