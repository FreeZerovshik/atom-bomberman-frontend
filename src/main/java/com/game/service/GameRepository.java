package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/15/17.
 * - Создает инстанс игры, где уже начинаю играть игроки
 */
@Component
public class GameRepository {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);
    public static final int PLAYERS_IN_GAME = 4;

    @Autowired
    private MatchMakerService matchMakerService;


    private final ConcurrentHashMap<Long, WebSocketSession> sessionQueue = new ConcurrentHashMap<>();  // очередь из игр
    private final ConcurrentHashMap<String, Player> playerQueue = new ConcurrentHashMap<>();  // очередь из игроков

    public void start(Long gameId) {
        log.info(">>> Start game:" + gameId);

        if (sessionQueue.containsKey(gameId)) {
            log.info(">>> Game exists start game" + gameId);
            log.info(">>> get map:" + sessionQueue.get(gameId));
            //game.startGameThread(sessionQueue.get(gameId).getName());
        }
    }

    public void put(WebSocketSession socket){this.sessionQueue.put(matchMakerService.getGameId(), socket);}
    public void put(Player player) { this.playerQueue.put(player.getName(), player);  }

    public String getCurrentPlayerName(){
        return matchMakerService.getPlayerName();
    }

    public List<Player> getPlayersBySize(int size) {
        List<Player> players = new ArrayList<>();
        Enumeration<String> keys = playerQueue.keys();
        for (int i = 0; i <= size - 1; i++) {
            players.add(playerQueue.get(keys.nextElement()));
        }

//        playerQueue.forEach((k, v) ->  players.add(v));

//        if (playerQueue.size() >= size) {
////            playerQueue.forEach((k, v) ->  players.add(v) );
//
//            for (Long i = 0L; i <= size - 1; i++) {
//                players.add(playerQueue.get(i));
//                playerQueue.remove(i);
//            }
//        }
        return players;
    }

    public Player getPlayer(Long key) {
        return playerQueue.get(key);
    }

    public Player getPlayerByName(String name){
        return playerQueue.get(name);
    }

    public int sessionSize() {
        sessionQueue.forEach((k, v) -> System.out.println(k + " contains Session id=" + v.getId()));
        return sessionQueue.size();
    }

    public Long playerSize() {
        playerQueue.forEach((k, v) -> System.out.println(k + " contains Player id=" + v.getId() + " name=" + v.getName() + " session="+ v.getSession()));
        return Long.valueOf(playerQueue.size());
    }


}
