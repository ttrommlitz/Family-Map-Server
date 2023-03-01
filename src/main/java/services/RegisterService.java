package services;

import dao.AuthtokenDao;
import dao.DataAccessException;
import dao.UserDao;
import model.Authtoken;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.util.UUID;

/** Service to register a new User **/
public class RegisterService extends Service {
    /**
     * Creates a new User account, generates 4
     * generations of ancestor data (using FillService),
     * and logs the User in (using LoginService). Result
     * contains an authtoken for the current User
     * @param request
     * @return RegisterResult
     */
    public RegisterResult register(RegisterRequest request) {
        UserDao userDao = new UserDao(conn);
        AuthtokenDao authDao = new AuthtokenDao(conn);
        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setGender(request.getGender());

        String personID = UUID.randomUUID().toString();
        String authtoken = UUID.randomUUID().toString();
        newUser.setPersonID(personID);

        RegisterResult result = new RegisterResult();
        try {
            userDao.insert(newUser);
            Authtoken token = new Authtoken(authtoken, newUser.getUsername());
            authDao.insert(token);

            result.setAuthtoken(authtoken);
            result.setUsername(newUser.getUsername());
            result.setPersonID(personID);
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            handleException(e, result);
        }

        return result;
    }
}
