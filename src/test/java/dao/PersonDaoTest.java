package dao;

import model.Authtoken;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private PersonDao personDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("123456", "Jimmy123X",
                "Jimmy", "Neutron", "m", null, null, null);

        // Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Then we pass that connection to the EventDAO, so it can access the database.
        personDao = new PersonDao(conn);
        //Let's clear the database as well so any lingering data doesn't affect our tests
        personDao.clear("Person");
    }

    @AfterEach
    public void tearDown() {
        // Here we close the connection to the database file, so it can be opened again later.
        // We will set commit to false because we do not want to save the changes to the database
        // between test cases.
        db.closeConnection(false);
    }

    @Test
    public void insertPersonPass() throws DataAccessException {
        personDao.insert(bestPerson);
        Person compareTest = personDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertPersonFail() throws DataAccessException {
        personDao.insert(bestPerson);
        assertThrows(DataAccessException.class, () -> personDao.insert(bestPerson));
        Person nullPerson = new Person();
        assertThrows(DataAccessException.class, () -> personDao.insert(nullPerson));

    }

    @Test
    public void findPersonPass() throws DataAccessException {
        personDao.insert(bestPerson);
        var result = personDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, result);
    }

    @Test
    public void findPersonFail() throws DataAccessException {
        personDao.insert(bestPerson);
        var result = personDao.find("NonExistentID");
        assertNull(result);
    }

    @Test
    public void findAllPersonsPass() throws DataAccessException  {
        Person[] persons = new Person[3];
        for (int i = 0; i < 3; i++) {
            Person person = new Person(Integer.toString(i), "Jimmy123X", "Jimmy",
                    "Neutron", "m", null, null,
                    null);
            persons[i] = person;
            personDao.insert(person);
        }
        Person[] result = personDao.findAll("Jimmy123X");
        assertNotNull(result);
        assertArrayEquals(persons, result);
    }

    @Test
    public void findAllPersonsFail() throws DataAccessException {
        Person[] persons = new Person[4];
        for (int i = 0; i < 3; i++) {
            Person person = new Person(Integer.toString(i), "Jimmy123X", "Jimmy",
                    "Neutron", "m", null, null,
                    null);
            persons[i] = person;
            personDao.insert(person);
        }
        // Insert Event associated with different User
        Person event = new Person(Integer.toString(3), "NotJimmy123X", "NotJimmy",
                "Neutron", "m", null, null,
                null);
        persons[3] = event;
        personDao.insert(event);
        assertNull(personDao.findAll("NonExistentUsername"));
        var result = personDao.findAll("Jimmy123X");
        assertFalse(Arrays.equals(result, persons));
    }

    @Test
    public void clearPersonsPass() throws DataAccessException {
        Person[] persons = new Person[3];
        for (int i = 0; i < 3; i++) {
            Person person = new Person(Integer.toString(i), "Jimmy123X", "Jimmy",
                    "Neutron", "m", null, null,
                    null);
            persons[i] = person;
            personDao.insert(person);
        }
        personDao.clear("Person");
        assertNull(personDao.findAll("Jimmy123X"));
        personDao.clear("Person");
    }

    @Test
    public void clearPersonByUsername() throws DataAccessException {
        personDao.insert(bestPerson);
        Person newPerson = new Person("1234567", "Jimmy123Y",
                "Jimmy", "Neutron", "m", null, null, null);
        personDao.insert(newPerson);
        personDao.clearByUsername("Person", "Jimmy123X");
        assertNull(personDao.find("123456"));
        assertNotNull(personDao.find("1234567"));
    }
}
