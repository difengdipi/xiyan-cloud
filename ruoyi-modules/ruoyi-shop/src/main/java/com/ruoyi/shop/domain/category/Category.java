package com.ruoyi.shop.domain.category;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

import java.io.Serializable;


/**
 * 分类实体类
 */
@Data // Lombok annotation for auto generating getters and setters
@NoArgsConstructor // Lombok annotation for no-args constructor
@AllArgsConstructor // Lombok annotation for all-args constructor
@Builder // Lombok annotation for builder pattern
@TableName("sys_category") // JPA annotation to specify the table name
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // JPA annotation to indicate the primary key
    private Integer categoryId;

    private String categoryName;

    private String categoryIcon;

    private Integer categoryLayer;

    private Integer categoryParent;
}