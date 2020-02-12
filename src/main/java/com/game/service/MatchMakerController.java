package com.game.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity join(@RequestParam("name") String name){
        MatchMakerImp gameMatch = new MatchMakerImp();

        log.info(">>> Call join with game name : " + name);
        return  ResponseEntity.ok().body(gameMatch.startThread(name));
//        new MatchMakerImp().startThread(name);
//        GameMechanic gm  = new GameMechanic();
//        Thread th = new Thread(gm);
//        th.start();
//        System.out.println("Thread: "+ th.getId());

//        new Thread(new MatchMakerImp()).start();
    }
}
