package com.tym.Tmall.member.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginVO implements Serializable {
    private String loginAcct;
    private String password;
}
