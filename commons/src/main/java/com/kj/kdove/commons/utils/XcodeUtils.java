package com.kj.kdove.commons.utils;

public class XcodeUtils {

    /**
     * 加密算法
     * @param ud
     * @param ph
     * @return
     */
    public static String getXcode(String ud,String ph){
        //这里需要加密，对称加密，前端无法拿到这个xcode，xcode全是后端计算
        // 前端只需要提供后端传的ucode和phonenum
        String l_code =  ph.substring(0,4) + ph.substring(ph.length() - 4);
        String r_code = ud.substring(ud.length()/2) + ud.substring(0,ud.length()/2);
        String xcode = l_code + r_code;
        return xcode.substring(xcode.length()/2)+ xcode.substring(0,xcode.length()/2);
    }

}
