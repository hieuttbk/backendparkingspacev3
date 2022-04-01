package com.sparking.entities.payloadReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class BookPayload {

    private Integer fieldId;

    private String carNumber;

    @JsonFormat(pattern="YYYY-MM-DD HH:mm:ss")
    private Timestamp timeInBook;

    @JsonFormat(pattern="YYYY-MM-DD HH:mm:ss")
    private Timestamp timeOutBook;
}
