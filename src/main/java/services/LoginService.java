package services;

import dao.*;
import model.*;
import request.LoginRequest;
import result.LoginResult;

import java.util.Objects;
import java.util.UUID;

/** Service to log a User in **/
public class LoginService extends Service {
    /**
     * Logs the current User in. Result contains
     * an authtoken for the current User
     * @param request
     * @return LoginResult
     */
    public LoginResult login(LoginRequest request) {
        UserDao userDao = new UserDao(conn);
        AuthtokenDao authDao = new AuthtokenDao(conn);
        LoginResult result = new LoginResult();
        try {
            User user = userDao.find(request.getUsername());
            if (user == null) {
                throw new DataAccessException("User does not exist");
            }
            if (!Objects.equals(user.getPassword(), request.getPassword())) {
                throw new DataAccessException("Invalid password");
            }
            String authtoken = UUID.randomUUID().toString();
            Authtoken token = new Authtoken(authtoken, user.getUsername());
            authDao.insert(token);
            result.setAuthtoken(authtoken);
            result.setUsername(user.getUsername());
            result.setPersonID(user.getPersonID());
            result.setSuccess(true);
            db.closeConnection(true);
        } catch (DataAccessException e) {
            result.setMessage("Error: " + e.getMessage());
            result.setSuccess(false);
            db.closeConnection(false);
        }
        return result;
    }
}
