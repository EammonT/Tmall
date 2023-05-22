package com.tym.Tmall.auth.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {
    private String loginAcct;
    private String password;
}
