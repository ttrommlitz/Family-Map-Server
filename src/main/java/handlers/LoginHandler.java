package handlers;

import com.sun.net.httpserver.HttpExchange;
import request.LoginRequest;
import result.LoginResult;
import services.LoginService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class LoginHandler extends Handler <LoginRequest, LoginResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Login handler called");
            boolean success = false;
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                LoginRequest request = serialize(LoginRequest.class, exchange.getRequestBody());
                LoginService service = new LoginService();
                LoginResult result = service.login(request);
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
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }
}
