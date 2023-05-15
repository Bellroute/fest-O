package com.example.festo.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MockBoothRequest {

    @JsonProperty("booth_name")
    private String boothName;

//    @JsonProperty("booth_category")
//    private String boothCategory;

    @JsonProperty("booth_image_url")
    private String boothImageUrl;

    @JsonProperty("menu")
    private List<MenuRequest> menu;
}


//    "booth_name": "포라코",
//            "booth_category": ["치킨"],
//            "booth_image_url": "https://cdn.imweb.me/thumbnail/20180814/5b7287fdc19c4.jpg",
//            "menu": [
//            {
//            "name": "치킨(소)",
//            "price": "7000",
//            "category": "치킨",
//            "image_url": "https://cdn.imweb.me/upload/S2017041358eed92818b4f/5b72895296f52.jpg"
//            },
//            {
//            "name": "치킨(대)",
//            "price": "10000",
//            "category": "치킨",
//            "image_url": "https://cdn.imweb.me/upload/S2017041358eed92818b4f/5b72895296f52.jpg"
//            }
//            ]