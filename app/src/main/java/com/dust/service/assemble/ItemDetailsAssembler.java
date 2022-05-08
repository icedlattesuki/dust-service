package com.dust.service.assemble;

import com.dust.gateway.response.FloatDetails;
import com.dust.gateway.response.common.Asset;
import com.dust.gateway.response.common.Description;
import com.dust.service.entity.item.ItemDetails;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ItemDetailsAssembler {

    public static List<ItemDetails> assemble(String steamId, List<Asset> assets, Map<String, Description> descriptionMap, Map<String, FloatDetails> floatMap) {
        return assets.stream()
                .map(a -> assemble(steamId, a, descriptionMap.get(getDescriptionMapKey(a)), floatMap.get(a.getAssetId())))
                .filter(Objects::nonNull).toList();
    }

    public static ItemDetails assemble(String steamId, Asset asset, Description description, FloatDetails floatDetails) {
        if (asset == null || description == null || floatDetails == null) {
            return null;
        }

        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setAssetId(asset.getAssetId());
        itemDetails.setClassId(asset.getClassId());
        itemDetails.setInstanceId(asset.getInstanceId());
        itemDetails.setName(description.getName());
        itemDetails.setFloatValue(floatDetails.getFloatValue());
        itemDetails.setImageUrl(floatDetails.getImageUrl());
        itemDetails.setInspectLink(description.getInspectLink(steamId, asset.getAssetId()));

        return itemDetails;
    }

    private static String getDescriptionMapKey(Asset asset) {
        return asset.getClassId() + "_" + asset.getInstanceId();
    }
}
