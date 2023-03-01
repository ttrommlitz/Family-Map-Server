package services;

import dao.DataAccessException;
import dao.*;
import model.*;
import request.EventRequest;
import result.EventResult;

/** Service to get one event or all Events
 * associated with a User **/
public class EventService extends AuthenticateService {
    private EventDao eventDao = new EventDao(conn);
    private EventResult result = new EventResult();
    /**
     * Returns the Event with the specified ID (if
     * the Event is associated with the current User)
     * @param request
     * @return EventResult
     */
    public EventResult getEventByID(EventRequest request) {
        try {
            Event event = eventDao.find(request.getEventID());
            if (event == null) { throw new DataAccessException("No event exists with that id"); }
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(validateAuthtoken(request.getAuthtoken()));
            if (!user.getUsername().equals(event.getAssociatedUsername())) {
                throw new DataAccessException("Event must belong to a relative of the user" +
                        " associated with the provided authtoken");
            }

            result.setAssociatedUsername(event.getAssociatedUsername());
            result.setEventID(event.getEventID());
            result.setPersonID(event.getPersonID());
            result.setLatitude(event.getLatitude());
            result.setLongitude(event.getLongitude());
            result.setCountry(event.getCountry());
            result.setCity(event.getCity());
            result.setEventType(event.getEventType());
            result.setYear(event.getYear());
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            handleException(e, result);
        }
        return result;
    }

    /**
     * Returns all Events for all family members
     * of the current User
     * @param request
     * @return EventRequest
     */
    public EventResult getAllEvents(EventRequest request) {
        try {
            Event[] events = eventDao.findAll(validateAuthtoken(request.getAuthtoken()));
            if (events == null) {
                throw new DataAccessException("No events belong to any family members of the User associated with" +
                        " the provided authtoken");
            }
            result.setData(events);
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            handleException(e, result);
        }
        return result;
    }
}
