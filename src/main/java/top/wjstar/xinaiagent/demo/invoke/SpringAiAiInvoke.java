package top.wjstar.xinaiagent.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author wxvirus
 * 在 Springboot 项目启动的时候自动注入依赖,并执行大模型
 * SpringAi 内置了几个 Chat Memory
 */
@Component
public class SpringAiAiInvoke implements CommandLineRunner {

    /**
     * SpringAi 调用阿里的大模型
     */
    @Resource
    private ChatModel dashscopeChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = dashscopeChatModel.call(new Prompt("你好,我是无解"))
                .getResult().getOutput();
        System.out.println(output.getText());
    }
}
