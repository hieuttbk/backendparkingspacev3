package com.hongdatchy.entities.json;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class GatewayJson {

    private int id;
    private int fieldId;
    private String address;
    private int totalDetector;

}
