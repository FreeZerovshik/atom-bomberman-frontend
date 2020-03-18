package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Pawn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    private final ConcurrentHashMap<String, Pawn> playerQueue = new ConcurrentHashMap<>();  // очередь из игроков
    private final ConcurrentHashMap<String, Pawn> Queue = new ConcurrentHashMap<>();  // очередь из игроков
    private final ConcurrentHashMap<Long, String> outQueue = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, String> inQueue = new ConcurrentHashMap<>();


    public void put(Long id, String json) {
        this.outQueue.put(id, json);
    }
    public void put(Pawn pawn) {
        this.playerQueue.put(pawn.getName(), pawn);
    }
    public void put(GameSession gameSession) {
        this.gameQueue.put(gameSession.getId(), gameSession);
    }

    public String getMessage(){
        Long key = outQueue.keys().nextElement();
        String str = outQueue.get(key);
        outQueue.remove(key);
        log.info("json="+ str);
        return str;
    }

    public String getCurrentPlayerName() {
        return matchMakerService.getPlayerName();
    }

    public List<Pawn> getPlayersBySize(int size) {
        List<Pawn> pawns = new ArrayList<>();
//        Enumeration<String> keys = playerQueue.keys();
        String key;
        //TODO интересно в многопоточке не будет потерь ключей при такам подходет, может обернуть как потокобезопасный блок?
        for (int i = 0; i <= size - 1; i++) {
            key = playerQueue.keys().nextElement();
            pawns.add(playerQueue.get(key));
            playerQueue.remove(key);
        }

//        playerQueue.forEach((k, v) ->  pawns.add(v));

        return pawns;
    }



    public Pawn getPlayerByName(String name) {
        return playerQueue.get(name);
    }

    public Long outQueueSize() {return Long.valueOf(outQueue.size()); }

    public Long playerSize() {
        playerQueue.forEach((k, v) -> System.out.println(k + " contains Pawn id=" + v.getId() + " name=" + v.getName() + " session=" + v.getSession()));
        return Long.valueOf(playerQueue.size());
    }

    public void startGame() throws IOException {
        if (gameQueue.size() > 0) {
            GameSession game = gameQueue.get(gameQueue.keys().nextElement());

            game.startGame();
        }
    }


}
