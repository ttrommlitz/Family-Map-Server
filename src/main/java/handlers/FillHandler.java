package handlers;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import request.FillRequest;
import result.EventResult;
import result.FillResult;
import services.FillService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class FillHandler extends Handler <FillRequest, FillResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String url = exchange.getRequestURI().getPath();
            System.out.println("Fill handler called");
            boolean success = false;
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                String[] params = url.split("/");
                FillRequest request = new FillRequest();
                request.setGenerations((params.length == 4) ? Integer.parseInt(params[3]) : 4);
                request.setUsername(params[2]);
                FillService service = new FillService(false);
                FillResult result = service.fill(request);
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
