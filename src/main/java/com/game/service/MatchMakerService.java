package com.game.service;

import com.game.model.Pawn;
import com.game.model.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    GameSession gameSession;



    //    @PostConstruct
    public Long start(String playerName) {
        this.playerName = playerName;
        new Thread(this, playerName).start();
        return gameId.get();
    }

    public Long getGameId() {
        return gameId.get();
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void run() {
//        while (!Thread.currentThread().isInterrupted()) {
        // добавим игрока в очередь
        Pawn pawn = new Pawn(Thread.currentThread().getName(), new Position(0,0));

        gameRepository.put(pawn);


        if (gameRepository.playerSize() == gameRepository.PLAYERS_IN_GAME) {
            // GameSession session = new GameSession(gameId.get(),gameRepository.getPlayersBySize(gameRepository.PLAYERS_IN_GAME));
//            gameSession.setPawns(gameRepository.getPlayersBySize(gameRepository.PLAYERS_IN_GAME));
//            gameSession.setId(gameId.get());
            log.info(">>>>>>>>> CREATE NEW GAME <<<<<<<<<<<<< id=" + gameId);
            gameSession.setId(gameId.get());
            // save gameSession on map
            gameRepository.put(gameSession);
//                candidates.clear();
            gameId.getAndIncrement();
        }
//        }
    }
}
