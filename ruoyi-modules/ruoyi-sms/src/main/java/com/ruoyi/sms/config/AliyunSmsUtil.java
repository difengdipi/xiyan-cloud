package com.ruoyi.sms.config;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.sms.inter.SmsComInterFace;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AliyunSmsUtil implements SmsComInterFace {
    @Value("${aliyun.sms.sms-access-key-id}")
    private String accessKeyId;

    @Value("${aliyun.sms.sms-access-key-secret}")
    private String accessKeySecret;

    @Value("${aliyun.sms.sms-sign-nam}")
    private String signName;

    @Value("${aliyun.sms.sms-template-code}")
    private String templateCode;

    @Value("${aliyun.sms.sms-endpoint}")
    private String endpoint;
    @Value("${aliyun.sms.region-id}")
    private String regionId;


    public  R sendSms(String phoneNumber, Map<String, Object> templateParams) {
        try {
            DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
            IAcsClient client = new DefaultAcsClient(profile);

            SendSmsRequest request = new SendSmsRequest();
            request.setPhoneNumbers(phoneNumber);
            request.setSignName(signName);
            request.setTemplateCode(templateCode);
            // 将HashMap转化为JSON字符串
            String templateParam = JSON.toJSONString(templateParams);
            request.setTemplateParam(templateParam);

            SendSmsResponse response = client.getAcsResponse(request);
            if(response.getCode() != null && response.getCode().equals("OK")){
                return R.ok();
            }
            return R.fail(response.getMessage());
        } catch (ClientException e) {
            e.printStackTrace();
            return null;
        }
    }

}
