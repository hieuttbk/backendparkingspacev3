package com.hongdatchy.entities.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class DetectorPayload {

    private Integer id;
    private String addressDetector;
    private Integer slotId;
    private Integer gatewayId;
    private String batteryLevel;
    private String loracomLevel;
    private String operatingMode;


}
