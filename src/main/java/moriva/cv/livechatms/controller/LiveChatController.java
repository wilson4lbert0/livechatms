package moriva.cv.livechatms.controller;

import moriva.cv.livechatms.domain.ChatInput;
import moriva.cv.livechatms.domain.ChatOutput;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;

public class LiveChatController {

    @MessageMapping("/new-message")
    @SendTo("/topics-livechat")
    public ChatOutput newMessage(ChatInput input){
        return new ChatOutput(HtmlUtils.htmlEscape(input.user() + ": " + input.message()));
    }
}
