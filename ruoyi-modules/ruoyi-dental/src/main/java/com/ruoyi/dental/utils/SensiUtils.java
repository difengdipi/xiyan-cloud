package com.ruoyi.dental.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description: 敏感信息工具类
 * @author: zh
 * @Create : 2025/4/8
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
public class SensiUtils {
    /**
     * 加密身份证号 -- 加密规则为加密中间8位
     * @return
     */
    public final static String EntryIdCard(String IdCard){
        if(IdCard.length()>18 || IdCard.length()<15){
            return "身份证号格式不符合";
        }
        StringBuilder sb = new StringBuilder();
        if(IdCard.length()==18){
            sb.append(IdCard.substring(0,6));
            sb.append("********");
            sb.append(IdCard.substring(14,18));
            return sb.toString();
        }else{
            sb.append(IdCard.substring(0,4));
            sb.append("********");
            sb.append(IdCard.substring(10,14));
        }
        return sb.toString();
    }
    /**
     * 根据身份证号获取年龄
     * @param IdCard 身份证号码（15位或18位）
     * @return 年龄（Long类型），如果身份证号无效返回null
     */
    public final static Long getAge(String IdCard) {
        if (IdCard == null || (IdCard.length() != 15 && IdCard.length() != 18)) {
            return null; // 身份证号格式无效
        }

        try {
            // 提取出生日期（兼容15位和18位身份证）
            String birthDateStr = IdCard.length() == 15 ?
                    "19" + IdCard.substring(6, 12) : // 15位：7-12位是YYMMDD
                    IdCard.substring(6, 14);         // 18位：7-14位是YYYYMMDD

            // 解析出生日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            Date birthDate = sdf.parse(birthDateStr);

            // 获取当前日期
            Calendar now = Calendar.getInstance();

            // 计算年龄
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthDate);

            long age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);

            // 处理未过生日的情况
            if (now.get(Calendar.MONTH) < birth.get(Calendar.MONTH) ||
                    (now.get(Calendar.MONTH) == birth.get(Calendar.MONTH) &&
                            now.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH))) {
                age--;
            }
            return age;
        } catch (Exception e) {
            return null; // 日期解析错误
        }
    }

    /**
     * 根据身份证号获取性别---国内省份正号规则为导数第二位为奇数男，偶数女
     * @param IdCard
     * @return 性别：0-男性,1-女性
     */
    public final static int getSec(String IdCard){
        int i = Integer.parseInt(IdCard.substring(16, 17));
        return i%2==0?1:0;
    }

    /**
     * 加密手机号
     * @param phone
     * @return
     */
    public  final static String  EntryPhone(String phone){
        if(phone.length()!=11){
            return "手机号格式错误";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(phone.substring(0,3));
        sb.append("****");
        sb.append(phone.substring(7,11));
        return sb.toString();
    }
}
