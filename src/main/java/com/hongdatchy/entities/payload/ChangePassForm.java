package com.hongdatchy.entities.payload;

import lombok.Data;

@Data
public class ChangePassForm {

    private String oldPassword;
    private String password;
    private String rePassword;

}
