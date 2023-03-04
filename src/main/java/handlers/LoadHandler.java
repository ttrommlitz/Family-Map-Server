package handlers;

import com.sun.net.httpserver.HttpExchange;
import request.LoadRequest;
import result.LoadResult;
import services.LoadService;

import java.io.IOException;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler<LoadRequest, LoadResult> {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Load handler called");
            boolean success = false;
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {
                LoadRequest request = serialize(LoadRequest.class, exchange.getRequestBody());
                LoadService service = new LoadService();
                LoadResult result = service.load(request);
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
