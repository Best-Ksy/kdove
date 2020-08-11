package com.kj.kdove.usermatching.sms;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.kj.kdove.usermatching.utils.GsonUtil;
import com.kj.kdove.usermatching.utils.HttpClient4Utils;
import com.kj.kdove.usermatching.utils.SignatureUtils;
import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * 短息发送的结果不做处理（包括发送失败，服务器异常，手机号码错误等）
 * 集体返回状态码和随机数码
 * 具体处理在controller层
 *
 */

public class SendSms {
    /**
     * 业务ID，易盾根据产品业务特点分配
     */
    private final static String BUSINESSID = "8deb3c4ab21d4df2875c1b086ba80001";
    /**
     * 产品密钥ID，产品标识
     */
    private final static String SECRETID = "2d83873046608a35c63fcea5266ce86d";
    /**
     * 产品私有密钥，服务端生成签名信息使用，请严格保管，避免泄露
     */
    private final static String SECRETKEY = "5218bdf65189060fe08b46040ac747bf";

    /**
     * 接口地址
     */
    private final static String API_URL = "https://sms.dun.163yun.com/v2/sendsms";
    /**
     * 实例化HttpClient，发送http请求使用，可根据需要自行调参
     */
    private static HttpClient httpClient = HttpClient4Utils.createHttpClient(100, 20, 10000, 2000, 2000);

    /**
     * @param phoneNumber
     * @throws Exception
     */
    public static Map<String,String> sendCode(String phoneNumber) throws Exception {
        Map<String,String> returnMap = Maps.newHashMap();
        // 此处用申请通过的模板id
        String templateId = "11318";
        // 模板参数对应的json格式数据,例如模板为您的验证码为${p1},请于${p2}时间登陆到我们的服务器
        JSONObject jsonObject = new JSONObject();
        Random random = new Random();
        int ran1 = random.nextInt(10);
        int ran2 = random.nextInt(10);
        int ran3 = random.nextInt(10);
        int ran4 = random.nextInt(10);
        int ran5 = random.nextInt(10);
        String code = ""+ran1+ran2+ran3+ran4+ran5;
        jsonObject.put("code", code);
        String params = jsonObject.toJSONString();
        String mobile = phoneNumber;
        //获取发送结果
        Map<String, String> datas = buildParam(mobile, templateId, params);
        String result = HttpClient4Utils.sendPost(httpClient, API_URL, datas, Consts.UTF_8);

        Map<String, Object> stringObjectMap = GsonUtil.GsonToMaps(result);
        System.out.println(stringObjectMap.get("code"));

        String smsResultCode = String.valueOf((double)stringObjectMap.get("code"));

        returnMap.put("smsResultCode",smsResultCode);
        returnMap.put("smscode",code);
        returnMap.put("phonenumber",mobile);
        return returnMap;

    }

    private static Map<String, String> buildParam(String mobile, String templateId, String params) throws IOException {
        Map<String,String> map = Maps.newHashMap();
        map.put("businessId", BUSINESSID);
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("version", "v2");
        map.put("templateId", templateId);
        map.put("mobile", mobile);
        // 国际短信对应的国际编码(非国际短信接入请注释掉该行代码)
        // map.put("internationalCode", "对应的国家编码");
        map.put("paramType", "json");
        map.put("params", params);
        map.put("nonce", UUID.randomUUID().toString().replace("-", ""));
        map.put("secretId", SECRETID);
        String sign = SignatureUtils.genSignature(SECRETKEY, map);
        map.put("signature", sign);
        return map;
    }

}
