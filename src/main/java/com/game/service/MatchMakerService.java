package com.game.service;

import com.game.model.Session;
import com.game.model.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by sergey on 3/14/17.
 * - Работает только с http запросами
 * - Собирает игру из нескольких игроков = 4, работает с пулом игроков, где отбирает кол-во игроков для игры и отправляет
 * в игровую сессию
 *
 */
@Service
public class MatchMakerService {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerService.class);

    @Autowired
    private GameService service;
//    private GameSession session;

    private  ConcurrentHashMap<Long, Session> gamesQueue = new ConcurrentHashMap<>();  // очередь из игр

    private  ConcurrentHashMap<Long, Player> playerQueue = new ConcurrentHashMap<>();  // очередь из игроков

    public Long join(String gameName) {
        log.info(">>> Create game in matchmaker " + gameName);

        service.createSession(gameName);

        service.put(service.session);
        service.put(service.player);
//        service.start(service.getGameId());
        //service.connect(service.getGameName(),service.getGameId());

        log.info("<<< Session id " + service.session.getId());
        log.info("<<< Player id " + service.player.getId());
        log.info("<<< Game queue size " + service.gameSize());
        log.info("<<< Player queue size " + service.playerSize());

        return service.getGameId();
    }


}
