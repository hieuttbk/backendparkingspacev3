package com.sparking.entities.payloadReq;

import lombok.Data;

@Data
public class ManagerFieldPayload {

    private Integer id;
    private int fieldId;
    private int managerId;

}