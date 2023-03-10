package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;

import static org.junit.jupiter.api.Assertions.*;

public class LoginServiceTest {
    private LoginService loginService;
    private LoginRequest loginRequest;
    @BeforeEach
    public void setUp() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("ttromm");
        loginRequest.setPassword("myPassword");

        RegisterService registerService = new RegisterService();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername("ttromm");
        registerRequest.setFirstName("Tyler");
        registerRequest.setLastName("Trommlitz");
        registerRequest.setEmail("ttromm@byu.edu");
        registerRequest.setPassword("myPassword");
        registerRequest.setGender("m");
        registerService.register(registerRequest);

        loginService = new LoginService();
    }

    @Test
    public void loginPass() {
        LoginResult result = loginService.login(loginRequest, false);
        assertNotNull(result.getAuthtoken());
        assertNotNull(result.getPersonID());
        assertEquals("ttromm", result.getUsername());
        assertTrue(result.isSuccess());
    }

    @Test
    public void loginFail() {
        ClearService clearService = new ClearService();
        clearService.clear(false);

        loginService = new LoginService();
        LoginResult result = loginService.login(loginRequest, false);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
