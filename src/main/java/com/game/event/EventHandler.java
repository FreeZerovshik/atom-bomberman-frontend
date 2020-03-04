package com.game.event;

import com.game.model.Player;
import com.game.service.GameRepository;
import com.game.service.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class EventHandler extends TextWebSocketHandler implements WebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(EventHandler.class);

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSession gameSession;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        log.info("Socket Connected: " + session);

        // инжектив в игрока сессию
        Player player = gameRepository.getPlayerByName(gameRepository.getCurrentPlayerName());
        player.setSession(session);

        log.info(">>>> PLAYER:" + player);
//       gameRepository.put(session);


//       String tst_msg = "{\"id\":1,\"type\":\"Wood\",\"position\":{\"y\":20,\"x\":10}}";

//        String posses = "{\"topic\":\"posses\", \"data\": 123}";
        // (new Ticker()).gameLoop(session,tst_msg);
//        session.sendMessage(new TextMessage(posses));

//        if (gameRepository.playerSize() == GameRepository.PLAYERS_IN_GAME) {
//            log.info(">>>> CREATING GAME NOW <<<<<");
//            log.info("<<< Game queue size " + gameRepository.gameSize());
//            log.info("<<< Player queue size " + gameRepository.playerSize());

//            gameRepository.put(session);
//            gameSession.setSession(session);
//            gameSession.setSession(session);
//            gameSession.startGameThread("test");

//            MatchMakerService matchMakerService = new MatchMakerService();

//        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        log.info(">>>>>>>>> handleTextMessage " + message.toString());

        session.sendMessage(new TextMessage("{ \"history\": [ \"ololo\", \"2\" ] }"));
        log.info("Received " + message.toString());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Socket Closed: [" + closeStatus.getCode() + "] " + closeStatus.getReason());
        super.afterConnectionClosed(session, closeStatus);
    }


}
