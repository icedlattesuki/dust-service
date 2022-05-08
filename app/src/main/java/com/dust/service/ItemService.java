package com.dust.service;

import com.dust.exception.InternalException;
import com.dust.gateway.FloatGateway;
import com.dust.gateway.SteamGateway;
import com.dust.gateway.response.FloatDetails;
import com.dust.gateway.response.InventoryResponse;
import com.dust.gateway.response.common.Asset;
import com.dust.gateway.response.common.Description;
import com.dust.service.assemble.ItemDetailsAssembler;
import com.dust.service.entity.item.ItemDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemService.class);

    @Autowired
    private SteamGateway steamGateway;

    @Autowired
    private FloatGateway floatGateway;

    public List<ItemDetails> getInventory(String steamId) {
        InventoryResponse response = doGetInventory(steamId);
        Map<String, Description> descriptionMap = buildDescriptionMap(response);
        Map<String, FloatDetails> floatMap = getFloat(steamId, response.getAssets(), descriptionMap);
        return ItemDetailsAssembler.assemble(steamId, response.getAssets(), descriptionMap, floatMap);
    }

    private InventoryResponse doGetInventory(String steamId) {
        try {
            return steamGateway.getInventory(steamId);
        } catch (Exception e) {
            LOGGER.error("Fail to get customer inventory", e);
            throw new InternalException("获取库存失败");
        }
    }

    private Map<String, FloatDetails> getFloat(String steamId, List<Asset> assets, Map<String, Description> descriptionMap) {
        try {
            List<String> inspectLinks = getInspectLinks(steamId, assets, descriptionMap);
            return floatGateway.batchGetFloat(inspectLinks);
        } catch (Exception e) {
            LOGGER.error("Fail to get float", e);
            throw new InternalException("获取库存失败");
        }
    }

    private Map<String, Description> buildDescriptionMap(InventoryResponse response) {
        return response.getDescriptions().stream().collect(Collectors.toMap(this::getDescriptionMapKey, Function.identity()));
    }

    private String getDescriptionMapKey(Description description) {
        return description.getClassId() + "_" + description.getInstanceId();
    }

    private List<String> getInspectLinks(String steamId, List<Asset> assets, Map<String, Description> descriptionMap) {
        return assets.stream()
                .map(a -> new Object[] {a, descriptionMap.get(a.getClassId() + "_" + a.getInstanceId())})
                .filter(arr -> arr[1] != null)
                .map(arr -> ((Description) arr[1]).getInspectLink(steamId, ((Asset) arr[0]).getAssetId()))
                .filter(Objects::nonNull)
                .toList();
    }
}
