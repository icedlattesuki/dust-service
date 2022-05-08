package com.dust.gateway.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tag {

    private String category;

    @JsonProperty("internal_name")
    private String internalName;

    @JsonProperty("localized_category_name")
    private String localizedCategoryName;

    @JsonProperty("localized_tag_name")
    private String localizedTagName;
}