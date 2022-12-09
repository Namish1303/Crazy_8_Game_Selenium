package com.crazy.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    @Autowired
    game g;



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
