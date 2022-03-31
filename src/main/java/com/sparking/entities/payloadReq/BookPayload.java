package com.sparking.entities.payloadReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Builder
@Data
public class BookPayload {
    private Integer fieldId;
    private String carNumber;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT+7")
    private Timestamp timeInBook;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT+7")
    private Timestamp timeOutBook;
}
