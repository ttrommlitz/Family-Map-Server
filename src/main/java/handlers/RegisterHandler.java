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
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                RegisterRequest request = serialize(RegisterRequest.class, exchange.getRequestBody());
                RegisterService service = new RegisterService();
                RegisterResult result = service.register(request);
                String respData = deserialize(result);
                int statusCode = result.isSuccess() ? HttpURLConnection.HTTP_OK : HttpURLConnection.HTTP_BAD_REQUEST;
                exchange.sendResponseHeaders(statusCode, 0);
                var respBody = exchange.getResponseBody();
                writeString(respData, respBody);
                respBody.close();
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
