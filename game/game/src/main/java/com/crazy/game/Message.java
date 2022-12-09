package com.crazy.game;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Message {

    String playerTurn;
    String commonCard;
    String status;
    String user;
    Map<String,List<String>> cards = new HashMap<>();
    Map<String,Integer> users = new HashMap<>();
    int cardsLeft;
    String cardPlayed;
    Map<String,String> messages = new HashMap<>();
    Map<String,Boolean> draw = new HashMap<>();

}
