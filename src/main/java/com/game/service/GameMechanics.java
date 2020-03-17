package com.game.service;

import com.game.controller.MatchMakerController;
import com.game.message.Message;
import com.game.message.MessageObjects;
import com.game.message.Topic;
import com.game.model.Pawn;
import com.game.model.Position;
import com.game.model.Wall;
import com.game.tick.Tickable;
import com.game.util.Ellers;
import com.game.util.Helper;
import com.game.util.JsonInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


public class GameMechanics implements Tickable {
    private static final Logger log = LoggerFactory.getLogger(MatchMakerController.class);

    private GameSession gameSession;
//    private GameRepository gameRepository;
    private Replicator replicator;


    public GameMechanics(GameSession gameSession, Replicator replicator) {
        this.gameSession = gameSession;
//        this.gameRepository = gameRepository;
        this.replicator = replicator;
    }

    AtomicLong idGenerator = new AtomicLong();


    @Override
    public void tick(long elapsed) throws IOException {

        List<Object> objects = new ArrayList<>();

        // add Pawn json to array
        for (Pawn pawn : gameSession.getPawns()) {
            objects.add(pawn);
        }

        replicator.sendReplica(objects);

    }

//    public void sendMessage() throws IOException {
//        if (gameRepository.outQueueSize() > 0L) {
//            String msg = gameRepository.getMessage();
//            for (Pawn pawn : gameSession.getPawns()) {
//                WebSocketSession session = pawn.getSession();
//                session.sendMessage(new TextMessage(msg));
//            }
//        }
//    }

    public List<Object> generateWalls(){
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
        List<Object> walls = new ArrayList<>();

        for(int i=0; i< maze.length; i++){
            for(int j=0; j<maze[0].length; j++){
//                System.out.print(maze[i][j]);
//                walls.add(new Wall(new Position(maze[i],maze[j])));
                if (maze[i][j] == 1) walls.add(new Wall(new Position(i*28,j*28)));

            }
//            System.out.println();
        }
        return walls;
    }




}
