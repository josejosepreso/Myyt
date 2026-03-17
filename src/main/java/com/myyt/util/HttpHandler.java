package com.myyt.util;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient;

import java.util.Optional;
import static java.util.Optional.*;

public class HttpHandler {
    public static Optional<HttpResponse<String>> GETRequest(String url) {
        try {
            final HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();

            final HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            return of(response);
        } catch (Exception exception) {
            return empty();
        }
    }

    public static Optional<String> getResponseBody(HttpResponse<String> response) {
        return response.statusCode() == 200
            ? of(response.body())
            : empty();
    }
}
