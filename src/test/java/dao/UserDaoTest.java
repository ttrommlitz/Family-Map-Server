package dao;

import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDaoTest {
    private Database db;
    private User bestUser;
    private UserDao userDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestUser = new User("IronMan456", "thorSucks", "ironMan@gmail.com",
                "Tony", "Stark", "m", "thisIsMyPersonID");
        Connection conn = db.getConnection();
        userDao = new UserDao(conn);
        userDao.clear("User");
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertUserPass() throws DataAccessException {
        userDao.insert(bestUser);
        User compareTest = userDao.find(bestUser.getUsername());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertUserFail() throws DataAccessException {
        userDao.insert(bestUser);
        assertThrows(DataAccessException.class, () -> userDao.insert(bestUser));
        User nullUser = new User();
        assertThrows(DataAccessException.class, () -> userDao.insert(nullUser));

    }

//    @Test
//    public void updateUserPass() throws DataAccessException {
//        userDao.insert(bestUser);
//        User newUser = bestUser;
//        newUser.setFirstName("A New First Name");
//        userDao.updateUser(newUser);
//        User result = userDao.find(newUser.getUsername());
//        assertEquals(newUser, result);
//    }
//
//    @Test
//    public void updateUserFail() throws DataAccessException {
//        User nullUser = new User();
//        assertThrows(DataAccessException.class, () -> userDao.insert(nullUser));
//        userDao.insert(bestUser);
//        User badUser = bestUser;
//        badUser.setUsername("nonExistentUsername");
//        assertThrows(DataAccessException.class, () -> userDao.updateUser(badUser));
//    }

    @Test
    public void findUserPass() throws DataAccessException {
        userDao.insert(bestUser);
        var result = userDao.find(bestUser.getUsername());
        assertEquals(bestUser, result);
    }

    @Test
    public void findUserFail() throws DataAccessException {
        userDao.insert(bestUser);
        var result = userDao.find("NonExistentUsername");
        assertNull(result);
    }

    @Test
    public void clearUserPass() throws DataAccessException {
            userDao.insert(bestUser);
        userDao.clear("User");
        assertNull(userDao.find("IronMan456"));
        userDao.clear("User");
    }

    @Test
    public void clearUserByUsername() throws DataAccessException {
        userDao.insert(bestUser);
        User newUser = new User("IronMan123", "thorSucks", "ironMan@gmail.com",
                "Tony", "Stark", "m", "thisIsMyPersonID");
        userDao.insert(newUser);
        userDao.clearByUsername("User", "IronMan456");
        assertNull(userDao.find("IronMan456"));
        assertNotNull(userDao.find("IronMan123"));
    }
}
