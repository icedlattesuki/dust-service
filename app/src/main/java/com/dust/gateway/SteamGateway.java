package com.dust.gateway;

import com.dust.gateway.response.InventoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class SteamGateway {

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void getInventory(String steamId) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://steamcommunity.com/inventory/" + steamId + "/730/2")).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        InventoryResponse inventoryResponse = objectMapper.readValue(response.body(), InventoryResponse.class);
        System.out.println(inventoryResponse);
    }
}
