package com.game.controller;

import com.game.service.MatchMakerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private MatchMakerService matchmaker;
    /**
     * curl -X POST -i localhost:8090/game/join -d "user=test1"
     */
    @RequestMapping(
            path = "join",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity join(@RequestParam("name") String gameName){
        log.info(">>> Call join with game name : " + gameName);
        return  ResponseEntity.ok().body(matchmaker.join(gameName));
    }
}
