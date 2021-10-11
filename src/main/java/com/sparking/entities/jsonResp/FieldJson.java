package com.sparking.entities.jsonResp;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class FieldJson {

    private int id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String image;
    private Double price;
    private String openstatus;
    private BigDecimal space;
    private String details;
    private int totalBook;
    private int totalSlot;
    private int busySlot;

}