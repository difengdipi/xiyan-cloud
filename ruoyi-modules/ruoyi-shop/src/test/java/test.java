import com.ruoyi.shop.RuoYiShopApplication;
import com.ruoyi.shop.controller.goods.GoodsController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/31
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@SpringBootTest(classes = RuoYiShopApplication.class)
public class test {

    @Resource
    GoodsController good;

    @Test
    public void demo(){
        System.out.println(good.getGoodsByIds(56));
    }
}
