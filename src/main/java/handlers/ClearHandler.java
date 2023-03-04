package handlers;

import com.sun.net.httpserver.HttpExchange;
import request.ClearRequest;
import result.ClearResult;
import services.ClearService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class ClearHandler extends Handler <ClearRequest, ClearResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Clear handler called");
            boolean success = false;
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                ClearService service = new ClearService();
                ClearResult result = service.clear(false);
                sendResponse(exchange, result);
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
