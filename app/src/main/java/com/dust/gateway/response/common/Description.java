package com.dust.gateway.response.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Description {

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

    public String getInspectLink(String steamId, String assetId) {
        if (CollectionUtils.isEmpty(actions)) {
            return null;
        }

        for (Action action : actions) {
            if (action.getName().contains("在游戏中检视")) {
                return action.getLink().replace("%owner_steamid%", steamId).replace("%assetid%", assetId);
            }
        }

        return null;
    }
}