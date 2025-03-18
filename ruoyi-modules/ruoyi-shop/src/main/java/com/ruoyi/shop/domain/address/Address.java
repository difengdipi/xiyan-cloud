package com.ruoyi.shop.domain.address;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 收货地址实体类
 */
@Data // 使用 Lombok 自动生成 getter 和 setter 方法
@TableName("sys_address") // 映射到数据库表 address
public class Address {

    @TableId(type = IdType.AUTO) // 主键自增
    private Integer id; // 地址ID

    private Long userId; // 用户id

    private String receiver; // 收货人姓名

    private String contact; // 联系方式

    private String provinceCode; // 省份编码

    private String cityCode; // 城市编码

    private String countyCode; // 区/县编码

    private String address; // 详细地址

    private Integer isDefault; // 是否默认地址, 1为是，0为否
    //时间格式
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; // 创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime; // 更新时间
}