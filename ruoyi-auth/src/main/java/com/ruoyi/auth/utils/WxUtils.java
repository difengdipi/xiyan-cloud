package com.ruoyi.auth.utils;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.auth.pojos.WxUserInfo;
import com.ruoyi.common.core.utils.sign.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @Description: wx小程序解密工具类
 * @author: zh
 * @Create : 2025/3/29
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public class WxUtils {
    public static WxUserInfo getUserInfo(String encryptedData, String sessionKey, String iv) {
// 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
// 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
// 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result, WxUserInfo.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}