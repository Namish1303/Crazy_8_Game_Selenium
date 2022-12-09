package com.crazy.game;

import com.crazy.game.rules.game;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    game g = new game();

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message)
    {
        Message temp;
        temp = message;
        message = g.manage(temp);
        return message;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message)
    {
        Message temp;
        temp = message;
        message = g.managePrivate(temp);
        return message;
    }
}
