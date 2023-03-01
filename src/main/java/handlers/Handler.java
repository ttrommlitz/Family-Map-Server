package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

public abstract class Handler<Request, Result> implements HttpHandler {
    // Serializes json strings from client into Request objects to be utilized in services
    protected Request serialize(Class<Request> type, InputStream stream) {
        Reader json = new InputStreamReader(stream);
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    // Deserializes Result objects into json strings
    protected String deserialize(Result result) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(result);
    }

    // writes a string of json data to the OutputStream responseBody to be sent to the client
    protected void writeString(String data, OutputStream stream) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(stream);
        writer.write(data);
        writer.flush();
    }
}
