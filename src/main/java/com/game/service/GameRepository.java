package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Session;
import com.game.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/15/17.
 * - Создает инстанс игры, где уже начинаю играть игроки
 */
@Component
public class GameRepository {
    public static final int PLAYERS_IN_GAME = 4;


    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private final ConcurrentHashMap<Long, Session> gamesQueue = new ConcurrentHashMap<>();  // очередь из игр
    private final ConcurrentHashMap<Long, Player> playerQueue = new ConcurrentHashMap<>();  // очередь из игроков


    private final GameSession game = new GameSession();


    public Long getGameId(Session session) {
        return session.getId();
    }
//

//    public Collection<Player> getAll() {
//        return playerQueue.values();
//    }

    public void start(Long gameId) {
        log.info(">>> Start game:" + gameId);

        if (gamesQueue.containsKey(gameId)) {
            log.info(">>> Game exists start game" + gameId);
            log.info(">>> get map:" + gamesQueue.get(gameId));
            game.startGameThread(gamesQueue.get(gameId).getName());
        }
    }


    public void put(Session session) {
        this.gamesQueue.put(session.getId(), session);
    }

    public void put(Player player) {
        this.playerQueue.put(player.getId(), player);
    }

    public List<Player> getPlayersBySize(int size) {
        List<Player> players = new ArrayList<>();
        if (playerQueue.size() >= size) {
//            playerQueue.forEach((k, v) ->  players.add(v) );

            for (Long i = 0L; i <= size - 1; i++) {
                players.add(playerQueue.get(i));
                playerQueue.remove(i);
            }
        }
        return players;
    }

    public Player getPlayer(Long key) {
        return playerQueue.get(key);
    }

    public Collection<Session> getAll() {
        return gamesQueue.values();
    }

    public int gameSize() {
        gamesQueue.forEach((k, v) -> System.out.println(k + " contains Session id=" + v.getId() + " name=" + v.getName()));
        return gamesQueue.size();
    }

    public int playerSize() {
        playerQueue.forEach((k, v) -> System.out.println(k + " contains Player id=" + v.getId() + " name=" + v.getName()));
        return playerQueue.size();
    }


}
