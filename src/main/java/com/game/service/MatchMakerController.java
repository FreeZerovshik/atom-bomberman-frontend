package com.game.service;

import model.Connection;
import model.GameSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("matchmaker")
public class MatchMakerController {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);
    /**
     * curl -X POST -i localhost:8090/game/start -d "user=test1"
     */
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void join(@RequestParam("name") String name){
        // new Thread(new GameMechanic()).start();
//        GameMechanic gm  = new GameMechanic();
//        Thread th = new Thread(gm);
//        th.start();
//        System.out.println("Thread: "+ th.getId());
        log.info(">>> Call join with game name : " + name);
//        new Thread(new MatchMakerImp()).start();
    }
}
