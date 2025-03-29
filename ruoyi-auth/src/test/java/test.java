import com.ruoyi.auth.RuoYiAuthApplication;
import com.ruoyi.auth.config.WxConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/28
 * @Project_name : RuoYi-Cloud
 * @Version :
 **/
@SpringBootTest(classes = RuoYiAuthApplication.class)
public class test {

    @Autowired
    WxConfig wxConfig;

    @Test
    public void test1(){
        wxConfig.init();
    }

}
