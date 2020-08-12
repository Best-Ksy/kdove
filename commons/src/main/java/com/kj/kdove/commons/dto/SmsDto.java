package com.kj.kdove.commons.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SmsDto implements Serializable {


    private static final long serialVersionUID = -7047638910322936628L;

    private String phoneNumber;
    private String smsCode;


}
