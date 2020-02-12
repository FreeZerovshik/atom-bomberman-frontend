package com.game.service;

import model.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.autoconfigure.metrics.export.ganglia.GangliaMetricsExportAutoConfiguration;
import org.springframework.stereotype.Service;

/**
 * Created by sergey on 3/14/17.
 */
@Service
public class MatchMakerImp implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerImp.class);
    private GameSession gameSession = new GameSession();


//    @Autowired

//    private ConnectionQueue connectionQueue;

//    @Autowired
//    private GameRepository gameRepository;


//   @PostConstruct
    public Long startThread(String gameName) {
        new Thread(this, gameName).start();
        log.info(">>> Start thread in matchmaker " + gameName);
        return gameSession.getId();
    }

    @Override
    public void run() {
        gameSession.setId();
        log.info(">>> MatchMaker Started: " + gameSession.getId());

        GameRepository repo  = new GameRepository();
        repo.put(gameSession);

        log.info(">>>> GameSessions:" +  repo.getAll());

//        List<Connection> candidates = new ArrayList<>(GameSession.PLAYERS_IN_GAME);
//
//        while (!Thread.currentThread().isInterrupted()) {
//            try {
//                candidates.add(
//                        connectionQueue.getQueue().poll(10_000, TimeUnit.SECONDS)
//                );
//            } catch (InterruptedException e) {
//                log.warn("Timeout reached");
//            }
//
//            log.info(">>> After try "+ candidates.size() );
//            if (candidates.size() == GameSession.PLAYERS_IN_GAME) {
//                GameSession session = new GameSession(candidates.toArray(new Connection[0]));
//                log.info(session.toString());
//                gameRepository.put(session);
//                candidates.clear();
//            }
//        }
    }
}
