package com.tym.Tmall.auth.vo;

import lombok.Data;

@Data
public class UserRegVO {
    private String userName;
    private String password;
    private String phone;
    private String code;
}
