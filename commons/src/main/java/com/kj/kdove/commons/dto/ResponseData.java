package com.kj.kdove.commons.dto;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@ToString
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = 3114482627842823503L;

    private int code;
    private String message;
    private T data;

    public ResponseData(){};

    public ResponseData(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
