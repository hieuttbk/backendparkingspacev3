package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class VerifyAccountPayload {
    private String email;
    private String code;
}
