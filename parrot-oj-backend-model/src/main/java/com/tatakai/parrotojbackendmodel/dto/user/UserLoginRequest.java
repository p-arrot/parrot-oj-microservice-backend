package com.tatakai.parrotojbackendmodel.dto.user;

import lombok.Data;

@Data
public class UserLoginRequest {

    private String userAccount;
    private String userPassword;

}
