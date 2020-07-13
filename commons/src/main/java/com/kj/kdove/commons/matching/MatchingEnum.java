package com.kj.kdove.commons.matching;

public enum  MatchingEnum {

    MATCHING_OVERTIME(101,"匹配超时"),
    MATCHING_NUM_EXC(102,"匹配人数异常"),
    MATCHING_FALSE(103,"匹配失败"),
    MATCHING_SUCCESS(104,"匹配成功"),
    MATCHING_ERRO(105,"网络异常，请重试");


    private int code;
    private String message;

    private MatchingEnum(int code,String message){
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
