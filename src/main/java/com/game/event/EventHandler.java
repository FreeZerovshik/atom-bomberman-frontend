package com.game.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.game.model.*;
import com.game.network.ConnectionPool;
import com.game.service.GameRepository;
import com.game.service.GameSession;
import com.game.service.MatchMakerService;
import com.game.service.Replicator;
import com.game.util.JsonInterface;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);
    private static ConcurrentHashMap<WebSocketSession, Pawn> mapPawns= new ConcurrentHashMap<>();


    private static final Object lock = new Object();

//    @Autowired
    private ConnectionPool connectionPool;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSession gameSession;

    @Autowired
    Replicator replicator;

    @Autowired
    MatchMakerService matchMakerService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        connectionPool = ConnectionPool.getInstance();
        log.info("Socket Connected: " + session);

         // вычитываем игрока из очереди и помещаем в очередь в новой игре
         connectionPool.add(session, gameRepository.getPlayer());
         log.info("pool size="+connectionPool.size());

         if (connectionPool.size() == GameRepository.PLAYERS_IN_GAME) {
             matchMakerService.startGame(connectionPool.getPlayerPool());
             connectionPool.newPlayerPool();
         }



    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        log.info("Received " + message.getPayload());

        JsonNode jsonNode = JsonInterface.getJsonNode(message.getPayload());
        String topic = jsonNode.findValue("topic").asText();
        String replica = null;

        switch (topic) {
            case "MOVE" : replica = playerAction(jsonNode, session); break;
            case "PLANT_BOMB": replica = bombAction(jsonNode,session);
            case "JUMP": break;
            default:  break;

        }

        //TODO надо вытаскивать объекты из исх. очереди
        gameSession.broadcast(replica);

     }

    public String playerAction(JsonNode jsonNode, WebSocketSession session) {

        String direction = jsonNode.findValue("direction").asText();

        Pawn pawn = gameSession.getPlayer(session);
        pawn.move(direction);
        return replicator.getReplica(gameSession.getListObjects(null));
    }

    public String bombAction(JsonNode jsonNode, WebSocketSession session) {

        Position currentPosition = getPosition(session);

        Bomb bomb = new Bomb(objectType.Bomb,currentPosition);

        //добавляем бомбу в очередь, пока бомба есть в очереди, значит она есть на экране
        gameSession.put(session,bomb);

        startBomb(bomb);

        return replicator.getReplica(gameSession.getListObjects(bomb));
    }

    public void startBomb(@NotNull Bomb bomb) {
        TimerTask bombTask = new Bomb(objectType.Fire,bomb.getPosition());
        // стартуем TimerTask в виде демона

        Timer timer = new Timer(true);
        System.out.println(">>>> TimerTask начал выполнение");
        timer.schedule(bombTask,10 * 1000);
        String json = replicator.getReplica(gameSession.getListObjects(bombTask));
        gameSession.broadcast(json);
    }

//    public String fireAction(){
//        if(gameSession.sizeBombInGame()>0){
//            Bomb bomb = gameSession.getBomb()
//        }
//    }

    public Position getPosition(WebSocketSession session) {
        Pawn pawn = gameSession.getPlayer(session);
        return pawn.getPosition();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }


}
