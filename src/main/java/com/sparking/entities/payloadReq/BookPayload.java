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
//    vì default timezone đang là GMT+7 rồi nên không viết timezone = "GMT+7" cũng vẫn được
    @JsonFormat(pattern="YYYY-MM-DD HH:mm:ss", timezone = "GMT+7")
    private Timestamp timeInBook;
    @JsonFormat(pattern="YYYY-MM-DD HH:mm:ss")
    private Timestamp timeOutBook;
}
