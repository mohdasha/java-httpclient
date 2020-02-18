package com.app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create("https://pluralsight.com")).build();

        HttpResponse httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.discarding());

        System.out.println(httpResponse.body());
    }
}
