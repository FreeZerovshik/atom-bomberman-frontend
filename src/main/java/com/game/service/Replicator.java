package com.game.service;

import com.game.model.Pawn;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class Replicator {

    @Autowired
    GameSession gameSession;

    public String getPawnJson() {
        List<Pawn> pawnList =  gameSession.getPawns();
        return null;
    }

}
