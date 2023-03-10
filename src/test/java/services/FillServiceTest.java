package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.EventRequest;
import request.FillRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.*;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    private FillService fillService;
    private FillRequest fillRequest;
    @BeforeEach
    public void setUp() {
        fillRequest = new FillRequest();
        fillRequest.setUsername("ttromm");
        fillRequest.setGenerations(4);

        RegisterService registerService = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("ttromm");
        registerRequest.setFirstName("Tyler");
        registerRequest.setLastName("Trommlitz");
        registerRequest.setEmail("ttromm@byu.edu");
        registerRequest.setPassword("myPassword");
        registerRequest.setGender("m");
        registerService.register(registerRequest);

        fillService = new FillService(false);
    }

    @Test
    public void fillPass() {
        FillResult result = fillService.fill(fillRequest);
        assertTrue(result.isSuccess());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("ttromm");
        loginRequest.setPassword("myPassword");
        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(loginRequest, false);

        EventService eventService = new EventService();
        EventRequest eventRequest = new EventRequest();
        eventRequest.setAuthtoken(loginResult.getAuthtoken());
        EventResult eventResult = eventService.getAllEvents(eventRequest);
        assertDoesNotThrow(() -> eventResult.getData()[0]);
    }

    @Test
    public void fillFail() {
        ClearService clearService = new ClearService();
        clearService.clear(false);

        fillService = new FillService(false);
        FillResult result = fillService.fill(fillRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
