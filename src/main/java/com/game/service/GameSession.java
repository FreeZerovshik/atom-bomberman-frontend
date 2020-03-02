package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.model.Player;
import com.game.tick.Ticker;
import okhttp3.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.game.service.GameRepository.PLAYERS_IN_GAME;

@Component
public class GameSession implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private final ConcurrentHashMap<Long, Message> outputQueue = new ConcurrentHashMap<>();// исходящая очередь
    private final ConcurrentHashMap<Long, Message> inputQueue = new ConcurrentHashMap<>();// входящая очередь сообщение

    private WebSocketSession session;
    private Player player;

    @Autowired
    private GameRepository gameRepository;


    public void startGameThread(String gameName) {
        new Thread(this, gameName).start();
        log.info(">>> Start thread in GameMechanic " + gameName);
    }

    @Override
    public void run() {
        log.info(">>> Thread started " + Thread.currentThread().getId());

//        if (gameRepository.playerSize() == GameRepository.PLAYERS_IN_GAME) {

//        GameMechanics gameMechanics= new GameMechanics();
            Ticker tick = new Ticker(this);
            this.player  = gameRepository.getPlayer(gameRepository.playerSize()-1);
            tick.registerTickable(player);
//            List<Player> players = gameRepository.getPlayersBySize(PLAYERS_IN_GAME);
//            players.forEach(v -> tick.registerTickable(v));

            tick.gameLoop();    //start tick
//        }
    }

    public void put(Message message){this.outputQueue.put(message.getId(),message);}

    public WebSocketSession getSession() {
        return session;
    }

    public void setSession(WebSocketSession session) {
        this.session = session;
    }
}
