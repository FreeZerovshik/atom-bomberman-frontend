package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Pawn;
import com.game.model.Replica;
import com.game.tick.Ticker;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class GameSession  {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private Long id;
    private List<Pawn> pawns;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    Replicator replicator;

    public void setId(Long id) {
        this.id = id;
    }

    public void setPawns(List<Pawn> pawns) {
        this.pawns = pawns;
    }

    public Long getId() {
        return id;
    }

    public void startGame() throws IOException {

        this.setPawns(gameRepository.getPlayersBySize(GameRepository.PLAYERS_IN_GAME));

        GameMechanics gameMechanics = new GameMechanics(this,replicator);
//        replicator.sendReplica(gameMechanics.generateWalls());
        replicator.sendReplica(gameMechanics.generateMaze(270,420));

        Ticker tick = new Ticker();
        tick.registerTickable(gameMechanics);
        tick.gameLoop();

    }

    private void pawnToJSON() throws IOException {
        Message msg;
        List<Object> objects = new ArrayList<>();

        for (Pawn pawn : getPawns()) {
            objects.add(JsonInterface.toJson(pawn));
        }

        MessageObjects messageObjects = new MessageObjects(objects);
        msg = new Message(Topic.REPLICA, messageObjects);

        String str = JsonInterface.toJson(msg);
        log.info(">>> REPLICA json:" + str.toString());

    }

    private void sendTestPossess() throws IOException {
        Message msg;
        for (Pawn pawn : getPawns()) {
            log.info("++++ Pawn:" + pawn.getName() + " [" + pawn.getId() + "]" + " in game:" + getId());

//            msg = new Message(Topic.POSSESS, JsonInterface.toJson(pawn.getId()));
            WebSocketSession session = pawn.getSession();
//            session.sendMessage(new TextMessage(JsonInterface.toJson(msg)));

            // валидация json
//            Boolean res = JsonHelper.isValidJSON(Replica.test_message());
//            log.info(">>> REPLICA validate json:" + res.toString());
            // тестовая реплика для проверки фронта
            log.info("test msg="+Replica.test_message());
            session.sendMessage(new TextMessage(Replica.test_message()));
        }
    }

    public List<Pawn> getPawns() {
        return pawns;
    }
}
