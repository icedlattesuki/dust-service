package com.dust.service.entity.item;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemDetails {

    private String assetId;

    private String classId;

    private String instanceId;

    private String name;

    private Float floatValue;

    private String floatType;

    private String imageUrl;

    private String type;

    private List<Sticker> stickers;

    private List<Description> descriptions;

    private String inspectLink;
}
