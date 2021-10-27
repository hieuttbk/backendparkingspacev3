package com.sparking.entities.payloadReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class ManUpdateInfoPayload {

    private Integer idNumber;
    private String phone;
    private String equipment;
    private String image;
    private String address;
    private String sex;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private Date birth;

}
