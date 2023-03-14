package dao;

import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class EventDaoTest {
    private Database db;
    private Event bestEvent;
    private EventDao eventDao;
    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);

        Connection conn = db.getConnection();
        eventDao = new EventDao(conn);
        eventDao.clear("Event");
    }

    @AfterEach
    public void tearDown() {
        db.closeConnection(false);
    }

    @Test
    public void insertEventPass() throws DataAccessException {
        eventDao.insert(bestEvent);
        Event compareTest = eventDao.find(bestEvent.getEventID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertEventFail() throws DataAccessException {
        eventDao.insert(bestEvent);
        assertThrows(DataAccessException.class, () -> eventDao.insert(bestEvent));
    }

    @Test
    public void findEventPass() throws DataAccessException {
        eventDao.insert(bestEvent);
        var result = eventDao.find(bestEvent.getEventID());
        assertEquals(bestEvent, result);
    }

    @Test
    public void findEventFail() throws DataAccessException {
        eventDao.insert(bestEvent);
        var result = eventDao.find("NonExistentID");
        assertNull(result);
    }

    @Test
    public void findAllEventsPass() throws DataAccessException  {
        Event[] events = new Event[3];
        for (int i = 0; i < 3; i++) {
            Event event = new Event(Integer.toString(i), "Gale", "Gale123A",
                    35.9f, 140.1f, "Japan", "Ushiku",
                    "Biking_Around", 2016);
            events[i] = event;
            eventDao.insert(event);
        }
        Event[] result = eventDao.findAll("Gale");
        assertNotNull(result);
        assertArrayEquals(events, result);
    }

    @Test
    public void findAllEventsFail() throws DataAccessException {
        Event[] events = new Event[4];
        for (int i = 0; i < 3; i++) {
            Event event = new Event(Integer.toString(i), "Gale", "Gale123A",
                    35.9f, 140.1f, "Japan", "Ushiku",
                    "Biking_Around", 2016);
            events[i] = event;
            eventDao.insert(event);
        }
        // Insert Event associated with different User
        Event event = new Event(Integer.toString(3), "NotGale", "NotGale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        events[3] = event;
        eventDao.insert(event);
        assertNull(eventDao.findAll("NonExistentUsername"));
        var result = eventDao.findAll("Gale");
        assertFalse(Arrays.equals(result, events));
    }

    @Test
    public void clearEventPass() throws DataAccessException {
        for (int i = 0; i < 3; i++) {
            Event event = new Event(Integer.toString(i), "Gale", "Gale123A",
                    35.9f, 140.1f, "Japan", "Ushiku",
                    "Biking_Around", 2016);
            eventDao.insert(event);
        }
        eventDao.clear("Event");
        assertNull(eventDao.findAll("Gale"));
    }

    @Test
    public void clearEventPassTwo() throws DataAccessException {
        for (int i = 0; i < 3; i++) {
            Event event = new Event(Integer.toString(i), "Gale", "Gale123A",
                    35.9f, 140.1f, "Japan", "Ushiku",
                    "Biking_Around", 2016);
            eventDao.insert(event);
        }
        eventDao.clear("Event");
        assertDoesNotThrow(() -> eventDao.clear("Event"));
    }

    @Test
    public void clearEventByUsernamePass() throws DataAccessException {
        Event event1 = new Event(Integer.toString(1), "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        Event event2 = new Event(Integer.toString(2), "NotGale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventDao.insert(event1);
        eventDao.insert(event2);
        eventDao.clearByUsername("Event", "Gale");
        assertNull(eventDao.find("1"));
        assertNotNull(eventDao.find("2"));
    }

    @Test
    public void clearEventByUsernamePassTwo() throws DataAccessException {
        Event event1 = new Event(Integer.toString(1), "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        Event event2 = new Event(Integer.toString(2), "NotGale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        eventDao.insert(event1);
        eventDao.insert(event2);
        eventDao.clearByUsername("Event", "Gale");
        assertDoesNotThrow(() -> eventDao.clearByUsername("Event", "Gale"));
        assertNotNull(eventDao.find("2"));
    }
}
