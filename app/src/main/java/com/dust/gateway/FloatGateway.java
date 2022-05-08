package com.dust.gateway;

import com.dust.gateway.response.FloatDetails;
import com.dust.util.ObjectMapperUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FloatGateway {

    private final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public Map<String, FloatDetails> batchGetFloat(List<String> inspectLinks) throws URISyntaxException, IOException, InterruptedException {
        String url = "https://api.csgofloat.com/bulk";
        String body = ObjectMapperUtils.get().writeValueAsString(buildRequest(inspectLinks));
        HttpRequest request = HttpRequest.newBuilder().uri(new URI(url)).POST(HttpRequest.BodyPublishers.ofString(body)).header("Content-Type", "application/json").build();
        HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        Map<String, Object> map = ObjectMapperUtils.get().readValue(response.body(), HashMap.class);
        return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> ObjectMapperUtils.get().convertValue(e.getValue(), FloatDetails.class)));
    }

    private Map<String, Object> buildRequest(List<String> inspectLinks) {
        List<Map<String, String>> links = new ArrayList<>();
        for (String inspectLink : inspectLinks) {
            Map<String, String> map = new HashMap<>();
            map.put("link", inspectLink);
            links.add(map);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("links", links);
        return map;
    }
}
