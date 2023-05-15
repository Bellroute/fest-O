package com.example.festo.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MenuRequest {

    private String name;

    private String price;

    private String category;

    @JsonProperty("image_url")
    private String imageUrl;
}

//            {
//            "name": "치킨(소)",
//            "price": "7000",
//            "category": "치킨",
//            "image_url": "https://cdn.imweb.me/upload/S2017041358eed92818b4f/5b72895296f52.jpg"
//            },