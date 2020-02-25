package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Session;
import com.game.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/15/17.
 * - Создает инстанс игры, где уже начинаю играть игроки
 */
@Repository
public class GameRepository {
    //gameId, gameSession

    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    public final ConcurrentHashMap<Long, Session> gamesQueue = new ConcurrentHashMap<>();  // очередь из игр
    public  final ConcurrentHashMap<Long, Player> playerQueue = new ConcurrentHashMap<>();  // очередь из игроков
//    private final GameMechanic game = new GameMechanic();


    //Создаем пользователя, помещаем в очередь игроков
    //Создаем игру, помещаем в очередь игр
//    public void createSession(String gameName){
//
//        log.info("<<< Session id " + session.getId() + " name: "+ session.getName());
//        log.info("<<< Player id " + player.getId() + " name: " + player.getName());
////        this.save();          // save object into queue
//        //return session;
//    }
//
    public Long getGameId(Session session){
        return session.getId();
    }
//
//    public String getGameName(){
//        return session.getName();
//    }

//    public Collection<Player> getAll() {
//        return playerQueue.values();
//    }

    public void start(Long gameId) {
        log.info(">>> Start game:"+ gameId);

//       if(session.gamesQueue.containsKey(gameId)) {
////           log.info(">>> Game exists start game"+ gameId);
////           log.info(">>> get map:"+ session.gamesQueue.get(gameId));
////           game.startGameThread(session.gamesQueue.get(gameId).getGameName());
////       }
    }

//    public void connect(String name, Long gameId){
////        player.getAndIncrementPlayerId();
//        //TODO add player to game
//        log.info(">>> PlayerId " + player.getId());
//        log.info(">>> PlayerName " + player.getName());
//    }

//    public void save() {
//        log.info(">>>Save sessionId: "+ session.getId());
//        log.info(">>>Save player: "+ player.getId() + " name:" + player.getName());
////        session.gamesQueue.put(session.getSessionId(), session);
////        playerQueue.put(player.getPlayerId(),player);
//    }

    public void put(Session session){
        this.gamesQueue.put(session.getId(), session);
    }

    public void put(Player player){
        this.playerQueue.put(player.getId(),player);
    }

    public Collection<Session> getAll() {
        return gamesQueue.values();
    }
    public int gameSize() {
        gamesQueue.forEach((k, v) -> System.out.println(k + " contains Session id="+ v.getId() + " name=" + v.getName()));
        return gamesQueue.size();
    }

    public int playerSize() {
        playerQueue.forEach((k, v) -> System.out.println(k + " contains Player id=" + v.getId() + " name=" + v.getName()));
        return playerQueue.size();

    }


}