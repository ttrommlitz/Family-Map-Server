package services;

import dao.DataAccessException;
import dao.*;
import model.*;
import request.PersonRequest;
import result.PersonResult;

/** Service to get one or all Persons
 * associated with a User **/
public class PersonService extends AuthenticateService {
    private PersonDao personDao = new PersonDao(conn);
    private PersonResult result = new PersonResult();
    /**
     * Returns the Person with the specified ID (if
     * the Person is associated with the current User)
     * @param request
     * @return PersonResult
     */
    public PersonResult getPersonByID(PersonRequest request) {
        try {
            UserDao userDao = new UserDao(conn);
            User user = userDao.find(validateAuthtoken(request.getAuthtoken()));
            Person person = personDao.find(request.getPersonID());
            if (person == null) { throw new DataAccessException("No person exists with that id"); }
            if (!user.getUsername().equals(person.getAssociatedUsername())) {
                throw new DataAccessException("Person must be a relative of the user" +
                        " associated with the provided authtoken");
            }

            result.setAssociatedUsername(person.getAssociatedUsername());
            result.setPersonID(person.getPersonID());
            result.setPersonID(person.getPersonID());
            result.setFirstName(person.getFirstName());
            result.setLastName(person.getLastName());
            result.setGender(person.getGender());
            result.setPersonID(person.getPersonID());
            result.setFatherID(person.getFatherID());
            result.setMotherID(person.getMotherID());
            result.setSpouseID(person.getSpouseID());
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            handleException(e, result);
        }
        return result;
    }

    /**
     * Returns all Person family members
     * of the current User
     * @param request
     * @return PersonResult
     */
    public PersonResult getAllPersons(PersonRequest request) {
        try {
            Person[] persons = personDao.findAll(validateAuthtoken(request.getAuthtoken()));
            if (persons == null) {
                throw new DataAccessException("No persons belong to the User associated with" +
                        "the provided authtoken");
            }
            result.setData(persons);
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            handleException(e, result);
        }
        return result;
    }
}
