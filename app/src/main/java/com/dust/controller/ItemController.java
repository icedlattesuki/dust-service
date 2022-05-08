package com.dust.controller;

import com.dust.service.ItemService;
import com.dust.service.entity.item.ItemDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/api/inventory")
    public List<ItemDetails> getInventory(@RequestParam("steamId") String steamId) {
        return itemService.getInventory(steamId);
    }
}
