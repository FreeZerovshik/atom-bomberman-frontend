package com.game.event;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class EventController {
    @MessageMapping("/connect")
    @SendTo("/topic/greetings")
    public void connection() {
        System.out.println(">>>>>>>> I'm connected!!!!");
    }
}
