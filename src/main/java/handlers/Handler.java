package handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;

public abstract class Handler<Request, Result extends result.Result> implements HttpHandler {
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

    protected void sendResponse(HttpExchange exchange, Result result) throws IOException {
        String respData = deserialize(result);
        int statusCode = result.isSuccess() ? HttpURLConnection.HTTP_OK : HttpURLConnection.HTTP_BAD_REQUEST;
        exchange.sendResponseHeaders(statusCode, 0);
        var respBody = exchange.getResponseBody();
        writeString(respData, respBody);
        respBody.close();
    }
}
