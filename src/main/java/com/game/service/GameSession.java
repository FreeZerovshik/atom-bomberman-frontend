package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Bomb;
import com.game.model.Pawn;
import com.game.tick.Ticker;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.game.service.GameRepository.PLAYERS_IN_GAME;

@Component
public class GameSession  {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private Long id;
    private ConcurrentHashMap<WebSocketSession, Pawn> playersInGame;
    private ConcurrentHashMap<WebSocketSession, Bomb> bombInGame = new ConcurrentHashMap<>();

    @Autowired
    GameRepository gameRepository;

    @Autowired
    Replicator replicator;

    public void sendPawns()  {  playersInGame.forEachValue(PLAYERS_IN_GAME, player ->  replicator.sendReplica(player)); }

//    public Pawn getPawn(WebSocketSession session){
//        return connectionPool.getPlayer(session);
//    }
    public void setId(Long id) {
        this.id = id;
    }

    public void put(WebSocketSession session, Bomb bomb){
        bombInGame.put(session,bomb);
    }

    public Bomb getBomb(WebSocketSession session){ return bombInGame.get(session);}

    public void setPlayersInGame(ConcurrentHashMap playersInGame) {
        this.playersInGame = playersInGame;
    }

    public int sizeBombInGame(){return bombInGame.size();}

    public Long getId() {return id; }

    public Pawn getPlayer(WebSocketSession session) {
        return playersInGame.get(session);
    }

    public List<Object> getListObjects(Object object) {
        List<Object> objectList = new ArrayList<>();
        if (object != null) objectList.add(object);
        objectList.addAll(playersInGame.values());
        return objectList;
    }

    public void startGame() {

        GameMechanics gameMechanics = new GameMechanics(this,replicator);

        //TODO вынести в отдельный метод инициализации

        // generate initial objects arrray (maze, pawn ...) and send to front
        List<Object> initialObjects = gameMechanics.generateMaze(270, 420);

        initialObjects.addAll(playersInGame.values());
        playersInGame.forEach((k,v)->gameMechanics.getStartPositionPawn(v));
        broadcast(replicator.getReplica(initialObjects));

//        Ticker tick = new Ticker();
//        tick.registerTickable(gameMechanics);
//        tick.gameLoop();

    }


    public void send(@NotNull WebSocketSession session, @NotNull String msg) {
        if (session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(msg));
            } catch (IOException ignored) {
            }
        }
    }
    public void broadcast(@NotNull String msg) {
        log.info("send json="+ msg);
        playersInGame.forEachKey(1, session -> send(session, msg));
    }

//    private void pawnToJSON() throws IOException {
//        Message msg;
//        List<Object> objects = new ArrayList<>();
//
//        for (Pawn pawn : getPlayersInGame()) {
//            objects.add(JsonInterface.toJson(pawn));
//        }
//
//        MessageObjects messageObjects = new MessageObjects(objects);
//        msg = new Message(Topic.REPLICA, messageObjects);
//
//        String str = JsonInterface.toJson(msg);
//        log.info(">>> REPLICA json:" + str.toString());
//
//    }

//    private void sendTestPossess() throws IOException {
//        Message msg;
//        for (Pawn pawn : getPlayersInGame()) {
//            log.info("++++ Pawn:" + pawn.getName() + " [" + pawn.getId() + "]" + " in game:" + getId());
//            WebSocketSession session = pawn.getSession();
//
//            log.info("test msg="+Replica.test_message());
//            session.sendMessage(new TextMessage(Replica.test_message()));
//        }
//    }

//    public List<Pawn> getPlayersInGame() {
//        List<Pawn> pawns = new ArrayList<>();
//        playersInGame.forEach((session, player) ->  pawns.add(player));
//        return pawns;
//    }
}
