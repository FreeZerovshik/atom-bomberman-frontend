package com.game.service;

import com.game.model.Pawn;
import com.game.model.Position;
import com.game.network.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by sergey on 3/14/17.
 * - Работает только с http запросами
 * - Собирает игру из нескольких игроков = 4, работает с пулом игроков, где отбирает кол-во игроков для игры и отправляет
 * в игровую сессию
 */
@Service
public class MatchMakerService implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerService.class);

    private final AtomicLong gameId = new AtomicLong(0L);
    private String playerName;


    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameSession gameSession;

//    @Autowired
//    private ConnectionPool poolPlayer;

    public Long addPlayerToGame(String playerName) {
        Pawn pawn = new Pawn(playerName, new Position(0,0));
        gameRepository.put(pawn);

//        new Thread(this, playerName).start();
        return gameId.get();
    }

    public void startGame(ConcurrentHashMap playersPool){
        gameSession.setPlayersInGame(playersPool);
        new Thread(this, String.valueOf(gameId.get())).start();
    }

    public Long getGameId() {
        return gameId.get();
    }

    public String getPlayerName() {
        return playerName;
    }


    @Override
    public void run() {

        log.info(">>>>>>>>> CREATE NEW GAME <<<<<<<<<<<<< id=" + gameId);

        gameSession.setId(gameId.get());
        gameRepository.put(gameSession);
        gameId.getAndIncrement();
        gameSession.startGame();

    }
}
