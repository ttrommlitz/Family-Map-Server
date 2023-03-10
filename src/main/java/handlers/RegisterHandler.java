package handlers;

import com.sun.net.httpserver.HttpExchange;
import request.RegisterRequest;
import result.RegisterResult;
import services.RegisterService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class RegisterHandler extends Handler <RegisterRequest, RegisterResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Register handler called");
            boolean success = false;
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                RegisterRequest request = serialize(RegisterRequest.class, exchange.getRequestBody());
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);
                sendResponse(exchange, result);
                success = true;
            }
            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_GATEWAY, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
