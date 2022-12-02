package net.luconia.api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimplexAPI {

    public static void main(String[] args) throws IOException {
        int serverPort = 8000;
        HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
        server.createContext("/api/cosmetics/wings", (exchange -> {
            if (exchange.getRequestMethod().equals("POST")) {
                String response = "{\"test\":{\"name\":\"test-cosmetic\"}}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());

                //System.out.println(inputStreamToString(exchange.getRequestBody()));

                JsonElement jsonElement = new JsonParser().parse(inputStreamToString(exchange.getRequestBody()));
                JsonObject jsonObject = jsonElement.getAsJsonObject();
                //jsonObject = jsonObject.getAsJsonObject("uuid");

                if (jsonObject.get("uuid") != null && jsonObject.get("username") != null) {
                    System.out.println(jsonObject.get("uuid"));
                } else {
                    exchange.sendResponseHeaders(422, -1);
                }

                //os.close();
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
            exchange.close();
        }));
        server.setExecutor(null);
        server.start();
    }

    private static String inputStreamToString(InputStream inputStream) {
        String newLine = System.getProperty("line.separator");
        String result;
        try (Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
            result = lines.collect(Collectors.joining(newLine));
        }

        return result;
    }

}
