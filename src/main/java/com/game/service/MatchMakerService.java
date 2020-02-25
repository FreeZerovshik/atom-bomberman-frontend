package com.game.service;

import com.game.model.Session;
import com.game.model.Player;
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
 *
 */
@Service
public class MatchMakerService  implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerService.class);

    @Autowired
    private GameRepository repo;

    private AtomicLong playerId = new AtomicLong();
    private AtomicLong sessionId = new AtomicLong();

    public Long join(String gameName) {
        new Thread(this, gameName).start();
        return 1L;
    }


    @Override
    public void run() {

        String gameName = Thread.currentThread().getName();

        log.info(">>> Create game in matchmaker " + gameName + " repo=" + repo );
        log.info(">>>gameQueue " + repo.gamesQueue + " playerQueue=" + repo.playerQueue );

        Session session = new Session(sessionId.getAndIncrement(), gameName);
        Player player = new Player(playerId.getAndIncrement());


        log.info("<<< Session id " + session.getId() + " name=" + session.getName() + " obj=" + session.toString());
        log.info("<<< Player id " + player.getId() + " name="+ player.getName()+ " obj=" + player.toString());


        repo.put(session);
        repo.put(player);
//        service.start(service.getGameId());
        //service.connect(service.getGameName(),service.getGameId());



        log.info("<<< Game queue size " + repo.gameSize());
        log.info("<<< Player queue size " + repo.playerSize());

//        return session.getId();
    }
}
