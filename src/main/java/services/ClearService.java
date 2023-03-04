package services;

import dao.*;
import result.ClearResult;

/** Service to delete ALL data from database, including
 * User, Authtoken, Event, and Person data **/
public class ClearService extends Service {
    /**
     * Contains the logic for clearing all
     * data from database
     * @return ClearResult
     */
    public ClearResult clear(boolean beingCalledByOtherService) {
        ClearResult result = new ClearResult();
        AuthtokenDao authDao = new AuthtokenDao(conn);
        EventDao eventDao = new EventDao(conn);
        PersonDao personDao = new PersonDao(conn);
        UserDao userDao = new UserDao(conn);
        try {
            authDao.clear("Authtoken");
            eventDao.clear("Event");
            personDao.clear("Person");
            userDao.clear("User");
            result.setMessage("Clear succeeded.");
            result.setSuccess(true);
            if (!beingCalledByOtherService) {
                db.closeConnection(true);
            }
        } catch (DataAccessException e) {
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
            if (!beingCalledByOtherService) {
                db.closeConnection(false);
            }
        }
        return result;
    }

    public void clearByUsername(String username) throws DataAccessException {
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        ClearResult result = new ClearResult();

        personDao.clearByUsername("Person", username);
        eventDao.clearByUsername("Event", username);
    }
}
