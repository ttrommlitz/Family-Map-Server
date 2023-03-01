package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import request.EventRequest;
import result.EventResult;
import services.EventService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class EventHandler extends Handler <EventRequest, EventResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String url = exchange.getRequestURI().getPath();
            System.out.println("Event handler called");
            boolean success = false;
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                EventRequest request = new EventRequest();
                EventService service = new EventService();
                EventResult result;
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authtoken = reqHeaders.getFirst("Authorization");
                    request.setAuthtoken(authtoken);
                    if (url.equals("/event")) {
                        result = service.getAllEvents(request);
                    } else {
                        request.setEventID(getEventID(url));
                        result = service.getEventByID(request);
                    }
                    String respData = deserialize(result);
                    int statusCode = result.isSuccess() ? HttpURLConnection.HTTP_OK : HttpURLConnection.HTTP_BAD_REQUEST;
                    exchange.sendResponseHeaders(statusCode, 0);
                    var respBody = exchange.getResponseBody();
                    writeString(respData, respBody);
                    respBody.close();
                    success = true;
                }
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

    private String getEventID(String url) {
        return url.replaceAll("/event/", "");
    }
}
