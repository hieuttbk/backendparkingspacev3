package com.hongdatchy.entities.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Date;

@Data
public class UserUpdateInfo {

    private Integer idNumber;
    private String phone;
    private String equipment;
    private String image;
    private String address;
    private String sex;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date birth;

}
