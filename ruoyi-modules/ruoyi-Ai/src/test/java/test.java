import com.ruoyi.ai.RuoYiAiapplication;
import com.ruoyi.ai.component.OpenChatModel;
import com.ruoyi.ai.feign.DeepSeekFegin;
import com.ruoyi.ai.pojos.ChatRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

/**
 * @Description:
 * @author: zh
 * @Create : 2025/3/12
 * @Project_name : spring-Ai
 * @Version :
 **/
@SpringBootTest(classes = RuoYiAiapplication.class)
public class test {

    @Autowired
    DeepSeekFegin deepSeekFegin;
    @Autowired
    OpenChatModel openChatModel;

    /**
     * 文本型请求案例
     */
    @Test
    public void demo(){
        ChatRequest chatRequest = new ChatRequest();
        ChatRequest.Message message = new ChatRequest.Message();
        message.setContent("今天上海的天气如何，请你用贴吧的语气告诉我");
        message.setRole("user");
        chatRequest.setModel("deepseek-chat");
        chatRequest.setMessages(Arrays.asList(message));
        chatRequest.setStream(false);
        System.out.println(deepSeekFegin.getContent(openChatModel.getDpApiKey(), chatRequest));
    }
}
