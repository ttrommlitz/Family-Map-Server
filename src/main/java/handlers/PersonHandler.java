package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import request.PersonRequest;
import result.PersonResult;
import services.PersonService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class PersonHandler extends Handler <PersonRequest, PersonResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String url = exchange.getRequestURI().getPath();
            System.out.println("Event handler called");
            boolean success = false;
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {
                PersonRequest request = new PersonRequest();
                PersonService service = new PersonService();
                PersonResult result;
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authtoken = reqHeaders.getFirst("Authorization");
                    request.setAuthtoken(authtoken);
                    if (url.equals("/person")) {
                        result = service.getAllPersons(request);
                    } else {
                        request.setPersonID(getPersonID(url));
                        result = service.getPersonByID(request);
                    }
                    sendResponse(exchange, result);
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

    private String getPersonID(String url) {
        return url.replaceAll("/person/", "");
    }
}
