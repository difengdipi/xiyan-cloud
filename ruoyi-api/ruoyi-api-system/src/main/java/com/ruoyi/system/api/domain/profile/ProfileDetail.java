package com.ruoyi.system.api.domain.profile;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProfileDetail{

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账户名
     */
    private String account;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 省市区
     */
    private String fullLocation;

    /**
     * 职业
     */
    private String profession;
}