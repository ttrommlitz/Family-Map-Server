package services;

import dao.AuthtokenDao;
import dao.DataAccessException;

public abstract class AuthenticateService extends Service {
    public String validateAuthtoken(String authtoken) throws DataAccessException {
        AuthtokenDao authDao = new AuthtokenDao(conn);
        var result = authDao.find(authtoken);
        if (result != null) {
            return result.getUsername();
        }
        throw new DataAccessException("Invalid Authtoken");

    }
}
