package services;

import com.google.gson.Gson;
import dao.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.*;
import result.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class EventServiceTest {
    private EventService eventService;
    private LoadService loadService;
    private EventRequest eventRequest;
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        loadService = new LoadService();
        Gson gson = new Gson();
        Reader reader = new FileReader("passoffFiles/LoadData.json");
        LoadRequest loadRequest = gson.fromJson(reader, LoadRequest.class);
        loadService.load(loadRequest);

        LoginService loginService = new LoginService();
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("sheila");
        loginRequest.setPassword("parker");
        LoginResult loginResult = loginService.login(loginRequest, false);

        eventService = new EventService();
        eventRequest = new EventRequest();
        eventRequest.setEventID("Sheila_Birth");
        eventRequest.setAuthtoken(loginResult.getAuthtoken());

    }
    @Test
    public void getOneEventPass() {
        EventResult result = eventService.getEventByID(eventRequest);
        assertEquals("birth", result.getEventType());
        assertEquals("Sheila_Parker", result.getPersonID());
        assertEquals("Melbourne", result.getCity());
        assertEquals("Australia", result.getCountry());
        assertEquals((float)(-36.1833), result.getLatitude());
        assertEquals((float)(144.9667), result.getLongitude());
        assertEquals(1970, result.getYear());
        assertEquals("Sheila_Birth", result.getEventID());
        assertEquals("sheila", result.getAssociatedUsername());
        assertTrue(result.isSuccess());
    }

    @Test
    public void getOneEventFail() {
        eventRequest.setEventID("NonExistentId");
        EventResult result = eventService.getEventByID(eventRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }

    @Test
    public void getAllEventsPass() {
        eventRequest.setEventID(null);
        EventResult result = eventService.getAllEvents(eventRequest);
        assertEquals("birth", result.getData()[0].getEventType());
        assertEquals("completed asteroids", result.getData()[2].getEventType());
        assertEquals("death", result.getData()[4].getEventType());
        assertTrue(result.isSuccess());
    }

    @Test
    public void getAllEventsFail() {
        eventRequest.setAuthtoken("badAuthtoken");
        EventResult result = eventService.getAllEvents(eventRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
