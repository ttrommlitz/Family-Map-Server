package services;

import com.google.gson.Gson;
import dao.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.ClearResult;
import result.LoadResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private ClearService clearService;
    private LoadService loadService;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        loadService = new LoadService();
        Gson gson = new Gson();
        Reader reader = new FileReader("passoffFiles/LoadData.json");
        LoadRequest loadRequest = gson.fromJson(reader, LoadRequest.class);
        loadService.load(loadRequest);
        clearService = new ClearService();
    }
    @Test
    public void clearPass() {
        ClearResult result = clearService.clear(false);
        assertEquals("Clear succeeded.", result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void clearPassTwo() {
        clearService.clear(false);
        clearService = new ClearService();
        assertDoesNotThrow(() -> clearService.clear(false));
    }

    @Test
    public void clearByUsernamePass() throws DataAccessException {
        ClearResult result = clearService.clearByUsername("sheila");
        assertEquals("Clear By Username Succeeded.", result.getMessage());
        assertTrue(result.isSuccess());
    }

    @Test
    public void clearByUsernamePassTwo() {
        assertDoesNotThrow(() -> clearService.clearByUsername("BadUsername"));
    }
}
