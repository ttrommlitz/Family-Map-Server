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

public class PersonServiceTest {
    private PersonService personService;
    private LoadService loadService;
    private PersonRequest personRequest;
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

        personService = new PersonService();
        personRequest = new PersonRequest();
        personRequest.setPersonID("Davis_Hyer");
        personRequest.setAuthtoken(loginResult.getAuthtoken());

    }
    @Test
    public void getOnePersonPass() {
        PersonResult result = personService.getPersonByID(personRequest);
        assertEquals("sheila", result.getAssociatedUsername());
        assertEquals("Davis_Hyer", result.getPersonID());
        assertEquals("Davis", result.getFirstName());
        assertEquals("Hyer", result.getLastName());
        assertEquals("m", result.getGender());
        assertNull(result.getFatherID());
        assertNull(result.getMotherID());
        assertEquals("Sheila_Parker", result.getSpouseID());
        assertTrue(result.isSuccess());
    }

    @Test
    public void getOnePersonFail() {
        personRequest.setPersonID("NonExistentId");
        PersonResult result = personService.getPersonByID(personRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }

    @Test
    public void getAllPersonsPass() {
        personRequest.setPersonID(null);
        PersonResult result = personService.getAllPersons(personRequest);
        assertEquals("Sheila", result.getData()[0].getFirstName());
        assertEquals("Blaine", result.getData()[2].getFirstName());
        assertEquals("Ken", result.getData()[4].getFirstName());
        assertTrue(result.isSuccess());
    }

    @Test
    public void getAllPersonsFail() {
        personRequest.setAuthtoken("badAuthtoken");
        PersonResult result = personService.getAllPersons(personRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
