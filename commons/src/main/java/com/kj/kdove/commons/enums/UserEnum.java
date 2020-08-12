package com.kj.kdove.commons.enums;

public enum  UserEnum {

    SELECT_USER_BY_USERNAME_SUCCESS(201,"获取用户信息成功"),
    SELECT_USER_BY_USERNAME_FALSE(202,"不存在该用户"),
    SELECT_USER_BY_USERNAME_PARAM_ERRO(203,"用户名参数错误"),
    SELECT_USER_BY_USERNAME_IOEXCEPTION(204,"网络错误请重试"),


    USER_REG_FALSE_NO1(211,"用户名已存在，请重新修改用户名"),
    USER_REG_FALSE_NO2(212,"用户名错误，请查看用户名"),
    USER_REG_SUCCESS(213,"注册成功"),
    USER_REG_FALSE(214,"注册失败，请重新注册"),

    SEND_SMS_SUCCESS(230,"短信发送成功"),
    SEND_SMS_SERVICE_EXCEPTION(233,"服务异常"),
    SEND_SMS_MOBILE_ERROR(236,"手机号码不正确"),
    SEND_SMS_OVER_MAXNUM_LIMITS(222,"超过每日最大次数"),
    SEND_SMS_EXC(223,"网络错误请稍后重试"),

    SMS_ERRO(231,"验证码错误")


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
