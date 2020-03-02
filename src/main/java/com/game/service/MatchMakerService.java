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
public class MatchMakerService  {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerService.class);

    @Autowired
    public GameRepository gameRepository;

    private AtomicLong playerId = new AtomicLong();
    private AtomicLong sessionId = new AtomicLong();


    //save players and send answer for ws connect
    public Long join(String gameName) {

        log.info(">>> Create game in matchmaker " + gameName + " repo=" + gameRepository);

        Session session = new Session(sessionId.getAndIncrement(), gameName);
        Player player = new Player(playerId.getAndIncrement());

        log.info("<<< Session id " + session.getId() + " name=" + session.getName() + " obj=" + session.toString());
        log.info("<<< Player id " + player.getId() + " name="+ player.getName()+ " obj=" + player.toString());

        gameRepository.put(session);
        gameRepository.put(player);

//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        log.info("<<< Game queue size " + repo.gameSize());
//        log.info("<<< Player queue size " + repo.playerSize());


//        service.start(service.getGameId());
        //service.connect(service.getGameName(),service.getGameId());
        log.info("+++++++++++ Game Repo:"+ gameRepository.toString());

        return session.getId();
    }

    public GameRepository getGameRepository() {
        return gameRepository;
    }


    //    @Override
//    public void run() {
//

//    }
}
