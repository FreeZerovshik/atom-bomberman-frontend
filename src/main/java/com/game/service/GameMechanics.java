package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.model.Pawn;
import com.game.model.Position;
import com.game.model.Wall;
import com.game.tick.Tickable;
import com.game.util.Ellers;
import com.game.util.Helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class GameMechanics implements Tickable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private GameSession gameSession;
    private Replicator replicator;
    private final List<Object> walls = new ArrayList<>();
    private final List<Object> empty = new ArrayList<>();
    private List<Object> replicas = new ArrayList<>();



    public GameMechanics(GameSession gameSession, Replicator replicator) {
        this.gameSession = gameSession;
        this.replicator = replicator;
    }

    @Override
    public void tick(long elapsed) throws IOException {

        List<Object> objects = new ArrayList<>();

        // add Pawn json to array
        for (Pawn pawn : gameSession.getPawns()) {
            objects.add(pawn);
        }

        replicator.sendReplica(objects);

    }


    public List<Object> generateTestWalls(){
        int step = 10;
        int cntWalls = 50;
        int min = 0;
        int maxX = 800;
        int maxY = 500;

        List<Object> walls = new ArrayList<>();

        for(int i = 0; i <=cntWalls; i++) {
            double x = Helper.random(min, maxX);
            double y = Helper.random(min, maxY);
            walls.add(new Wall(new Position(x,y)));
        }

        return walls;
    }

    public List<Object> generateMaze(int w, int h){
        Ellers ellers = new Ellers(h/28, w/28);
        ellers.makeMaze();
        int[][] maze = ellers.getMaze();


        for(int i=0; i< maze.length; i++){
            for(int j=0; j<maze[0].length; j++){
                if (maze[i][j] == 1) walls.add(new Wall(new Position(i*28,j*28)));// ADD wall into array
                if (maze[i][j] == 0) empty.add(new Position(i*28,j*28));            // add empty point to array
            }
        }
        return walls;
    }


    public List<Object> getWalls() {
        return walls;
    }

    public List<Object> getEmpty() {
        return empty;
    }

    public List<Object> getReplicas() {
        return replicas;
    }

    public void setReplicas(List<Object> objects) {
        this.replicas = objects;
    }


    public void getStartPositionPawn(List<Pawn> pawns){
        Collections.shuffle(empty);

        for(Pawn pawn: pawns){
            Position pos = (Position) empty.get(Helper.random(0,empty.size()-1));
            pawn.setPosition(pos);
        }
    }
}
