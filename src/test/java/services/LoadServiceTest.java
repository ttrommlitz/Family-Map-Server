package services;

import com.google.gson.Gson;
import dao.DataAccessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {
    private LoadService loadService;
    private LoadRequest loadRequest;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        loadService = new LoadService();
        loadRequest = new LoadRequest();
        Gson gson = new Gson();
        Reader reader = new FileReader("passoffFiles/LoadData.json");
        loadRequest = gson.fromJson(reader, LoadRequest.class);
    }

    @Test
    public void loadPass() {
        LoadResult loadResult = loadService.load(loadRequest);
        assertTrue(loadResult.getMessage().toLowerCase().contains("successfully added"));
        assertTrue(loadResult.isSuccess());
    }

    @Test
    public void loadFail() {
        loadRequest.setEvents(null);
        loadRequest.setPersons(null);
        loadRequest.setUsers(null);

        LoadResult result = loadService.load(loadRequest);
        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().toLowerCase().contains("error"));
    }
}
