package dao;

import model.Authtoken;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthtokenDaoTest {
    private Database db;
    private Authtoken bestAuthtoken;
    private AuthtokenDao authtokenDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestAuthtoken = new Authtoken("thisisatoken", "generalZod");
        Connection conn = db.getConnection();
        authtokenDao = new AuthtokenDao(conn);
        authtokenDao.clear("Authtoken");
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertAuthtokenPass() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        Authtoken compareTest = authtokenDao.find(bestAuthtoken.getAuthtoken());
        assertNotNull(compareTest);
        assertEquals(bestAuthtoken, compareTest);
    }

    @Test
    public void insertAuthtokenFail() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        assertThrows(DataAccessException.class, () -> authtokenDao.insert(bestAuthtoken));
        Authtoken nullAuthtoken = new Authtoken();
        assertThrows(DataAccessException.class, () -> authtokenDao.insert(nullAuthtoken));

    }

    @Test
    public void findAuthtokenPass() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        var result = authtokenDao.find(bestAuthtoken.getAuthtoken());
        assertEquals(bestAuthtoken, result);
    }

    @Test
    public void findAuthtokenFail() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        var result = authtokenDao.find("NonExistentAuthtoken");
        assertNull(result);
        assertNull(authtokenDao.find(null));
    }

    @Test
    public void clearAuthtokenPass() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        authtokenDao.clear("Authtoken");
        assertNull(authtokenDao.find("thisisatoken"));
    }

    @Test
    public void clearAuthtokenPassTwo() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        authtokenDao.clear("Authtoken");
        assertDoesNotThrow(() -> authtokenDao.clear("Authtoken"));
    }

    @Test
    public void clearAuthtokenByUsernamePass() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        Authtoken newToken = new Authtoken("thisisanewtoken", "generalZod2");
        authtokenDao.insert(newToken);
        authtokenDao.clearByUsername("Authtoken", "generalZod");
        assertNull(authtokenDao.find("thisisatoken"));
        assertNotNull(authtokenDao.find("thisisanewtoken"));
    }

    @Test
    public void clearAuthtokenByUsernamePassTwo() throws DataAccessException {
        authtokenDao.insert(bestAuthtoken);
        Authtoken newToken = new Authtoken("thisisanewtoken", "generalZod2");
        authtokenDao.insert(newToken);
        authtokenDao.clearByUsername("Authtoken", "generalZod");
        assertDoesNotThrow(() -> authtokenDao.clearByUsername("Authtoken", "generalZod"));
    }
}
