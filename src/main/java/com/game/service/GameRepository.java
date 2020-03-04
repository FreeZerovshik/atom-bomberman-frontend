package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Game;
import com.game.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
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


    private final ConcurrentHashMap<Long, GameSession> gameQueue = new ConcurrentHashMap<>();  // очередь из игр
    private final ConcurrentHashMap<String, Player> playerQueue = new ConcurrentHashMap<>();  // очередь из игроков

    public void start(Long gameId) {
        log.info(">>> Start game:" + gameId);

        if (gameQueue.containsKey(gameId)) {
            log.info(">>> Game exists start game" + gameId);
            log.info(">>> get map:" + gameQueue.get(gameId));
            //game.startGameThread(sessionQueue.get(gameId).getName());
        }
    }

    //    public void put(WebSocketSession socket){this.gameQueue.put(matchMakerService.getGameId(), socket);}
    public void put(Player player) {
        this.playerQueue.put(player.getName(), player);
    }

    public void put(GameSession gameSession) {
        this.gameQueue.put(gameSession.getId(), gameSession);
    }

    public String getCurrentPlayerName() {
        return matchMakerService.getPlayerName();
    }

    public List<Player> getPlayersBySize(int size) {
        List<Player> players = new ArrayList<>();
//        Enumeration<String> keys = playerQueue.keys();
        String key;
        //TODO интересно в многопоточке не будет потерь ключей при такам подходет, может обернуть как потокобезопасный блок?
        for (int i = 0; i <= size - 1; i++) {
            key = playerQueue.keys().nextElement();
            players.add(playerQueue.get(key));
            playerQueue.remove(key);
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

    public void removePlayers(List<Player> players) {
        for (Player pl : players) {
            playerQueue.remove(pl.getName());
        }
    }

    public Player getPlayer(Long key) {
        return playerQueue.get(key);
    }

    public Player getPlayerByName(String name) {
        return playerQueue.get(name);
    }

    public int sessionSize() {
        gameQueue.forEach((k, v) -> System.out.println(k + " contains Game with id=" + v.getId()));
        return gameQueue.size();
    }

    public Long playerSize() {
        playerQueue.forEach((k, v) -> System.out.println(k + " contains Player id=" + v.getId() + " name=" + v.getName() + " session=" + v.getSession()));
        return Long.valueOf(playerQueue.size());
    }

    public void startGame() throws IOException {
        if (gameQueue.size() > 0) {
            GameSession game = gameQueue.get(gameQueue.keys().nextElement());

//            game.setId(matchMakerService.getGameId());

//            this.removePlayers(game.getPlayers());
            game.startGame();

        }
    }


}
