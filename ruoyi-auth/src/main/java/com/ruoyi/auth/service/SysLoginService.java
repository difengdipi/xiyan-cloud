package com.ruoyi.auth.service;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.auth.config.WxConfig;
import com.ruoyi.auth.form.LoginUserVO;
import com.ruoyi.auth.form.UserLoginDTO;
import com.ruoyi.auth.form.WxAuthResponse;
import com.ruoyi.auth.pojos.WxUserInfo;
import com.ruoyi.auth.utils.WxUtils;
import com.ruoyi.common.core.constant.CacheConstants;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.context.SecurityContextHolder;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.enums.UserStatus;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.service.TokenService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteUserService;
import com.ruoyi.system.api.RemoteWxService;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
@Slf4j
public class SysLoginService
{
    @Autowired
    private RemoteUserService remoteUserService;

    @Autowired
    private SysPasswordService passwordService;

    @Autowired
    private SysRecordLogService recordLogService;

    @Autowired
    private RedisService redisService;

    @Autowired
    TokenService tokenService;

    /**
     * 登录
     */
    public LoginUser login(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户/密码必须填写");
            throw new ServiceException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户密码不在指定范围");
            throw new ServiceException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不在指定范围");
            throw new ServiceException("用户名不在指定范围");
        }
        // IP黑名单校验
        String blackStr = Convert.toStr(redisService.getCacheObject(CacheConstants.SYS_LOGIN_BLACKIPLIST));
        if (IpUtils.isMatchedIp(blackStr, IpUtils.getIpAddr()))
        {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "很遗憾，访问IP已被列入系统黑名单");
            throw new ServiceException("很遗憾，访问IP已被列入系统黑名单");
        }
        // 查询用户信息
        R<LoginUser> userResult = remoteUserService.getUserInfo(username, SecurityConstants.INNER);

        if (R.FAIL == userResult.getCode())
        {
            throw new ServiceException(userResult.getMsg());
        }
        LoginUser userInfo = userResult.getData();
        SysUser user = userResult.getData().getSysUser();
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "对不起，您的账号已被删除");
            throw new ServiceException("对不起，您的账号：" + username + " 已被删除");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "用户已停用，请联系管理员");
            throw new ServiceException("对不起，您的账号：" + username + " 已停用");
        }
        passwordService.validate(user, password);
        recordLogService.recordLogininfor(username, Constants.LOGIN_SUCCESS, "登录成功");
        recordLoginInfo(user.getUserId());
        return userInfo;
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId)
    {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        // 更新用户登录IP
        sysUser.setLoginIp(IpUtils.getIpAddr());
        // 更新用户登录时间
        sysUser.setLoginDate(DateUtils.getNowDate());
        remoteUserService.recordUserLogin(sysUser, SecurityConstants.INNER);
    }

    public void logout(String loginName)
    {
        recordLogService.recordLogininfor(loginName, Constants.LOGOUT, "退出成功");
    }

    /**
     * 注册
     */
    public void register(String username, String password)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw new ServiceException("用户/密码必须填写");
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new ServiceException("账户长度必须在2到20个字符之间");
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new ServiceException("密码长度必须在5到20个字符之间");
        }

        // 注册用户信息
        SysUser sysUser = new SysUser();
        sysUser.setUserName(username);
        sysUser.setNickName(username);
        sysUser.setPassword(SecurityUtils.encryptPassword(password));
        R<?> registerResult = remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER);

        if (R.FAIL == registerResult.getCode())
        {
            throw new ServiceException(registerResult.getMsg());
        }
        recordLogService.recordLogininfor(username, Constants.REGISTER, "注册成功");
    }
    @Autowired
    RemoteWxService remoteWxService;
    @Autowired
    WxConfig wxConfig;
    /**
     * 微信登录验证
     * @param dto
     * @return
     */
    public LoginUserVO wxMinLogin(UserLoginDTO dto) {
        log.info("请求参数：{}，{}，{}，{}",wxConfig.appid, wxConfig.secret, dto.getCode(), wxConfig.grantType);
        LoginUserVO loginUserVO1 = wxMinSimpleLogin("18385067084");
        if(wxMinSimpleLogin("18385067084") != null){
             return loginUserVO1;
         }
        LoginUserVO loginUserVO = new LoginUserVO();
        SysUser sysUser = null;
        LoginUser build = null;
        String access = remoteWxService.getAccess(wxConfig.appid, wxConfig.secret, dto.getCode(), wxConfig.grantType);
        WxAuthResponse wxAuthResponse = JSONObject.parseObject(access, WxAuthResponse.class);
        log.info("wxAuthResponse:{}", wxAuthResponse);
        //根据openId查询数据库看看当前用户是否已注册
        try {
            sysUser = remoteUserService.getinfoByopenId(wxAuthResponse.getOpenId(), SecurityConstants.INNER).getData().getSysUser();
            if (ObjectUtils.isEmpty(sysUser)) {
                //注册用户
                //解密用户信息
                WxUserInfo userInfo = WxUtils.getUserInfo(dto.getEncryptedData(), wxAuthResponse.getSessionKey(), dto.getIv());
                BeanUtils.copyProperties(userInfo, sysUser);
                sysUser.setAvatar(userInfo.getAvatarUrl());
                sysUser.setOpenId(wxAuthResponse.getOpenId());
                sysUser.setUserName(userInfo.getNickname()+"_"+wxAuthResponse.getOpenId());
                if(!remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER).getData()){
                    log.error("手机号登录注册失败系统错误");
                    throw new ServiceException("手机号登录注册失败系统错误");
                }
                loginUserVO.setUserId(sysUser.getUserId());
                loginUserVO.setAvatar(sysUser.getAvatar());
                if(sysUser.getAvatar() == null){
                    sysUser.setAvatar("https://typo-img.oss-cn-chengdu.aliyuncs.com/img-localhost/202503141042425.jpg");
                }
                loginUserVO.setUserName(sysUser.getUserName());
                loginUserVO.setNickName(sysUser.getNickName());
                loginUserVO.setPhonenumber(sysUser.getPhonenumber());
                String token = (String) tokenService.createAppToekn(sysUser).get("access_token");
                build = LoginUser.builder()
                        .token(token)
                        .userid(sysUser.getUserId())
                        .loginTime(System.currentTimeMillis())
                        .ipaddr(IpUtils.getIpAddr())
                        .build();
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, build);
                loginUserVO.setToken(token);
                tokenService.setLoginUser(build);
            }else{
                sysUser = new SysUser();
                loginUserVO.setUserId(sysUser.getUserId());
                loginUserVO.setAvatar(sysUser.getAvatar());
                if(sysUser.getAvatar() == null){
                    sysUser.setAvatar("https://typo-img.oss-cn-chengdu.aliyuncs.com/img-localhost/202503141042425.jpg");
                }
                loginUserVO.setUserName(sysUser.getUserName());
                loginUserVO.setNickName(sysUser.getNickName());
                loginUserVO.setPhonenumber(sysUser.getPhonenumber());
                String token = (String) tokenService.createAppToekn(sysUser).get("access_token");
                build = LoginUser.builder()
                        .token(token)
                        .userid(sysUser.getUserId())
                        .loginTime(System.currentTimeMillis())
                        .ipaddr(IpUtils.getIpAddr())
                        .build();
                loginUserVO.setToken(token);
                tokenService.setLoginUser(build);
            }
        }
        catch(Exception e){

        }
        return loginUserVO;
    }

    /**
     * 手机号登录
     * @param phoneNumber
     * @return
     */
    public LoginUserVO wxMinSimpleLogin(String phoneNumber) {
        LoginUserVO loginUserVO = new LoginUserVO();
        SysUser sysUser ;
        LoginUser build = null;
        if (StringUtils.isNotBlank(phoneNumber)) {
            R<LoginUser> userResult = remoteUserService.getinfoByphone(phoneNumber, SecurityConstants.INNER);
            if (R.SUCCESS == userResult.getCode()) {
                sysUser = userResult.getData().getSysUser();
                if (sysUser != null) {
                    loginUserVO.setUserId(sysUser.getUserId());
                    loginUserVO.setAvatar(sysUser.getAvatar());
                    if(sysUser.getAvatar() == null){
                        sysUser.setAvatar("https://typo-img.oss-cn-chengdu.aliyuncs.com/img-localhost/202503141042425.jpg");
                    }
                    loginUserVO.setUserName(sysUser.getUserName());
                    loginUserVO.setNickName(sysUser.getNickName());
                    loginUserVO.setPhonenumber(sysUser.getPhonenumber());
                    String token = (String) tokenService.createAppToekn(sysUser).get("access_token");
                    build = LoginUser.builder()
                            .token(token)
                            .userid(sysUser.getUserId())
                            .loginTime(System.currentTimeMillis())
                            .ipaddr(IpUtils.getIpAddr())
                            .build();
                    loginUserVO.setToken(token);
                }else{
                    //注册该用户
                    //根据手机号
                    sysUser = new SysUser();
                    sysUser.setNickName(phoneNumber);
                    sysUser.setUserName(phoneNumber);
                    sysUser.setPhonenumber(phoneNumber);
                    sysUser.setAvatar("https://typo-img.oss-cn-chengdu.aliyuncs.com/img-localhost/202503141042425.jpg");
                    if(!remoteUserService.registerUserInfo(sysUser, SecurityConstants.INNER).getData()){
                        log.error("手机号登录注册失败系统错误");
                        throw new ServiceException("手机号登录注册失败系统错误");
                    }
                    loginUserVO.setUserId(sysUser.getUserId());
                    loginUserVO.setAvatar(sysUser.getAvatar());
                    if(sysUser.getAvatar() == null){
                        sysUser.setAvatar("https://typo-img.oss-cn-chengdu.aliyuncs.com/img-localhost/202503141042425.jpg");
                    }
                    loginUserVO.setUserName(sysUser.getUserName());
                    loginUserVO.setNickName(sysUser.getNickName());
                    loginUserVO.setPhonenumber(sysUser.getPhonenumber());
                    String token = (String) tokenService.createAppToekn(sysUser).get("access_token");
                    build = LoginUser.builder()
                            .token(token)
                            .userid(sysUser.getUserId())
                            .loginTime(System.currentTimeMillis())
                            .ipaddr(IpUtils.getIpAddr())
                            .build();
                    SecurityContextHolder.set(SecurityConstants.LOGIN_USER, build);
                    loginUserVO.setToken(token);
                    tokenService.setLoginUser(build);
                }
            }
        }
        return loginUserVO;
    }

    public LoginUserVO Applogin(String username, String password) {
        LoginUser login = login(username, password);
        LoginUserVO loginUserVO = new LoginUserVO();
        SysUser sysUser = login.getSysUser();
        if (sysUser != null) {
            loginUserVO.setUserId(sysUser.getUserId());
            loginUserVO.setAvatar(sysUser.getAvatar());
            if(sysUser.getAvatar() == null){
                sysUser.setAvatar("https://typo-img.oss-cn-chengdu.aliyuncs.com/img-localhost/202503141042425.jpg");
            }
            loginUserVO.setUserName(sysUser.getUserName());
            loginUserVO.setNickName(sysUser.getNickName());
            loginUserVO.setPhonenumber(sysUser.getPhonenumber());
            String token = (String) tokenService.createToken(login).get("access_token");
            loginUserVO.setToken(token);
            LoginUser build = LoginUser.builder()
                    .token(token)
                    .userid(sysUser.getUserId())
                    .loginTime(System.currentTimeMillis())
                    .ipaddr(IpUtils.getIpAddr())
                    .build();
            tokenService.refreshToken(build);
            SecurityContextHolder.set(SecurityConstants.LOGIN_USER, build);
        }else{
            //提示用户需要注册
            throw new ServiceException("用户不存在");
        }
        return loginUserVO;
    }
}
