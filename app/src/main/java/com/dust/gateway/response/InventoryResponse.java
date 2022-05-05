package com.dust.gateway.response;

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

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Asset {

        @JsonProperty("assetid")
        private String assetId;

        @JsonProperty("classid")
        private String classId;

        @JsonProperty("instanceid")
        private String instanceId;

        private String amount;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Description {

        @JsonProperty("classid")
        private String classId;

        @JsonProperty("instanceid")
        private String instanceId;

        @JsonProperty("icon_url")
        private String iconUrl;

        private List<DescriptionDetail> descriptions;

        private int tradable;

        private List<Action> actions;

        private String name;

        private String type;

        private int commodity;

        private List<Tag> tags;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class DescriptionDetail {

        private String type;

        private String value;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Tag {

        private String category;

        @JsonProperty("internal_name")
        private String internalName;

        @JsonProperty("localized_category_name")
        private String localizedCategoryName;

        @JsonProperty("localized_tag_name")
        private String localizedTagName;
    }

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Action {

        private String link;

        private String name;
    }
}


