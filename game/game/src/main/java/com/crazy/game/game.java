package com.crazy.game;

import com.crazy.game.Message;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class game {
    public List<String> cards = new ArrayList<>();
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

    public String getCommon() {
        return common;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public void reset()
    {
        for(int i=0;i<cards.size();i++)
        {
            cards.remove(0);
        }
        assignCards();
        users.clear();
        current =0;
        direction=1;
        userNum=0;
        round=0;
        playerC.clear();
        userNames.clear();
        common="";
        for(int i=0;i<4;i++)
        {
            draws[i] = 0;
            scores[i] =0;
        }
        messages.clear();
        draw.clear();
    }


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

    public void setCommon(String common) {
        this.common = common;
    }

    public void assignCards(){
        cards = new ArrayList<>();
        String[] rank = {"A","2","3","4","5","6","7","8","9","T","J","Q","K"};
        String[] suite = {"S","C","H","D"};


        for(int i=0;i< rank.length;i++)
        {
            for(int j=0;j<suite.length;j++)
            {
                cards.add(rank[i]+suite[j]);
            }
        }
    }


    public void setPlayerC(Map<String, List<String>> playerC) {
        this.playerC = playerC;
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
            isDraw();
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
                    for(int i=0;i<userNames.size();i++)
                    {
                        if(direction == -1)
                        {
                            messages.put(userNames.get(i),"Direction of game changed to RIGHT");
                        }
                        else
                        {
                            messages.put(userNames.get(i),"Direction of game changed to LEFT");
                        }

                    }
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
            isDraw();
            if(draws[current]<3) {
                List<String> tempp = new ArrayList<>();

                tempp = playerC.get(userNames.get(current));
                String tempCard;
                tempCard = getCard();

                if(tempCard.charAt(0) == common.charAt(0))
                {
                    draw.put(userNames.get(current),false);
                }
                else if(tempCard.charAt(1) == common.charAt(1))
                {
                    draw.put(userNames.get(current),false);
                }
                else if(tempCard.charAt(0) == '8')
                {
                    draw.put(userNames.get(current),false);
                }
                tempp.add(tempCard);
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

                /*String tempCard;
                tempCard = playerC.get(userNames.get(current)).get(playerC.get(userNames.get(current)).size() - 1);
                if(tempCard.charAt(0) == common.charAt(0))
                {
                    draw.put(userNames.get(current),false);
                }
                else if(tempCard.charAt(1) == common.charAt(1))
                {
                    draw.put(userNames.get(current),false);
                }*/
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
                isDraw();

                message.setDraw(draw);
            }
        }


        //round over condition






        return message;
    }


    public Message managePrivate(Message message)
    {
        return message;
    }

    public String gameOver()
    {
        int temp = 200;
        String n = "";
        boolean check = false;
        for(int i=0;i<userNames.size();i++)
        {
            if(users.get(userNames.get(i)) > 100)
            {
                check = true;
            }
            if(users.get(userNames.get(i)) < temp)
            {
                temp = users.get(userNames.get(i));
                n = userNames.get(i);
            }
        }

        if(check)
        {
            return n;
        }
        else
        {
            return "none";
        }

    }

    public void removeUserCard(String name, String card)
    {
        List<String> temp = new ArrayList<>();
        temp = playerC.get(name);

        int rem= temp.indexOf(card);
        temp.remove(rem);

        playerC.put(name,temp);
    }

    public List<String> get5cards()
    {
        List<String> temp = new ArrayList<>();
        for(int i=0;i<5;i++)
        {
            temp.add(cards.remove(0));

        }
        return temp;
    }

    public void calculateScore()
    {
        for(int i=0;i<userNames.size();i++)
        {
            String name = userNames.get(i);
            users.put(name,listScore(playerC.get(name),users.get(name)));
        }
    }


    public int listScore(List<String> st,int sc)
    {
        int score =sc;

        if(st.size() == 0)
        {
            return score;
        }


        for(int i=0;i<st.size();i++)
        {
            char a = st.get(i).charAt(0);

            if(a=='T' || a =='Q' || a=='K' || a=='J')
            {
                score +=10;
            }
            else if(a == '8')
            {
                score +=50;
            }
            else if(a == 'A')
            {
                score +=1;
            }
            else
            {
                score += Integer.parseInt(String.valueOf(a));
            }
        }

        return score;
    }

    public Message playedEight (Message message)
    {
        message.setStatus("SELECT");
        message.setPlayerTurn(userNames.get(current));
        return message;
    }

    public void refreshMessages()
    {
        for(int i=0;i<userNames.size();i++)
        {
            messages.put(userNames.get(i),"");
        }
    }


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isMoveValid(String check)
    {
        if(check.charAt(0) == '8')
        {
            return true;
        }
        if(check.charAt(0) == common.charAt(0))
        {
            return true;
        }
        else if (check.charAt(1) == common.charAt(1))
        {
            return true;
        }
        return false;
    }

    public void increaseCurrent(){
        current +=direction;
        if(current>=4)
        {
            current =0;
        }

        if(current<0)
        {
            current =3;
        }
    }

    public void isDraw()
    {
        String name;
        name = userNames.get(current);

        List<String> temp2 = new ArrayList<>();
        temp2 = playerC.get(name);
        boolean checking = true;
        for(int i=0;i<temp2.size();i++)
        {
            if(temp2.get(i).charAt(0)=='8')
            {
                checking = false;
            }
            if(temp2.get(i).charAt(0) == common.charAt(0))
            {
                checking = false;
            }
            else if(temp2.get(i).charAt(1) == common.charAt(1))
            {
                checking = false;
            }
        }


        for(int i=0;i<userNames.size();i++)
        {
            draw.put(userNames.get(i),false);
        }

        draw.put(userNames.get(current),true);
    }

    public String getCard()
    {
        if(cards.size()>=1) {
            return cards.remove(0);
        }
        return null;
    }

    public boolean isRoundOver()
    {
        if(playerC.size()==0)
        {
            return false;
        }
        for(int i=0;i<userNames.size();i++)
        {
            if(playerC.get(userNames.get(i)).size() == 0)
            {
                return true;
            }
        }

        if(cards.size()==0)
        {
            List<String> temp = new ArrayList<>();
            temp = playerC.get(userNames.get(current));
            Boolean over;
            for(int i=0;i<temp.size();i++)
            {
                if(temp.get(i).charAt(0)=='8')
                {
                    return false;
                }
                if(temp.get(i).charAt(0) == common.charAt(0))
                {
                    return false;
                }
                else if(temp.get(i).charAt(1) == common.charAt(1))
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }


        return true;
    }

}
