package com.sparking.entities.payloadReq;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ParkPayload {
    private Integer fieldId;
    private String equipment;
    //    vì default timezone đang là GMT+7 rồi nên không viết timezone = "GMT+7" cũng vẫn được
    @JsonFormat(pattern="YYYY-MM-DD HH:mm", timezone = "GMT+7")
    private Timestamp timeCarIn;
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
//    private Timestamp timeOutBook;
}
