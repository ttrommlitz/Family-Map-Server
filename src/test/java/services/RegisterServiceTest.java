package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import request.RegisterRequest;
import result.EventResult;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterServiceTest {
    private RegisterService registerService;
    private RegisterRequest registerRequest;

    @BeforeEach
    public void setUp() {
        registerService = new RegisterService();
        registerRequest = new RegisterRequest();
        registerRequest.setUsername("ttromm");
        registerRequest.setFirstName("Tyler");
        registerRequest.setLastName("Trommlitz");
        registerRequest.setEmail("ttromm@byu.edu");
        registerRequest.setPassword("myPassword");
        registerRequest.setGender("m");
    }

    @Test
    public void registerPass() {
        RegisterResult result = registerService.register(registerRequest);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals("ttromm", result.getUsername());
        assertTrue(result.isSuccess());

        EventService eventService = new EventService();
        EventRequest eventRequest = new EventRequest();
        eventRequest.setAuthtoken(result.getAuthtoken());
        EventResult eventResult = eventService.getAllEvents(eventRequest);
        assertDoesNotThrow(() -> eventResult.getData()[0]);
    }

    @Test
    public void registerFail() {
        registerRequest.setGender("BadGender");
        RegisterResult result = registerService.register(registerRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
