package com.tym.Tmall.common.exception;

public enum BizCodeEnume {
    UNKNOW_EXCEPTION(10000,"系统未知异常"),
    VALID_EXCEPTION(10001,"参数格式校验失败"),
    SMS_CODE_EXCEPTION(20001,"验证码异常"),
    USER_EXIST_EXCEPTION(30001,"用户已存在"),
    PHONE_EXIST_EXCEPTION(30002,"手机号已存在"),
    EMAIL_EXIST_EXCEPTION(30003,"邮箱已存在"),
    LOGINACCT_PASS_INVALID_EXCEPTION(30004,"账号密码错误");


    private int code;
    private String msg;

    BizCodeEnume(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
