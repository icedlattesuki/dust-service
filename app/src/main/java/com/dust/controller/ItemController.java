package com.dust.controller;

import com.dust.gateway.SteamGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
public class ItemController {

    @Autowired
    private SteamGateway steamGateway;

    @GetMapping("/api/inventory")
    public void getInventory(@RequestParam("steamId") String steamId) throws URISyntaxException, IOException, InterruptedException {
        steamGateway.getInventory(steamId);
    }
}
