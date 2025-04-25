package com.ruoyi.shop.controller.category;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.shop.domain.category.Category;
import com.ruoyi.shop.domain.category.vo.CategoryItemVo;
import com.ruoyi.shop.service.category.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.shop.constants.ShopCacheConstants.CATEGORY_KEY;

/**
 * @Description: 小程序home分类controller
 * @author: zh
 * @Create : 2025/3/19
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@RestController
@RequestMapping("/home/category")
@Tag(name = "小程序homeController")
@Slf4j
public class HomeCategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    RedisService redisService;
    @PostConstruct
    public void init(){
        log.info("HomeCategoryController Bean加载完成，开始初始化首页数据");
        mutiliCategory();
    }
    @GetMapping("/mutli")
    @Operation(summary = "前台分类")
    public R mutiliCategory(){
        if(redisService.hasKey(CATEGORY_KEY)){
            return R.ok( redisService.getCacheObject(CATEGORY_KEY));
        }
        List<Category> list = categoryService.list();
        ArrayList<CategoryItemVo> categoryItemVos = new ArrayList<>();
        for (Category category : list) {
            CategoryItemVo build = CategoryItemVo.builder().iconUrl(category.getCategoryIcon())
                    .id(category.getCategoryId())
                    .name(category.getCategoryName()).build();
            categoryItemVos.add(build);
        }
        redisService.setCacheObject(CATEGORY_KEY,categoryItemVos,ThreadLocalRandom.current().nextLong(20,50), TimeUnit.MINUTES);
        return R.ok(categoryItemVos);
    }
}
