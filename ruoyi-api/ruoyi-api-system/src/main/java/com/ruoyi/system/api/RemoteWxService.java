package com.ruoyi.system.api;

import com.ruoyi.system.api.factory.RemoteWxFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/14
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@FeignClient(contextId = "remoteWxService",name = "remoteWxService",url = "https://api.weixin.qq.com" ,fallbackFactory = RemoteWxFallbackFactory.class )
public interface RemoteWxService {
    /**
     * 小程序登录
     * @param appid
     * @param secret
     * @param js_code
     * @param grant_type
     * @return
     */
    @GetMapping("/sns/jscode2session")
    public String getAccess(@RequestParam("appid") String appid, @RequestParam("secret") String secret, @RequestParam("js_code") String js_code, @RequestParam("grant_type") String grant_type);

    /**
     * 网页版登录-微信登录
     * @param appid
     * @param secret
     * @param code
     * @param grant_type
     * @return
     */
    @GetMapping("/sns/oauth2/access_token")
    public String getoauth2(@RequestParam("appid") String appid, @RequestParam("secret") String secret, @RequestParam("code") String code, @RequestParam("grant_type") String grant_type);

    /**
     * 服务端获取微信端的token
     * @param grant_type
     * @param appid
     * @param secret
     * @return
     */
    @GetMapping("/cgi-bin/token")
    public String getWxAccessToken( @RequestParam("grant_type") String grant_type,@RequestParam("appid") String appid, @RequestParam("secret") String secret);

    @GetMapping("/sns/userinfo")
    public String getUserInfo(@RequestParam("access_token") String access_token,@RequestParam("openid") String openid,@RequestParam("lang") String lang);

}
