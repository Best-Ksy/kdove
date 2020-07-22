package com.kj.kdove.commons.matching;

public enum  UserEnum {

    SELECT_USER_BY_USERNAME_SUCCESS(201,"获取用户信息成功"),
    SELECT_USER_BY_USERNAME_FALSE(202,"不存在该用户"),
    SELECT_USER_BY_USERNAME_PARAM_ERRO(203,"用户名参数错误"),
    SELECT_USER_BY_USERNAME_IOEXCEPTION(204,"网络错误请重试"),


    USER_REG_FALSE_NO1(211,"用户名已存在，请重新修改用户名"),
    USER_REG_FALSE_NO2(212,"用户名错误，请查看用户名"),
    USER_REG_SUCCESS(213,"注册成功"),
    USER_REG_FALSE(214,"注册失败，请重新注册")
    ;

    private int code;
    private String message;

    private UserEnum(int code,String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
