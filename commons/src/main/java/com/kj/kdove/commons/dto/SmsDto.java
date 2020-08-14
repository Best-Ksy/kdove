package com.kj.kdove.commons.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@Getter
@Setter
public class SmsDto implements Serializable {


    private static final long serialVersionUID = -7047638910322936628L;

    private String phoneNumber;
    private String smsCode;



}
