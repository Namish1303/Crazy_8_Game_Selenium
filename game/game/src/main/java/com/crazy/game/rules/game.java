package com.crazy.game.rules;

import com.crazy.game.Message;

import java.util.*;

public class game {
    List<String> cards = new ArrayList<>();
    Map<String,Integer> users = new HashMap<>();
    int current;
    int direction;
    int userNum;
    int round;
    int in;
    List<String> userNames = new ArrayList<>();
    Map<String,List<String>> playerC = new HashMap<>();
    String common;
    int[] draws = new int[4];
    int[] scores = new int[4];
    Map<String,String> messages = new HashMap<>();
    Map<String,Boolean> draw = new HashMap<>();
    public game()
    {
        assignCards();
        Collections.shuffle(cards);
        current =0;
        for(int i=0;i<4;i++)
        {
            scores[i] =0;
            draws[i] =0;
        }
        userNum=0;
        direction =1;
        round=1;
    }






    public Message manage(Message message)
    {
        //refresh messages before every chance
        refreshMessages();


        //set user for first 3
        if(message.getStatus().equals("SetUser") && userNum <3)
        {
            users.put(message.getUser(),scores[userNum]);
            userNames.add(message.getUser());
            messages.put(message.getUser(),"");
            draw.put(message.getUser(),false);
            userNum +=1;
            message.setStatus("WAIT");
            message.setUsers(users);
            message.setMessages(messages);
            message.setDraw(draw);

        }




        //set user for last one
        else if(message.getStatus().equals("SetUser") && userNum ==3)
        {
            users.put(message.getUser(),scores[userNum]);
            userNames.add(message.getUser());
            messages.put(message.getUser(),"");
            draw.put(message.getUser(),false);
            userNum +=1;

            for(int i=0;i<userNames.size();i++)
            {
                playerC.put(userNames.get(i),get5cards());
            }

            message.setPlayerTurn(userNames.get(current));
            common = getCard();
            while(common.charAt(0) == '8')
            {
                Random ran = new Random();
                int x = ran.nextInt(cards.size());
                cards.add(x,common);
                common = getCard();
            }
            message.setCommonCard(common);
            message.setCards(playerC);
            message.setStatus("START");
            message.setUsers(users);
            message.setCardsLeft(cards.size());
            message.setMessages(messages);
            message.setDraw(draw);
        }



        // card played by user
        if (message.getStatus().equals("PLAY"))
        {
            if(isMoveValid(message.getCardPlayed()))
            {
                if(message.getCardPlayed().charAt(0)=='8')
                {
                    message = playedEight(message);
                    removeUserCard(userNames.get(current),message.getCardPlayed());
                    return message;
                }

                removeUserCard(userNames.get(current),message.getCardPlayed());
                if(message.getCardPlayed().charAt(0) == 'A')
                {
                    direction *=-1;
                }
                draws[current] = 0;
                increaseCurrent();
                common = message.getCardPlayed();

                if(message.getCardPlayed().charAt(0)=='Q')
                {
                    messages.put(userNames.get(current),"Turn Skiped");
                    increaseCurrent();
                }
            }
            else
            {
                messages.put(userNames.get(current),"Play a Valid Card");
            }


            // all changes have been made
            if(!isRoundOver())
            {
                message.setPlayerTurn(userNames.get(current));
                message.setCommonCard(common);
                message.setStatus("CHANCE");
                message.setUsers(users);
                message.setCardsLeft(cards.size());
                message.setCards(playerC);
                message.setCardPlayed("");
                message.setMessages(messages);
                isDraw();
                message.setDraw(draw);
            }
            else
            {
                calculateScore();
                String name = gameOver();
                if(name.equals("none")) {
                    message.setStatus("OVER");

                    message.setUsers(users);
                    userNum=0;
                }
                else
                {
                    message.setStatus("GAME");

                    message.setUsers(users);
                    message.setUser(name);
                }


            }


        }

        else if (message.getStatus().equals("SUITE")) {
            common = " "+message.getUser();
            increaseCurrent();

            if(!isRoundOver())
            {
                message.setPlayerTurn(userNames.get(current));
                message.setCommonCard(common);
                message.setStatus("CHANCE");
                message.setUsers(users);
                message.setCardsLeft(cards.size());
                message.setCards(playerC);
                message.setCardPlayed("");
                message.setMessages(messages);
                isDraw();
                message.setDraw(draw);
            }
            else
            {
                calculateScore();
                String name = gameOver();
                if(name.equals("none")) {
                    message.setStatus("OVER");

                    message.setUsers(users);
                    userNum=0;
                }
                else
                {
                    message.setStatus("GAME");

                    message.setUsers(users);
                    message.setUser(name);
                }


            }
        }

        else if(message.getStatus().equals("DRAW"))
        {
            if(draws[current]<3) {
                List<String> tempp = new ArrayList<>();

                tempp = playerC.get(userNames.get(current));
                tempp.add(getCard());
                playerC.put(userNames.get(current), tempp);
                draws[current] += 1;
            }
            else
            {
                messages.put(userNames.get(current),"3 Draws done");
                draws[current]=0;
                increaseCurrent();
            }
            if(!isRoundOver())
            {
                message.setPlayerTurn(userNames.get(current));
                message.setCommonCard(common);
                message.setStatus("CHANCE");
                message.setUsers(users);
                message.setCardsLeft(cards.size());
                message.setCards(playerC);
                message.setCardPlayed("");
                message.setMessages(messages);
                isDraw();
                message.setDraw(draw);
            }
            else
            {
                calculateScore();
                String name = gameOver();
                if(name.equals("none")) {
                    message.setStatus("OVER");

                    message.setUsers(users);
                    userNum=0;
                }
                else
                {
                    message.setStatus("GAME");

                    message.setUsers(users);
                    message.setUser(name);
                }


            }
        }

        else if(message.getStatus().equals("READY"))
        {
            if(userNum <3)
            {
                userNum +=1;
            }
            else
            {
                assignCards();
                Collections.shuffle(cards);
                current =0;
                direction = 1;
                message.setStatus("START");
                message.setMessages(messages);
                message.setPlayerTurn(userNames.get(current));
                common = getCard();
                message.setCommonCard(common);
                message.setUsers(users);

                for(int i=0;i<userNames.size();i++)
                {
                    playerC.put(userNames.get(i),get5cards());
                }
                message.setCards(playerC);
                message.setCardsLeft(cards.size());
                message.setCardPlayed("");
                message.setDraw(draw);
            }
        }


        //round over condition






        return message;
    }
}