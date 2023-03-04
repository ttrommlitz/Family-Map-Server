package services;

import model.*;
import request.LoadRequest;
import result.ClearResult;
import result.LoadResult;
import dao.*;

/** Service to load database with data
 * from the Request body **/
public class LoadService extends Service {
    /**
     * Loads the User, Person, and Event data from
     * Request into the database for the current User.
     * If data already exists, it is overwritten
     * @param request
     * @return LoadResult
     */
    public LoadResult load(LoadRequest request) {
        User[] users = request.getUsers();
        Person[] persons = request.getPersons();
        Event[] events = request.getEvents();
        UserDao userDao = new UserDao(conn);
        PersonDao personDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);

        LoadResult result = new LoadResult();
        try {
            ClearService clearService = new ClearService();
            ClearResult clearResult = clearService.clear(true);
            if (!clearResult.isSuccess()) {
                result.setMessage(clearResult.getMessage());
                result.setSuccess(false);
                db.closeConnection(true);
                return result;
            }
            for (User user : users) {
                userDao.insert(user);
            }
            for (Person person : persons) {
                personDao.insert(person);
            }
            for (Event event : events) {
                eventDao.insert(event);
            }
            String message = "Successfully added " + users.length + " users, " + persons.length +
                    " persons, and " + events.length + " events to the database.";
            result.setMessage(message);
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            handleException(e, result);
        }
        return result;
    }
}
