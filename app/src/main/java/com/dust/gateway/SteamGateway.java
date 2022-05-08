package com.dust.gateway;

import com.dust.gateway.response.InventoryResponse;
import com.dust.util.ObjectMapperUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class SteamGateway {

    private final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public InventoryResponse getInventory(String steamId) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(new URI("https://steamcommunity.com/inventory/" + steamId + "/730/2?l=schinese")).GET().build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        return ObjectMapperUtils.get().readValue(response.body(), InventoryResponse.class);
    }
}
