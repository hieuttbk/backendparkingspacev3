package com.sparking.entities.payloadReq;

import lombok.Data;

@Data
public class ManagerPayload {

    private Integer id;
    private String email;
    private String pass;
    private Boolean acp;

}
