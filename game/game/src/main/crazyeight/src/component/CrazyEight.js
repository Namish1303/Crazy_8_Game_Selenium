import React, {useEffect, useState} from "react"
import {over} from 'stompjs';
import SockJS from 'sockjs-client';
import './ce.css';
var stompClient = null;
const CrazyEight =() =>{
  const [userData, setUserData] = useState({
    username:"",
    connected:false,
    suite:"",

  });
  const [gameStart,setStart] = useState(false);
  const [players,setPlayers] = useState(new Map());
  const [cards, setCards] = useState([]);
  const [turn,setTurn] = useState();
  const [common,setCommon] = useState();
  const [decksize,setDeckSize] = useState();
  const [isTurn,setisTurn] = useState(false);
  const [systemMessage,setMessage] = useState();
  const [suite,setSuite] = useState(false);
  const [draw,setDraw] = useState(false);
  const [round,setRound] = useState(false);
  const [gameOver,setGameOver] = useState(false);
  const [winner,setWinner] = useState();

  const handleUsername=(event)=>{
    const {value} = event.target;
    setUserData({...userData,"username":value})
    
  }

  const selectingSuite=(event)=>
  {
      const {value} = event.target;
      setUserData({...userData,"suite":value});
      console.log(value);
      var suiteMessage={
      status:"SUITE",
      user:value
    }
    stompClient.send("/game/message",{},JSON.stringify(suiteMessage));
  }


  const registerUser=()=>{
    connect();
  }
  
  const connect=()=>
  {
    let Sock = new SockJS("http://localhost:8080/crazy");
    stompClient = over(Sock);
    stompClient.connect({},onConnected,onError);
  }

  const onError=(err)=>
  {
      console.log(err);
  }

  const onConnected=()=>{
    setUserData({...userData,"connected":true});
    stompClient.subscribe('/chatroom/public', onMessageReceived);
    stompClient.subscribe('/user/'+userData.username+"/private",onPrivateMessage);
    var gameMessage ={
      status: "SetUser",
      user: userData.username,
    }
    stompClient.send("/game/message",{},JSON.stringify(gameMessage));
    

  
  }
  const handleClick = event =>
  {
    console.log(event.currentTarget.id);
    sendCard(event.currentTarget.id);
  }

  const sendCard = (bid)=>
  {
    if(stompClient){
      var cMessage ={
        cardPlayed: bid,
        status: "PLAY" 

      }

      console.log(cMessage);
      stompClient.send("/game/message",{},JSON.stringify(cMessage));
    }
  }

  const sendDraw =() =>
  {
    var drawMessage={
      status:"DRAW"
    }
    stompClient.send("/game/message",{},JSON.stringify(drawMessage));
  }

  const readyRound=()=>
  {
    //setStart(true);
    setRound(false);
    var RoundMessage ={
      status:"READY"
    }
    stompClient.send("/game/message",{},JSON.stringify(RoundMessage));

  }

  const onMessageReceived = (payload)=>
  {
       
      var payloadData = JSON.parse(payload.body);
      if(payloadData.status == 'START')
      {
        let arr = payloadData.cards[userData.username];
        setStart(true);
        setSuite(false);
        setRound(false);
        setDraw(payloadData.draw[userData.username]);
          if(payloadData.playerTurn == userData.username)
        {
          setisTurn(true);
          //console.log(isTurn);
        }
        else{
          setisTurn(false);
        }
        setTurn(payloadData.playerTurn);
        setPlayers(payloadData.users);
        setCommon(payloadData.commonCard);
        console.log(payloadData.playerTurn==userData.username);
        
        setDeckSize(payloadData.cardsLeft);
        
        setCards(arr);
        
        console.log(gameStart)
      }



      else if (payloadData.status == 'CHANCE')
      {
        let arr = payloadData.cards[userData.username];
        //setStart(true);
        setSuite(false);
        setDraw(payloadData.draw[userData.username]);
        if(payloadData.playerTurn == userData.username)
        {
          setisTurn(true);
          //console.log(isTurn);
        }
        else{
          setisTurn(false);
        }
        setTurn(payloadData.playerTurn);
        //setPlayers(payloadData.users);
        setCommon(payloadData.commonCard);
        setDeckSize(payloadData.cardsLeft);
        
        setCards(arr);
        setMessage(payloadData.messages[userData.username]);
      }

      else if(payloadData.status == 'SELECT')
      {
        setSuite(true);
        console.log("REACHED");
      }
      else if(payloadData.status == 'OVER')
      {
        setRound(true);
        setPlayers(payloadData.users);
        setStart(false);
      }
      else if(payloadData.status == "GAME")
      {
        setGameOver(true);
        setPlayers(payloadData.users);
        setWinner(payloadData.user);
        setUserData({...userData,"connected":false});
      }
      //console.log(payloadData.cards[userData.username]);


  }



  useEffect(()=>{
  console.log(isTurn)},[cards])

  const onPrivateMessage = (payload)=>
  {
    console.log(payload)
  }

  return (
    <div className="container">
      <h3>{userData.username}</h3>
      {userData.connected?
      <div> 
         {gameStart?
        <div>
          <p className="Turn" id="turn">Turn:  {turn}</p>
          <span>&nbsp;</span>
          <ul>
            {Object.keys(players).map((value,index)=>
            <li>{value} : {players[value]}</li>)}

          </ul> <p className="serverMessage">Message from Server: {systemMessage}</p>
          <span>&nbsp;&nbsp;</span>
          <button className="common" id="common">{common}</button>   <p>Cards left in deck: {decksize}</p>
          {draw?
          <div>
            <button onClick={sendDraw} className="drawButton" id="draw">Draw</button>
            </div>:<div><button onClick={sendDraw} id="draw" disabled>Draw</button></div>}
          {suite && isTurn?
          <div>
            &nbsp;&nbsp;
          <select onChange={selectingSuite} id="suiteSelect">
            <option value="none">None</option>
            <option value="H">Hearts</option>
            <option value="S">Spades</option>
            <option value="C">Clubs</option>
            <option value="D">Diamonds</option>

          </select>
          </div>
          :
          <div>{isTurn?
            <div>
            {cards.map((card,index) =>
              <button className="hand" id={card} onClick={handleClick}> {card} </button> 
            
              )}
              </div>
            :
            <div>
            {cards.map((card,index) =>
              <button className="hand" id={card} disabled> {card} </button> 
            
              )}
              </div>
            }</div>}
            
        </div>
        :
        <div>
        {round?
          <div>
          <ul>
            {Object.keys(players).map((value,index)=>
            <li id={value}>{value} : {players[value]}</li>)}

          </ul>
          <h4>Start next round ?</h4>
          <button className="round" onClick={readyRound} id="start">Start</button>
          </div>
        :
          <div>
          <p>Welcome</p>
          <h1>WAIT FOR PLAYERS TO JOIN</h1>
          </div>
        }
        
        </div>
      }
        </div>

      :
      <div>
      {gameOver?
      <div>
      <h1 id="gameOver">GAME OVER!!!</h1>
      
      <ul>
          {Object.keys(players).map((value,index)=>
          <li id={value}>{value} : {players[value]}</li>)}

          </ul>
        <h3> Winner:  {winner}</h3>
      </div>
      
      
        :
      <div className="register"> 
      <input
        id = "username"
        placeholder="Enter your name"
        name = "username"
        value = {userData.username}
        onChange={handleUsername}
        margin = "normal"
        />
        <button type="button" id="connect" onClick={registerUser}>
          connect
        </button>
      </div>
      }
      </div>}
        
    </div>
  )
}

export default CrazyEight

//Queens
//Playing a Queen causes the next player to miss their turn.
//b) Aces
//Playing an Ace reverses the direction of play (i.e., modifies who is the next player to play).