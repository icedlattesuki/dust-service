package com.dust.gateway.response;

import com.dust.gateway.response.common.Asset;
import com.dust.gateway.response.common.Description;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryResponse {

    private List<Asset> assets;

    private List<Description> descriptions;

    @JsonProperty("total_inventory_count")
    private int totalInventoryCount;
}


