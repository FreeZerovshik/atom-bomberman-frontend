package com.game.event;

import com.fasterxml.jackson.databind.JsonNode;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Direction;
import com.game.model.Game;
import com.game.model.Pawn;
import com.game.network.ConnectionPool;
import com.game.service.GameRepository;
import com.game.service.GameSession;
import com.game.service.MatchMakerService;
import com.game.service.Replicator;
import com.game.util.JsonHelper;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
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
//        session.sendMessage(new TextMessage("{ \"history\": [ \"ololo\", \"2\" ] }"));
        log.info("Received " + message.getPayload());
        JsonNode jsonNode = JsonInterface.getJsonNode(message.getPayload());
        String direction = jsonNode.findValue("direction").asText();

//        synchronized (session) {
            Pawn pawn = gameSession.getPlayer(session);
            pawn.move(direction);
            String replica = replicator.getReplica(gameSession.getPlayers());
            gameSession.broadcast(replica);
//        }
     }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }


}
